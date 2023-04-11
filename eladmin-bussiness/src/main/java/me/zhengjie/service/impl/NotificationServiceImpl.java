/*
*  Copyright 2019-2020 Zheng Jie
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package me.zhengjie.service.impl;

import me.zhengjie.domain.Notification;
import me.zhengjie.utils.*;
import lombok.RequiredArgsConstructor;
import me.zhengjie.repository.NotificationRepository;
import me.zhengjie.service.NotificationService;
import me.zhengjie.service.dto.NotificationDto;
import me.zhengjie.service.dto.NotificationQueryCriteria;
import me.zhengjie.service.mapstruct.NotificationMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.core.util.IdUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @website https://eladmin.vip
* @description 服务实现
* @author chenjie
* @date 2023-04-12
**/
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public Map<String,Object> queryAll(NotificationQueryCriteria criteria, Pageable pageable){

        String username = SecurityUtils.getCurrentUser().getUsername();//获取用户名
        Long currentUserId = SecurityUtils.getCurrentUserId();//获取用户名id
        Integer cuserid= currentUserId.intValue();
        criteria.setDeployeeNo(cuserid);
        Page<Notification> page = notificationRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(notificationMapper::toDto));
    }

    @Override
    public List<NotificationDto> queryAll(NotificationQueryCriteria criteria){
        return notificationMapper.toDto(notificationRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public NotificationDto findById(String id) {
        Notification notification = notificationRepository.findById(id).orElseGet(Notification::new);
        ValidationUtil.isNull(notification.getId(),"Notification","id",id);
        return notificationMapper.toDto(notification);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public NotificationDto create(Notification resources) {
        resources.setId(IdUtil.simpleUUID());
        return notificationMapper.toDto(notificationRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Notification resources) {
        Notification notification = notificationRepository.findById(resources.getId()).orElseGet(Notification::new);
        ValidationUtil.isNull( notification.getId(),"Notification","id",resources.getId());
        notification.copy(resources);
        notificationRepository.save(notification);
    }

    @Override
    public void deleteAll(String[] ids) {
        for (String id : ids) {
            notificationRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<NotificationDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (NotificationDto notification : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("通知标题", notification.getTitle());
            map.put("通知内容", notification.getContent());
            map.put("创建时间", notification.getCteatedAt());
            map.put("更新时间", notification.getUpdatedAt());
            map.put("被告知的员工编号", notification.getDeployeeNo());
            map.put("被告知的员工名称", notification.getDeplpyeeName());
            map.put("创建者", notification.getCteatedby());
            map.put("更新者", notification.getUpdatedby());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
