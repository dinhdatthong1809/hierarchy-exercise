package com.thong.employee.mapper.impl;

import com.thong.employee.dto.request.EmployeeCreationInput;
import com.thong.employee.entity.Employee;
import com.thong.employee.mapper.InputMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InputMapperImpl implements InputMapper {

    @Override
    public List<Employee> map(EmployeeCreationInput input) {
        return input.getEmployeeDtos().stream()
                .map(employeeDto -> {
                    Employee employee = new Employee();
                    employee.setId(employeeDto.getId());
                    employee.setManagerId(employeeDto.getManagerId());
                    return employee;
                })
                .toList();
    }

}
