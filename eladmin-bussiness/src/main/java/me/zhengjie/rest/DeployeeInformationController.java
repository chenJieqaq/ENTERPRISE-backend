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
package me.zhengjie.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.domain.DeployeeInformation;
import me.zhengjie.service.DeployeeInformationService;
import me.zhengjie.service.dto.DeployeeInformationQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://eladmin.vip
* @author chenjie
* @date 2023-04-12
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "Deployee_message管理")
@RequestMapping("/api/deployeeInformation")
public class DeployeeInformationController {

    private final DeployeeInformationService deployeeInformationService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('deployeeInformation:list')")
    public void exportDeployeeInformation(HttpServletResponse response, DeployeeInformationQueryCriteria criteria) throws IOException {
        deployeeInformationService.download(deployeeInformationService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询Deployee_message")
    @ApiOperation("查询Deployee_message")
    @PreAuthorize("@el.check('deployeeInformation:list')")
    public ResponseEntity<Object> queryDeployeeInformation(DeployeeInformationQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(deployeeInformationService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增Deployee_message")
    @ApiOperation("新增Deployee_message")
    @PreAuthorize("@el.check('deployeeInformation:add')")
    public ResponseEntity<Object> createDeployeeInformation(@Validated @RequestBody DeployeeInformation resources){
        return new ResponseEntity<>(deployeeInformationService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改Deployee_message")
    @ApiOperation("修改Deployee_message")
    @PreAuthorize("@el.check('deployeeInformation:edit')")
    public ResponseEntity<Object> updateDeployeeInformation(@Validated @RequestBody DeployeeInformation resources){
        deployeeInformationService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除Deployee_message")
    @ApiOperation("删除Deployee_message")
    @PreAuthorize("@el.check('deployeeInformation:del')")
    public ResponseEntity<Object> deleteDeployeeInformation(@RequestBody String[] ids) {
        deployeeInformationService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}