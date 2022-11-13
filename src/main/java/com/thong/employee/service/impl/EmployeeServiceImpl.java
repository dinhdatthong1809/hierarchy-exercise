package com.thong.employee.service.impl;

import com.thong.employee.dto.response.EmployeeTree;
import com.thong.employee.entity.Employee;
import com.thong.employee.exception.BusinessException;
import com.thong.employee.repository.EmployeeRepository;
import com.thong.employee.service.EmployeeService;
import com.thong.employee.service.EmployeeTreeBuilderService;
import com.thong.employee.service.EmployeeValidationService;
import org.apache.commons.collections4.CollectionUtils;
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

    @Override
    public EmployeeTree getEmployeeTree() {
        List<Employee> employees = employeeRepository.findAll();
        validate(employees);
        return employeeTreeBuilderService.buildEmployeeTree(employees);
    }

    @Override
    public List<String> findManagersOfEmployee(String employeeId) {
        getEmployee(employeeId);
        return employeeRepository.findManagersOfEmployee(employeeId);
    }

    /**
     * I chose to reset the employee list
     * because allowing user to update a part of employee list would be more complicated
     */
    @Override
    @Transactional
    public void saveEmployees(List<Employee> employees) {
        employeeRepository.deleteAll();
        employeeRepository.saveAll(employees);
    }

    private Employee getEmployee(String employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> BusinessException.of("Employee does not exist with id = " + employeeId));
    }

    private void validate(List<Employee> employees) {
        List<String> employeeCycle = employeeValidationService.findEmployeeCycle(employees);
        if (CollectionUtils.isNotEmpty(employeeCycle)) {
            throw BusinessException.of("The employee tree has cycle. Please check the cycle list: " + employeeCycle);
        }
    }

}
