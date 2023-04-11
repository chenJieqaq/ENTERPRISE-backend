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
* @date 2023-04-12
**/
@Entity
@Data
@Table(name="notification")
public class Notification implements Serializable {

    @Id
    @Column(name = "`id`")
    @ApiModelProperty(value = "通知id")
    private String id;

    @Column(name = "`title`")
    @ApiModelProperty(value = "通知标题")
    private String title;

    @Column(name = "`content`")
    @ApiModelProperty(value = "通知内容")
    private String content;

    @Column(name = "`cteated_at`")
    @ApiModelProperty(value = "创建时间")
    private String cteatedAt;

    @Column(name = "`updated_at`")
    @ApiModelProperty(value = "更新时间")
    private String updatedAt;

    @Column(name = "`deployee_no`")
    @ApiModelProperty(value = "被告知的员工编号")
    private Integer deployeeNo;

    @Column(name = "`deplpyee_name`")
    @ApiModelProperty(value = "被告知的员工名称")
    private String deplpyeeName;

    @Column(name = "`cteatedby`")
    @ApiModelProperty(value = "创建者")
    private String cteatedby;

    @Column(name = "`updatedby`")
    @ApiModelProperty(value = "更新者")
    private Timestamp updatedby;

    public void copy(Notification source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
