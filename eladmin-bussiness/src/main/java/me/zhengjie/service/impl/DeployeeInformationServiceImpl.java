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

import me.zhengjie.domain.DeployeeInformation;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.repository.DeployeeInformationRepository;
import me.zhengjie.service.DeployeeInformationService;
import me.zhengjie.service.dto.DeployeeInformationDto;
import me.zhengjie.service.dto.DeployeeInformationQueryCriteria;
import me.zhengjie.service.mapstruct.DeployeeInformationMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.core.util.IdUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
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
public class DeployeeInformationServiceImpl implements DeployeeInformationService {

    private final DeployeeInformationRepository deployeeInformationRepository;
    private final DeployeeInformationMapper deployeeInformationMapper;

    @Override
    public Map<String,Object> queryAll(DeployeeInformationQueryCriteria criteria, Pageable pageable){
        Page<DeployeeInformation> page = deployeeInformationRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(deployeeInformationMapper::toDto));
    }

    @Override
    public List<DeployeeInformationDto> queryAll(DeployeeInformationQueryCriteria criteria){
        return deployeeInformationMapper.toDto(deployeeInformationRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public DeployeeInformationDto findById(String id) {
        DeployeeInformation deployeeInformation = deployeeInformationRepository.findById(id).orElseGet(DeployeeInformation::new);
        ValidationUtil.isNull(deployeeInformation.getId(),"DeployeeInformation","id",id);
        return deployeeInformationMapper.toDto(deployeeInformation);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DeployeeInformationDto create(DeployeeInformation resources) {
        resources.setId(IdUtil.simpleUUID()); 
        return deployeeInformationMapper.toDto(deployeeInformationRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DeployeeInformation resources) {
        DeployeeInformation deployeeInformation = deployeeInformationRepository.findById(resources.getId()).orElseGet(DeployeeInformation::new);
        ValidationUtil.isNull( deployeeInformation.getId(),"DeployeeInformation","id",resources.getId());
        deployeeInformation.copy(resources);
        deployeeInformationRepository.save(deployeeInformation);
    }

    @Override
    public void deleteAll(String[] ids) {
        for (String id : ids) {
            deployeeInformationRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<DeployeeInformationDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (DeployeeInformationDto deployeeInformation : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put(" deployeeNo",  deployeeInformation.getDeployeeNo());
            map.put(" deployeeName",  deployeeInformation.getDeployeeName());
            map.put(" iphone",  deployeeInformation.getIphone());
            map.put(" address",  deployeeInformation.getAddress());
            map.put(" email",  deployeeInformation.getEmail());
            map.put(" sex",  deployeeInformation.getSex());
            map.put(" createBy",  deployeeInformation.getCreateBy());
            map.put(" createTime",  deployeeInformation.getCreateTime());
            map.put(" updateBy",  deployeeInformation.getUpdateBy());
            map.put(" updateTime",  deployeeInformation.getUpdateTime());
            map.put(" isDelete",  deployeeInformation.getIsDelete());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}