package com.example.openapidemo;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class EmployeeController {
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
        return repository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));
    }

    @PostMapping("/v1/employees")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee newEmployee) {
        Employee savedEmployee = repository.save(newEmployee);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }

    @Operation(summary = "Обновить данные о сотруднике", description = "Обновить данные о сотруднике. В ответе возвращается объект Employee c полями id, firstName и lastName.")
    @PutMapping("/v1/employees/{employeeId}")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee, @PathVariable int employeeId) {
        return repository.findById(employeeId)
                .map(existingEmployee -> {
                    existingEmployee.setFirstName(employee.getFirstName());
                    existingEmployee.setLastName(employee.getLastName());
                    Employee updatedEmployee = repository.save(existingEmployee);
                    return ResponseEntity.ok(updatedEmployee);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Удалить сотрудника", description = "В ответе возвращается пустое тело запроса.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Сотрудник успешно удалён"),
            @ApiResponse(responseCode = "404", description = "Сотрудник не найден")
    })
    @DeleteMapping("/v1/employees/{employeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteEmployee(@PathVariable int employeeId) {
        repository.findById(employeeId).ifPresentOrElse(
                employee -> repository.deleteById(employeeId),
                () -> {
                    throw new EmployeeNotFoundException(employeeId);
                }
        );
        return ResponseEntity.noContent().build();
    }
}
