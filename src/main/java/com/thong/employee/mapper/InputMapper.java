package com.thong.employee.mapper;

import com.thong.employee.dto.request.EmployeeCreationInput;
import com.thong.employee.entity.Employee;

import java.util.List;

public interface InputMapper {

    List<Employee> map(EmployeeCreationInput input);

}
