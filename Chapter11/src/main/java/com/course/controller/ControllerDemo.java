package com.course.controller;


import com.course.model.Employee;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//Log4j: 日志工具注解
@Log4j
//spring-boot注解
@RestController
//swagger注解
@Api(value = "v1", tags = "项目前环境测试接口")
@RequestMapping(value = "/v1")
public class ControllerDemo {
    //获取一个执行sql语句的对象
    @Autowired
    private SqlSessionTemplate template;

    @GetMapping(value = "/getEmployeeCount")
    @ApiOperation(value = "查询员工总数", httpMethod = "GET")
    public int getEmployeeCount() {
        return template.selectOne("getEmployeeCount");
    }

    @PostMapping("/addEmployee")
    @ApiOperation(value = "添加员工信息", httpMethod = "POST")
    // 返回增加数量：int
    public int addEmployee(@RequestBody Employee Emp) {
        //需要用户传入参数
        return template.insert("addEmployee", Emp);
    }

    @PostMapping("/updateEmployee")
    @ApiOperation(value = "更新员工信息", httpMethod = "POST")
    // 返回更新数量：int
    public int updateEmployee(@RequestBody Employee Emp) {
        //需要用户传入参数
        return template.update("updateEmployee", Emp);
    }

    @GetMapping("/deleteEmployee")
    @ApiOperation(value = "删除员工信息", httpMethod = "GET")
    // 返回更新数量：int
    public int deleteEmployee(@RequestParam int emp_id) {
        //需要用户传入参数
        return template.delete("deleteEmployee", emp_id);
    }
}
