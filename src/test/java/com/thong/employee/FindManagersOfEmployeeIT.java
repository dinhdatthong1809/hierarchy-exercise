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

    private static Stream<Arguments> testFindRootManagerIds() {
        List<Employee> employees1 = List.of(
                new Employee("Pete", "Nick"),
                new Employee("Barbara", "Nick"),
                new Employee("Nick", "Sophie1"),
                new Employee("Sophie", "Jonas")
        );

        List<Employee> employees2 = List.of(
                new Employee("Pete", "Nick"),
                new Employee("Sophie", "Jonas")
        );

        List<Employee> employees3 = List.of(
                new Employee("Pete", "Nick")
        );

        List<Employee> employees4 = List.of(
                new Employee("A", "B"),
                new Employee("C", "D"),
                new Employee("E", "F")
        );

        return Stream.of(
                Arguments.of(employees1, List.of("Sophie1", "Jonas")),
                Arguments.of(employees2, List.of("Nick", "Jonas")),
                Arguments.of(employees3, List.of("Nick")),
                Arguments.of(employees4, List.of("B", "D", "F"))
        );
    }

    @ParameterizedTest
    @MethodSource("testFindRootManagerIds")
    void testFindRootManagerIds(List<Employee> employees, List<String> expectedRootManagerIds) {
        List<String> actualRootManagerIds = employeeService.findRootManagerIds(employees);
        Assertions.assertIterableEquals(expectedRootManagerIds, actualRootManagerIds);
    }

}
