package com.thong.employee;

import com.thong.employee.entity.Employee;
import com.thong.employee.service.EmployeeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

@SpringBootTest
@Transactional
class FindManagersOfEmployeeIT {

    @Autowired
    private EmployeeService employeeService;

    private static Stream<Arguments> testFindManagersOfEmployee() {
        List<Employee> employees = List.of(
                new Employee("Pete", "Nick"),
                new Employee("Barbara", "Nick"),
                new Employee("Nick", "Sophie"),
                new Employee("Sophie", "Jonas")
        );

        return Stream.of(
                Arguments.of(employees, "Pete", List.of("Nick", "Sophie", "Jonas")),
                Arguments.of(employees, "Barbara", List.of("Nick", "Sophie", "Jonas")),
                Arguments.of(employees, "Nick", List.of("Sophie", "Jonas")),
                Arguments.of(employees, "Sophie", List.of("Jonas"))
        );
    }

    @ParameterizedTest
    @MethodSource("testFindManagersOfEmployee")
    void testFindManagersOfEmployee(List<Employee> employees, String employeeId, List<String> expectedManagers) {
        employeeService.saveEmployees(employees);
        List<String> actualManagers = employeeService.findManagersOfEmployee(employeeId);
        Assertions.assertIterableEquals(expectedManagers, actualManagers);
    }

}
