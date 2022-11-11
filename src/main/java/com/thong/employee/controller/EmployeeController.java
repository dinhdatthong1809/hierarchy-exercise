package com.thong.employee.controller;

import com.thong.employee.constant.ApiVersion;
import com.thong.employee.dto.request.SaveEmployeeInput;
import com.thong.employee.dto.response.EmployeeTree;
import com.thong.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/" + ApiVersion.VERSION_1 + "/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<EmployeeTree> getEmployeeTree() {
        return ResponseEntity.ok(employeeService.getEmployeeTree());
    }

    @PostMapping
    public ResponseEntity<Object> saveEmployees(@RequestBody SaveEmployeeInput input) {
        employeeService.saveEmployees(input);
        return ResponseEntity.ok().build();
    }

}
