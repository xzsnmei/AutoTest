package com.course.model;

import lombok.Data;

@Data
public class AddUserCase {
    private int CaseId;
    private String userName;
    private String password;
    private String age;
    private String sex;
    private String permission;
    private String isDelete;
    private String expected;
}
