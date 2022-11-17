package com.thong.employee.service.impl;

import com.thong.employee.dto.response.EmployeeTree;
import com.thong.employee.entity.Employee;
import com.thong.employee.exception.BusinessException;
import com.thong.employee.exception.ResourceNotFoundException;
import com.thong.employee.exception.enums.ErrorCode;
import com.thong.employee.repository.EmployeeRepository;
import com.thong.employee.service.EmployeeService;
import com.thong.employee.service.EmployeeTreeBuilderService;
import com.thong.employee.service.EmployeeValidationService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        return employeeTreeBuilderService.buildEmployeeTree(employees);
    }

    @Override
    public List<String> findManagersOfEmployee(String employeeId) {
        getEmployee(employeeId);
        return employeeRepository.findManagersOfEmployee(employeeId);
    }

    @Override
    public List<String> findRootManagerIds(List<Employee> employees) {
        Set<String> employeeIds = employees.stream().map(Employee::getId).collect(Collectors.toSet());
        return employees.stream()
                .map(Employee::getManagerId)
                .filter(managerId -> !employeeIds.contains(managerId))
                .toList();
    }

    /**
     * I chose to reset the employee list
     * because allowing user to update a part of employee list would be more complicated
     */
    @Override
    @Transactional
    public void saveEmployees(List<Employee> employees) {
        validate(employees);
        employeeRepository.deleteAll();
        employeeRepository.saveAll(employees);
    }

    private Employee getEmployee(String employeeId) {
        return employeeRepository.findById(employeeId).orElseThrow(ResourceNotFoundException::of);
    }

    private void validate(List<Employee> employees) {
        List<String> rootManagerIds = findRootManagerIds(employees);
        if (CollectionUtils.size(rootManagerIds) > 1) {
            throw BusinessException.of(ErrorCode.MULTIPLE_ROOT_MANAGERS, rootManagerIds);
        }

        List<String> employeeCycle = employeeValidationService.findEmployeeCycle(employees);
        if (CollectionUtils.isNotEmpty(employeeCycle)) {
            throw BusinessException.of(ErrorCode.DETECTED_EMPLOYEE_CYCLE, employeeCycle);
        }
    }

}
