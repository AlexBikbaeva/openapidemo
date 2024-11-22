package com.example.openapidemo;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class EmployeeController {
    @Autowired
    private EmployeeRepository repository;

    @Tag(name = "get", description = "GET-методы Employee API")
    @GetMapping("/v1/employees")
    public List<Employee> findAllEmployees() {
        return repository.findAll();
    }

    @Tag(name = "get", description = "GET-методы Employee API")
    @GetMapping("/v1/employees/{employeeId}")
    public Employee getEmployee(@Parameter(
            description = "ID сотрудника, данные по которому запрашиваются",
            required = true)
            @PathVariable int employeeId) {
        Employee employee = repository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("ID сотрудника не найден - " + employeeId));
        return employee;
    }

    @PostMapping("/v1/employees")
    public Employee addEmployee(@RequestBody Employee employee) {
        Employee newEmployee = repository.save(employee);
        return newEmployee;
    }

    @Operation(summary = "Обновить данные о сотруднике", description = "Обновить данные о сотруднике. В ответе возвращается объект Employee c полями id, firstName и lastName.")
    @PutMapping("/v1/employees")
    public Employee updateEmployee(@RequestBody Employee employee) {
        Employee theEmployee = repository.save(employee);
        return theEmployee;
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", content =
                    {@Content(mediaType = "application/json", schema = @Schema(implementation = Employee.class))}),
            @ApiResponse(responseCode = "404", description = "Сотрудник не найден", content = @Content)
    })

    @DeleteMapping("/v1/employees/{employeeId}")
    public String deleteEmployee(@PathVariable int employeeId) {
        repository.deleteById(employeeId);
        return "Удалён сотрудник с ID: " + employeeId;
    }

}
