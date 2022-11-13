package com.thong.employee.service;

import com.thong.employee.dto.response.EmployeeTree;
import com.thong.employee.entity.Employee;

import java.util.List;

public interface EmployeeService {

    EmployeeTree getEmployeeTree();

    List<String> findManagersOfEmployee(String employeeId);

    void saveEmployees(List<Employee> employees);

}
