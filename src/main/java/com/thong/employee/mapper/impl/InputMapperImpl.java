package com.thong.employee.mapper.impl;

import com.thong.employee.dto.request.SaveEmployeeInput;
import com.thong.employee.entity.Employee;
import com.thong.employee.mapper.InputMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InputMapperImpl implements InputMapper {

    @Override
    public List<Employee> map(SaveEmployeeInput input) {
        return input.getEmployeeDtos().stream()
                .map(employeeDto -> {
                    Employee employee = new Employee();
                    employee.setId(employeeDto.getId());
                    employee.setManagerId(employeeDto.getManagerId());
                    return employee;
                })
                .collect(Collectors.toList());
    }

}
