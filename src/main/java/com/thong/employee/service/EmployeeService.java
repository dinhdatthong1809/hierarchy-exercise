package com.thong.employee.service;

import com.thong.employee.dto.request.SaveEmployeeInput;
import com.thong.employee.dto.response.EmployeeTree;

public interface EmployeeService {

    EmployeeTree getEmployeeTree();

    void saveEmployees(SaveEmployeeInput dto);

}
