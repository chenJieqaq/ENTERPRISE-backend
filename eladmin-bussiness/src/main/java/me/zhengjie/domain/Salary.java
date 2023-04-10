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
import java.math.BigDecimal;
import java.io.Serializable;

/**
* @website https://eladmin.vip
* @description /
* @author chenjie
* @date 2023-04-10
**/
@Entity
@Data
@Table(name="salary")
public class Salary implements Serializable {

    @Id
    @Column(name = "`deploy_no`")
    @ApiModelProperty(value = "员工编号")
    private Integer deployNo;

    @Column(name = "`basic`")
    @ApiModelProperty(value = "基础薪资")
    private BigDecimal basic;

    @Column(name = "`performance`")
    @ApiModelProperty(value = "绩效")
    private BigDecimal performance;

    @Column(name = "`total`")
    @ApiModelProperty(value = "总薪资")
    private BigDecimal total;

    @Column(name = "`isdelete`")
    @ApiModelProperty(value = "是否删除")
    private String isdelete;

    @Column(name = "`createby`")
    @ApiModelProperty(value = "创建人")
    private String createby;

    @Column(name = "`updateby`")
    @ApiModelProperty(value = "更新人")
    private String updateby;

    @Column(name = "`deployee_name`")
    @ApiModelProperty(value = "员工姓名")
    private String deployeeName;

    @Column(name = "`update_at`")
    @ApiModelProperty(value = "跟新时间")
    private Timestamp updateAt;

    @Column(name = "`create_at`")
    @ApiModelProperty(value = "创建时间")
    private Timestamp createAt;

    public void copy(Salary source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
