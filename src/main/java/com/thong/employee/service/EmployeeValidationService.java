package com.thong.employee.service;

import com.thong.employee.entity.Employee;

import java.util.List;

public interface EmployeeValidationService {

    List<String> findEmployeeCycle(List<Employee> employees);

}
