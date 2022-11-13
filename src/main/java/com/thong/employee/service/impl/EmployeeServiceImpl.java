package com.thong.employee.service.impl;

import com.thong.employee.dto.request.SaveEmployeeInput;
import com.thong.employee.dto.response.EmployeeTree;
import com.thong.employee.entity.Employee;
import com.thong.employee.exception.BusinessException;
import com.thong.employee.mapper.InputMapper;
import com.thong.employee.repository.EmployeeRepository;
import com.thong.employee.service.EmployeeService;
import com.thong.employee.service.EmployeeTreeBuilderService;
import com.thong.employee.service.EmployeeValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeTreeBuilderService employeeTreeBuilderService;

    @Autowired
    private EmployeeValidationService employeeValidationService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private InputMapper inputMapper;

    @Override
    public EmployeeTree getEmployeeTree() {
        List<Employee> employees = employeeRepository.findAll();
        validate(employees);
        return employeeTreeBuilderService.buildEmployeeTree(employees);
    }

    @Override
    @Transactional
    public void saveEmployees(SaveEmployeeInput input) {
        List<Employee> employees = inputMapper.map(input);
        employeeRepository.deleteAll();
        employeeRepository.saveAll(employees);
    }

    private void validate(List<Employee> employees) {
        List<String> employeeCycle = employeeValidationService.findEmployeeCycle(employees);
        if (!employeeCycle.isEmpty()) {
            throw BusinessException.of("The employee tree has cycle. Please check the cycle list: " + employeeCycle);
        }
    }

}
