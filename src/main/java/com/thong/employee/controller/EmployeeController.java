package com.thong.employee.controller;

import com.thong.employee.constant.ApiVersion;
import com.thong.employee.dto.request.ConvertEmployeeHierarchyInput;
import com.thong.employee.dto.response.EmployeeHierarchyTree;
import com.thong.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/" + ApiVersion.VERSION_1 + "/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/convert-to-hierarchy-tree")
    public EmployeeHierarchyTree convertEmployeeHierarchy(@RequestBody ConvertEmployeeHierarchyInput dto) {
        return employeeService.convertEmployeeHierarchy(dto);
    }

}
