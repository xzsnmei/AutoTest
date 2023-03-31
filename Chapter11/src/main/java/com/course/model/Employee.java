package com.course.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Employee {
    private int emp_id;
    @ApiModelProperty("工号")
    private String emp_no;
    @ApiModelProperty("部门Id")
    private String dept_id;
    @ApiModelProperty("员工姓名")
    private String emp_name;
    @ApiModelProperty("员工年龄")
    private String emp_age;
    @ApiModelProperty("员工薪水")
    private float emp_salary;


}
