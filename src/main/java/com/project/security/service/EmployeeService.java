package com.project.security.service;

import com.project.security.config.JwtUtils;
import com.project.security.dto.EmployeeDTO;
import com.project.security.entity.Employee;
import com.project.security.entity.EmployeeLogin;
import com.project.security.exception.EmployeeNotFoundException;
import com.project.security.interfaces.EmployeeInterface;
import com.project.security.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService implements EmployeeInterface {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private AuthenticationManager authentication;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public EmployeeDTO register(Employee employee) {
        Employee employee1=Employee.builder().id(employee.getId())
                .firstname(employee.getFirstname())
                .lastname(employee.getLastname())
                .password(passwordEncoder.encode(employee.getPassword()))
                .role(employee.getRole())
                .email(employee.getEmail())
                .build();
        Employee employee2=this.employeeRepository.save(employee1);
        return new EmployeeDTO(employee.getId(), employee2.getFirstname(),
                employee2.getLastname(),employee2.getRole());
    }

    @Override
    public String login(EmployeeLogin employeeLogin) {
        authentication.authenticate(
                new UsernamePasswordAuthenticationToken(
                        employeeLogin.getEmail(),
                        employeeLogin.getPassword()
                )
        );
        return generateToken(employeeLogin.getEmail());
    }

    public String generateToken(String username)
    {
        UserDetails userDetails= employeeRepository.findByEmail(username).orElseThrow(()->
                new EmployeeNotFoundException("Employee Not Found"));
        return jwtUtils.generateTokenWithoutClaims(userDetails);
    }
}
