package com.thong.employee.mapper;

import com.thong.employee.dto.request.SaveEmployeeInput;
import com.thong.employee.entity.Employee;

import java.util.List;

public interface InputMapper {

    List<Employee> map(SaveEmployeeInput input);

}
