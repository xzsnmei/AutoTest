package com.course.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户信息")
public class User {
    @ApiModelProperty("用户ID")
    private int id;
    @ApiModelProperty("用户名")
    private String userName;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("年龄")
    private String age;
    @ApiModelProperty("性别")
    private String sex;
    @ApiModelProperty("权限：0-管理员；1-普通用户（默认）")
    private String permission;
    @ApiModelProperty("是否删除：0-未删除；1-已删除")
    private String isDelete;

}
