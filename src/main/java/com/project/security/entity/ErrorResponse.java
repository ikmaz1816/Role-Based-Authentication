package com.project.security.entity;

import lombok.*;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class ErrorResponse
{
    HttpStatus status;
    String message;
}
