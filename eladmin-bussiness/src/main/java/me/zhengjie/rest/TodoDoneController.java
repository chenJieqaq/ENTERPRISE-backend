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
import me.zhengjie.domain.TodoDone;
import me.zhengjie.service.TodoDoneService;
import me.zhengjie.service.dto.TodoDoneQueryCriteria;
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
* @date 2023-04-11
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "todo_done管理")
@RequestMapping("/api/todoDone")
public class TodoDoneController {

    private final TodoDoneService todoDoneService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('todoDone:list')")
    public void exportTodoDone(HttpServletResponse response, TodoDoneQueryCriteria criteria) throws IOException {
        todoDoneService.download(todoDoneService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询todo_done")
    @ApiOperation("查询todo_done")
    @PreAuthorize("@el.check('todoDone:list')")
    public ResponseEntity<Object> queryTodoDone(TodoDoneQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(todoDoneService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增todo_done")
    @ApiOperation("新增todo_done")
    @PreAuthorize("@el.check('todoDone:add')")
    public ResponseEntity<Object> createTodoDone(@Validated @RequestBody TodoDone resources){
        return new ResponseEntity<>(todoDoneService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改todo_done")
    @ApiOperation("修改todo_done")
    @PreAuthorize("@el.check('todoDone:edit')")
    public ResponseEntity<Object> updateTodoDone(@Validated @RequestBody TodoDone resources){
        todoDoneService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除todo_done")
    @ApiOperation("删除todo_done")
    @PreAuthorize("@el.check('todoDone:del')")
    public ResponseEntity<Object> deleteTodoDone(@RequestBody Integer[] ids) {
        todoDoneService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}