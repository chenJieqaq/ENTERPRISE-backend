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
package me.zhengjie.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://eladmin.vip
* @description /
* @author chenjie
* @date 2023-04-11
**/
@Entity
@Data
@Table(name="todo_done")
public class TodoDone implements Serializable {

    @Id
    @Column(name = "`id`")
    @ApiModelProperty(value = "id")
    private Integer id;

    @Column(name = "`title`")
    @ApiModelProperty(value = "title")
    private String title;

    @Column(name = "`content`")
    @ApiModelProperty(value = "content")
    private String content;

    @Column(name = "`deadline`")
    @ApiModelProperty(value = "deadline")
    private Timestamp deadline;

    @Column(name = "`created_at`")
    @ApiModelProperty(value = "createdAt")
    private String createdAt;

    @Column(name = "`updated_at`")
    @ApiModelProperty(value = "updatedAt")
    private String updatedAt;

    @Column(name = "`status`")
    @ApiModelProperty(value = "status")
    private String status;

    @Column(name = "`type`")
    @ApiModelProperty(value = "type")
    private String type;

    @Column(name = "`isdelete`")
    @ApiModelProperty(value = "isdelete")
    private String isdelete;

    public void copy(TodoDone source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
