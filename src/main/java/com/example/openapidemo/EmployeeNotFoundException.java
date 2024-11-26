package com.example.openapidemo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException (int employeeId) {
        super("Сотрудник с ID " + employeeId + " не найден");
    }
}