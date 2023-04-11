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
import java.io.Serializable;

/**
* @website https://eladmin.vip
* @description /
* @author chenjie
* @date 2023-04-12
**/
@Entity
@Data
@Table(name="deployee_information")
public class DeployeeInformation implements Serializable {

    @Id
    @Column(name = "`id`")
    @ApiModelProperty(value = "id")
    private String id;

    @Column(name = "`deployee_no`")
    @ApiModelProperty(value = "deployeeNo")
    private String deployeeNo;

    @Column(name = "`deployee_name`")
    @ApiModelProperty(value = "deployeeName")
    private String deployeeName;

    @Column(name = "`iphone`")
    @ApiModelProperty(value = "iphone")
    private String iphone;

    @Column(name = "`address`")
    @ApiModelProperty(value = "address")
    private String address;

    @Column(name = "`email`")
    @ApiModelProperty(value = "email")
    private String email;

    @Column(name = "`sex`")
    @ApiModelProperty(value = "sex")
    private String sex;

    @Column(name = "`create_by`")
    @ApiModelProperty(value = "createBy")
    private String createBy;

    @Column(name = "`create_time`")
    @ApiModelProperty(value = "createTime")
    private String createTime;

    @Column(name = "`update_by`")
    @ApiModelProperty(value = "updateBy")
    private String updateBy;

    @Column(name = "`update_time`")
    @ApiModelProperty(value = "updateTime")
    private String updateTime;

    @Column(name = "`is_delete`")
    @ApiModelProperty(value = "isDelete")
    private String isDelete;

    public void copy(DeployeeInformation source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
