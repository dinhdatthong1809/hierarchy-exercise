package com.thong.employee.service.impl;

import com.thong.employee.dto.request.GetEmployeeHierarchyTreeCriteria;
import com.thong.employee.dto.request.SaveEmployeeInput;
import com.thong.employee.dto.response.EmployeeHierarchyTree;
import com.thong.employee.entity.Employee;
import com.thong.employee.exception.BusinessException;
import com.thong.employee.mapper.InputMapper;
import com.thong.employee.repository.EmployeeRepository;
import com.thong.employee.service.EmployeeService;
import com.thong.employee.util.TreeNode;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private InputMapper inputMapper;

    @Override
    public EmployeeHierarchyTree getEmployeeHierarchyTree(GetEmployeeHierarchyTreeCriteria criteria) {
        List<Employee> employees = employeeRepository.findAll();
        Map<String, List<Employee>> managersMap = getManagersMap(employees);

        String rootManagerId = getRootManagerId();
        EmployeeHierarchyTree.EmployeeDto rootNodeData = new EmployeeHierarchyTree.EmployeeDto(rootManagerId);

        TreeNode<EmployeeHierarchyTree.EmployeeDto> rootNode = new TreeNode<>(rootNodeData);
        appendChildNodes(rootNode, managersMap);
        return new EmployeeHierarchyTree(rootNode);
    }

    @Override
    public void saveEmployees(SaveEmployeeInput input) {
        List<Employee> employees = inputMapper.map(input);
        employeeRepository.deleteAll();
        employeeRepository.saveAll(employees);
    }

    private void appendChildNodes(TreeNode<EmployeeHierarchyTree.EmployeeDto> parentNode, Map<String, List<Employee>> managersMap) {
        List<Employee> subEmployees = managersMap.get(parentNode.getData().getId());
        if (CollectionUtils.isEmpty(subEmployees)) {
            return;
        }

        for (Employee subEmployee : subEmployees) {
            EmployeeHierarchyTree.EmployeeDto childNodeData = new EmployeeHierarchyTree.EmployeeDto(subEmployee.getId(), subEmployee.getManagerId());
            TreeNode<EmployeeHierarchyTree.EmployeeDto> childNode = new TreeNode<>(childNodeData);
            parentNode.addChildNode(childNode);
            appendChildNodes(childNode, managersMap);
        }
    }

    private Map<String, List<Employee>> getManagersMap(List<Employee> employees) {
        return employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getManagerId,
                        Collectors.mapping(employee -> employee, Collectors.toList())
                ));
    }

    private String getRootManagerId() {
        List<String> rootManagerIds = employeeRepository.findRootManagerIds();
        if (rootManagerIds.size() > 1) {
            throw BusinessException.of("Should not contain more than 1 root manager in the employee tree. Root manager ids are " + rootManagerIds);
        }
        return rootManagerIds.get(0);
    }

}
