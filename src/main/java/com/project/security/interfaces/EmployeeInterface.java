package com.project.security.interfaces;

import com.project.security.dto.EmployeeDTO;
import com.project.security.entity.Employee;
import com.project.security.entity.EmployeeLogin;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public interface EmployeeInterface {
    EmployeeDTO register(Employee employee);
    String login(EmployeeLogin employeeLogin);
}
