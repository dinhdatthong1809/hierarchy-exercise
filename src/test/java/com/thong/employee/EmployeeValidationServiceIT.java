package com.thong.employee;

import com.thong.employee.entity.Employee;
import com.thong.employee.service.EmployeeValidationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Stream;

@SpringBootTest
class EmployeeValidationServiceIT {

    @Autowired
    private EmployeeValidationService employeeValidationService;

    private static Stream<Arguments> testFindEmployeeCycle() {
        List<Employee> employees1 = List.of(
                new Employee("Pete", "Nick"),
                new Employee("Barbara", "Nick"),
                new Employee("Nick", "Sophie"),
                new Employee("Sophie", "Pete")
        );

        List<Employee> employees2 = List.of(
                new Employee("Barbara", "Nick"),
                new Employee("Nick", "Barbara")
        );

        List<Employee> employees3 = List.of(
                new Employee("Pete", "Nick"),
                new Employee("Barbara", "Nick"),
                new Employee("Nick", "Sophie"),
                new Employee("Sophie", "Jonas")
        );

        List<Employee> employees4 = List.of(
                new Employee("Nick", "Nick")
        );

        return Stream.of(
                Arguments.of(employees1, List.of("Pete", "Nick", "Sophie", "Pete")),
                Arguments.of(employees2, List.of("Barbara", "Nick", "Barbara")),
                Arguments.of(employees3, List.of()),
                Arguments.of(employees4, List.of("Nick", "Nick"))
        );
    }

    @ParameterizedTest
    @MethodSource("testFindEmployeeCycle")
    void testFindEmployeeCycle(List<Employee> employees, List<String> expectedEmployeeCycle) {
        List<String> actualEmployeeCycle = employeeValidationService.findEmployeeCycle(employees);
        Assertions.assertIterableEquals(expectedEmployeeCycle, actualEmployeeCycle);
    }

}
