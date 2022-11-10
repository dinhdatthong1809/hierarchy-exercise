package com.thong.employee.service;

import com.thong.employee.dto.request.ConvertEmployeeHierarchyInput;
import com.thong.employee.dto.response.EmployeeHierarchyTree;

public interface EmployeeService {

    EmployeeHierarchyTree convertEmployeeHierarchy(ConvertEmployeeHierarchyInput dto);

}
