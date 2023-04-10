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

import me.zhengjie.domain.Salary;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.repository.SalaryRepository;
import me.zhengjie.service.SalaryService;
import me.zhengjie.service.dto.SalaryDto;
import me.zhengjie.service.dto.SalaryQueryCriteria;
import me.zhengjie.service.mapstruct.SalaryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
* @date 2023-04-10
**/
@Service
@RequiredArgsConstructor
public class SalaryServiceImpl implements SalaryService {

    private final SalaryRepository salaryRepository;
    private final SalaryMapper salaryMapper;

    @Override
    public Map<String,Object> queryAll(SalaryQueryCriteria criteria, Pageable pageable){
        Page<Salary> page = salaryRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(salaryMapper::toDto));
    }

    @Override
    public List<SalaryDto> queryAll(SalaryQueryCriteria criteria){
        return salaryMapper.toDto(salaryRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public SalaryDto findById(Integer deployNo) {
        Salary salary = salaryRepository.findById(deployNo).orElseGet(Salary::new);
        ValidationUtil.isNull(salary.getDeployNo(),"Salary","deployNo",deployNo);
        return salaryMapper.toDto(salary);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SalaryDto create(Salary resources) {
        return salaryMapper.toDto(salaryRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Salary resources) {
        Salary salary = salaryRepository.findById(resources.getDeployNo()).orElseGet(Salary::new);
        ValidationUtil.isNull( salary.getDeployNo(),"Salary","id",resources.getDeployNo());
        salary.copy(resources);
        salaryRepository.save(salary);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer deployNo : ids) {
            salaryRepository.deleteById(deployNo);
        }
    }

    @Override
    public void download(List<SalaryDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (SalaryDto salary : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("基础薪资", salary.getBasic());
            map.put("绩效", salary.getPerformance());
            map.put("总薪资", salary.getTotal());
            map.put("是否删除", salary.getIsdelete());
            map.put("创建人", salary.getCreateby());
            map.put("更新人", salary.getUpdateby());
            map.put("员工姓名", salary.getDeployeeName());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}