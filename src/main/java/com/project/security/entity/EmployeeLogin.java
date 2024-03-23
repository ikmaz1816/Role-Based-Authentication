package com.project.security.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class EmployeeLogin {
    String email;
    String password;
}
