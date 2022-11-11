package com.thong.employee.service.impl;

import com.thong.employee.entity.Employee;
import com.thong.employee.exception.BusinessException;
import com.thong.employee.service.EmployeeValidationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmployeeValidationServiceImpl implements EmployeeValidationService {

    @Override
    public void validate(List<Employee> employees) {
        Map<String, String> employeeMap = employees.stream()
                .collect(Collectors.toMap(Employee::getId, Employee::getManagerId));

        List<String> employeeCycle = detectCycle(employeeMap);
        if (!employeeCycle.isEmpty()) {
            throw BusinessException.of("The employee tree has cycle. Please check the cycle list: " + employeeCycle);
        }
    }

    private List<String> detectCycle(Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            List<String> visitedNodes = new ArrayList<>();
            visitNextItem(entry.getKey(), map, visitedNodes);
            boolean hasCycle = visitedNodes.size() > 1 && visitedNodes.get(0).equals(visitedNodes.get(visitedNodes.size() - 1));
            if (hasCycle) {
                return visitedNodes;
            }
        }

        return List.of();
    }

    private void visitNextItem(String currentItem, Map<String, String> itemMap, List<String> visitedItems) {
        if (!itemMap.containsKey(currentItem)) {
            return;
        }

        visitedItems.add(currentItem);
        String nextItem = itemMap.get(currentItem);
        if (visitedItems.contains(nextItem)) {
            visitedItems.add(nextItem);
            return;
        }
        visitNextItem(nextItem, itemMap, visitedItems);
    }

}
