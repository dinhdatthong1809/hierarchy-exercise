package com.thong.employee.service;

import com.thong.employee.dto.request.GetEmployeeHierarchyTreeCriteria;
import com.thong.employee.dto.request.SaveEmployeeInput;
import com.thong.employee.dto.response.EmployeeHierarchyTree;

public interface EmployeeService {

    EmployeeHierarchyTree getEmployeeHierarchyTree(GetEmployeeHierarchyTreeCriteria criteria);

    void saveEmployees(SaveEmployeeInput dto);

}
