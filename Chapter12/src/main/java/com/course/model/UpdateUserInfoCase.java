package com.course.model;

import lombok.Data;

@Data
public class UpdateUserInfoCase {

    private int CaseId;
    private int userId;
    private String userName;
    private String password;
    private String age;
    private String sex;
    private String permission;
    private String isDelete;
    private String expected;
}
