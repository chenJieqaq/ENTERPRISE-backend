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
import me.zhengjie.domain.Notification;
import me.zhengjie.service.NotificationService;
import me.zhengjie.service.dto.NotificationQueryCriteria;
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
@Api(tags = "notification管理")
@RequestMapping("/api/notification")
public class NotificationController {

    private final NotificationService notificationService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('notification:list')")
    public void exportNotification(HttpServletResponse response, NotificationQueryCriteria criteria) throws IOException {
        notificationService.download(notificationService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询notification")
    @ApiOperation("查询notification")
    @PreAuthorize("@el.check('notification:list')")
    public ResponseEntity<Object> queryNotification(NotificationQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(notificationService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增notification")
    @ApiOperation("新增notification")
    @PreAuthorize("@el.check('notification:add')")
    public ResponseEntity<Object> createNotification(@Validated @RequestBody Notification resources){
        return new ResponseEntity<>(notificationService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改notification")
    @ApiOperation("修改notification")
    @PreAuthorize("@el.check('notification:edit')")
    public ResponseEntity<Object> updateNotification(@Validated @RequestBody Notification resources){
        notificationService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除notification")
    @ApiOperation("删除notification")
    @PreAuthorize("@el.check('notification:del')")
    public ResponseEntity<Object> deleteNotification(@RequestBody String[] ids) {
        notificationService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}