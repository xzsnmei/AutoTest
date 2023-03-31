package com.course.model;

import lombok.Data;

@Data
public class GetUserInfoCase {
    private int CaseId;
    private int userId;
    private String expected;
}
