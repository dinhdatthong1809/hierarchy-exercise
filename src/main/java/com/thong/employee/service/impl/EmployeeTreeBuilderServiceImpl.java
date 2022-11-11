package com.thong.employee.service.impl;

import com.thong.employee.dto.response.EmployeeTree;
import com.thong.employee.entity.Employee;
import com.thong.employee.exception.BusinessException;
import com.thong.employee.repository.EmployeeRepository;
import com.thong.employee.service.EmployeeTreeBuilderService;
import com.thong.employee.util.TreeNode;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmployeeTreeBuilderServiceImpl implements EmployeeTreeBuilderService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeTree buildEmployeeTree(List<Employee> employees) {
        TreeNode<EmployeeTree.EmployeeDto> rootNode = findRootManager();
        Map<String, List<Employee>> managerMap = convertToManagerMap(employees);
        appendChildNodes(rootNode, managerMap);

        return new EmployeeTree(rootNode);
    }

    private TreeNode<EmployeeTree.EmployeeDto> findRootManager() {
        String rootManagerId = findRootManagerId();
        EmployeeTree.EmployeeDto data = new EmployeeTree.EmployeeDto(rootManagerId);
        return new TreeNode<>(data);
    }

    private Map<String, List<Employee>> convertToManagerMap(List<Employee> employees) {
        return employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getManagerId,
                        Collectors.mapping(employee -> employee, Collectors.toList())
                ));
    }

    private String findRootManagerId() {
        List<String> rootManagerIds = employeeRepository.findRootManagerIds();
        if (rootManagerIds.size() > 1) {
            throw BusinessException.of("Should not contain more than 1 root manager in the employee tree. Root manager ids are " + rootManagerIds);
        }
        return rootManagerIds.get(0);
    }

    private void appendChildNodes(TreeNode<EmployeeTree.EmployeeDto> parentNode, Map<String, List<Employee>> managersMap) {
        List<Employee> subEmployees = managersMap.get(parentNode.getData().getId());
        if (CollectionUtils.isEmpty(subEmployees)) {
            return;
        }

        for (Employee subEmployee : subEmployees) {
            EmployeeTree.EmployeeDto childNodeData = new EmployeeTree.EmployeeDto(subEmployee.getId(), subEmployee.getManagerId());
            TreeNode<EmployeeTree.EmployeeDto> childNode = new TreeNode<>(childNodeData);

            parentNode.addChildNode(childNode);
            appendChildNodes(childNode, managersMap);
        }
    }

}
