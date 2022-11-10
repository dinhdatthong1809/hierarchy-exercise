package com.thong.employee.service.impl;

import com.thong.employee.dto.request.ConvertEmployeeHierarchyInput;
import com.thong.employee.dto.request.ConvertEmployeeHierarchyInput.EmployeeDto;
import com.thong.employee.dto.response.EmployeeHierarchyTree;
import com.thong.employee.exception.BusinessException;
import com.thong.employee.service.EmployeeService;
import com.thong.employee.util.TreeNode;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Override
    public EmployeeHierarchyTree convertEmployeeHierarchy(ConvertEmployeeHierarchyInput input) {
        var managersMap = getManagersMap(input);
        var employeesMap = getEmployeesMap(input);

        var rootManagerId = getRootManagerId(managersMap.keySet(), employeesMap.keySet());

        var rootNode = new TreeNode<>(new EmployeeHierarchyTree.EmployeeDto(rootManagerId, null));
        appendChildNodes(rootNode, managersMap);
        return new EmployeeHierarchyTree(rootNode);
    }

    private void appendChildNodes(TreeNode<EmployeeHierarchyTree.EmployeeDto> parentNode, Map<String, List<EmployeeDto>> managersMap) {
        var subEmployees = managersMap.get(parentNode.getData().getId());
        if (CollectionUtils.isEmpty(subEmployees)) {
            return;
        }

        for (var subEmployee : subEmployees) {
            var childNode = new TreeNode<>(new EmployeeHierarchyTree.EmployeeDto(subEmployee.getId(), subEmployee.getManagerId()));
            parentNode.addChildNode(childNode);
            appendChildNodes(childNode, managersMap);
        }
    }

    private Map<String, List<EmployeeDto>> getManagersMap(ConvertEmployeeHierarchyInput input) {
        var map = new HashMap<String, List<EmployeeDto>>();

        for (var employeeDto : input.getEmployeeDtos()) {
            var key = employeeDto.getManagerId();
            if (map.containsKey(key)) {
                map.get(key).add(employeeDto);
            } else {
                var employeeDtos = new ArrayList<EmployeeDto>();
                employeeDtos.add(employeeDto);
                map.put(key, employeeDtos);
            }
        }

        return map;
    }

    private Map<String, EmployeeDto> getEmployeesMap(ConvertEmployeeHierarchyInput input) {
        return input.getEmployeeDtos().stream()
                .collect(Collectors.toMap(EmployeeDto::getId, Function.identity()));
    }

    private String getRootManagerId(Set<String> managerIds, Set<String> employeeIds) {
        var rootManagerIds = managerIds.stream()
                .filter(managerId -> !employeeIds.contains(managerId))
                .collect(Collectors.toList());

        if (rootManagerIds.size() > 1) {
            throw BusinessException.of("Should not contain more than 1 root manager in the employee tree. Root manager ids are " + rootManagerIds);
        }

        return rootManagerIds.get(0);
    }

}
