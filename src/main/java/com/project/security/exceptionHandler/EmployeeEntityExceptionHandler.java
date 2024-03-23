package com.project.security.exceptionHandler;

import com.project.security.exception.EmployeeNotFoundException;
import com.project.security.entity.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@ResponseStatus
public class EmployeeEntityExceptionHandler {
    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ErrorResponse> getResponse(EmployeeNotFoundException employeeNotFoundException,
                                                     WebRequest request)
    {
        ErrorResponse errorResponse=new ErrorResponse(HttpStatus.NOT_FOUND, employeeNotFoundException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
