package com.thong.employee.controller;

import com.thong.employee.constant.ApiVersion;
import com.thong.employee.dto.request.SaveEmployeeInput;
import com.thong.employee.dto.response.EmployeeTree;
import com.thong.employee.entity.Employee;
import com.thong.employee.mapper.InputMapper;
import com.thong.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/" + ApiVersion.VERSION_1 + "/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private InputMapper inputMapper;

    @GetMapping
    public ResponseEntity<EmployeeTree> getEmployeeTree() {
        return ResponseEntity.ok(employeeService.getEmployeeTree());
    }

    @GetMapping("/{id}/managers")
    public ResponseEntity<List<String>> findManagersOfEmployee(@PathVariable String id) {
        return ResponseEntity.ok(employeeService.findManagersOfEmployee(id));
    }

    @PostMapping
    public ResponseEntity<Object> saveEmployees(@RequestBody SaveEmployeeInput input) {
        List<Employee> employees = inputMapper.map(input);
        employeeService.saveEmployees(employees);
        return ResponseEntity.ok().build();
    }

}
