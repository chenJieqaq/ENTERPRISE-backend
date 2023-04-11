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

import cn.hutool.core.util.RandomUtil;
import me.zhengjie.domain.TodoDone;
import me.zhengjie.utils.*;
import lombok.RequiredArgsConstructor;
import me.zhengjie.repository.TodoDoneRepository;
import me.zhengjie.service.TodoDoneService;
import me.zhengjie.service.dto.TodoDoneDto;
import me.zhengjie.service.dto.TodoDoneQueryCriteria;
import me.zhengjie.service.mapstruct.TodoDoneMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.SecurityPermission;
import java.util.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://eladmin.vip
* @description 服务实现
* @author chenjie
* @date 2023-04-11
**/
@Service
@RequiredArgsConstructor
public class TodoDoneServiceImpl implements TodoDoneService {

    private final TodoDoneRepository todoDoneRepository;
    private final TodoDoneMapper todoDoneMapper;

    @Override
    public Map<String,Object> queryAll(TodoDoneQueryCriteria criteria, Pageable pageable){
        String username = SecurityUtils.getCurrentUser().getUsername();//获取用户名
        Long currentUserId = SecurityUtils.getCurrentUserId();//获取用户名id

//        criteria.setDeployeeName(username);
        criteria.setDeployeeNo(currentUserId.intValue());
        Page<TodoDone> page = todoDoneRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(todoDoneMapper::toDto));
    }

    @Override
    public List<TodoDoneDto> queryAll(TodoDoneQueryCriteria criteria){
        return todoDoneMapper.toDto(todoDoneRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public TodoDoneDto findById(Integer id) {
        TodoDone todoDone = todoDoneRepository.findById(id).orElseGet(TodoDone::new);
        ValidationUtil.isNull(todoDone.getId(),"TodoDone","id",id);
        return todoDoneMapper.toDto(todoDone);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TodoDoneDto create(TodoDone resources) {
        int i = RandomUtil.randomInt(5);
        resources.setId(i);//待办已办的消息id由hutool工具包的RandomUtil类生成随机数
        return todoDoneMapper.toDto(todoDoneRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(TodoDone resources) {
        TodoDone todoDone = todoDoneRepository.findById(resources.getId()).orElseGet(TodoDone::new);
        ValidationUtil.isNull( todoDone.getId(),"TodoDone","id",resources.getId());
        todoDone.copy(resources);
        todoDoneRepository.save(todoDone);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            todoDoneRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<TodoDoneDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (TodoDoneDto todoDone : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("标题", todoDone.getTitle());
            map.put("内容", todoDone.getContent());
            map.put("截至时间", todoDone.getDeadline());
            map.put("创建时间", todoDone.getCreatedAt());
            map.put("更新时间", todoDone.getUpdatedAt());
            map.put("状态", todoDone.getStatus());
            map.put("类型", todoDone.getType());
            map.put("被告知的员工名称", todoDone.getDeployeeName());
            map.put("被告知的员工编号", todoDone.getDeployeeNo());
            map.put(" isdelete",  todoDone.getIsdelete());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
