package com.project.security.dto;

import com.project.security.enums.Role;
public record EmployeeDTO(long id, String firstname, String lastname, Role role) {

}
