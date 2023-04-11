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
package me.zhengjie.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://eladmin.vip
* @description /
* @author chenjie
* @date 2023-04-11
**/
@Data
public class TodoDoneDto implements Serializable {

    /** id */
    private Integer id;

    /** 标题 */
    private String title;

    /** 内容 */
    private String content;

    /** 截至时间 */
    private Timestamp deadline;

    /** 创建时间 */
    private String createdAt;

    /** 更新时间 */
    private String updatedAt;

    /** 状态 */
    private String status;

    /** 类型 */
    private String type;

    /** 被告知的员工名称 */
    private String deployeeName;

    /** 被告知的员工编号 */
    private Integer deployeeNo;

    private Long isdelete;
}