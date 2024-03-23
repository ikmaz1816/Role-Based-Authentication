package com.project.security.controller;

import com.project.security.dto.EmployeeDTO;
import com.project.security.entity.Employee;
import com.project.security.entity.EmployeeLogin;
import com.project.security.entity.TokenEntity;
import com.project.security.interfaces.EmployeeInterface;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("employee")

public class EmployeeController {

    @Autowired
    private EmployeeInterface employeeInterface;

    @PostMapping("/register")
    public ResponseEntity<EmployeeDTO> register(@NonNull @RequestBody Employee employee)
    {

        return ResponseEntity.status(HttpStatus.OK).body(this.employeeInterface.register(employee));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenEntity> login(@NonNull @RequestBody EmployeeLogin employeeLogin)
    {
        TokenEntity tokenEntity=new TokenEntity(employeeInterface.login(employeeLogin));
        return ResponseEntity.status(HttpStatus.OK).body(tokenEntity);
    }

    @GetMapping("/demo")
    @PreAuthorize("hasRole('ADMIN')")
    public String hello()
    {
        return "Hello World";
    }
}
