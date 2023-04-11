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
    @ApiModelProperty(value = "标题")
    private String title;

    @Column(name = "`content`")
    @ApiModelProperty(value = "内容")
    private String content;

    @Column(name = "`deadline`")
    @ApiModelProperty(value = "截至时间")
    private Timestamp deadline;

    @Column(name = "`created_at`")
    @ApiModelProperty(value = "创建时间")
    private String createdAt;

    @Column(name = "`updated_at`")
    @ApiModelProperty(value = "更新时间")
    private String updatedAt;

    @Column(name = "`status`")
    @ApiModelProperty(value = "状态")
    private String status;

    @Column(name = "`type`")
    @ApiModelProperty(value = "类型")
    private String type;

    @Column(name = "`deployee_name`")
    @ApiModelProperty(value = "被告知的员工名称")
    private String deployeeName;

    @Column(name = "`deployee_no`")
    @ApiModelProperty(value = "被告知的员工编号")
    private Integer deployeeNo;

    @Column(name = "`isdelete`")
    @ApiModelProperty(value = "isdelete")
    private Long isdelete;

    public void copy(TodoDone source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
