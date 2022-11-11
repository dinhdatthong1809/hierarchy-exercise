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
        var input1 = List.of(
                new Employee("Pete", "Nick"),
                new Employee("Barbara", "Nick"),
                new Employee("Nick", "Sophie"),
                new Employee("Sophie", "Pete")
        );

        var input2 = List.of(
                new Employee("Barbara", "Nick"),
                new Employee("Nick", "Barbara")
        );

        var input3 = List.of(
                new Employee("Pete", "Nick"),
                new Employee("Barbara", "Nick"),
                new Employee("Nick", "Sophie"),
                new Employee("Sophie", "Jonas")
        );

        return Stream.of(
                Arguments.of(input1, new String[]{"Pete", "Nick", "Sophie", "Pete"}),
                Arguments.of(input2, new String[]{"Barbara", "Nick", "Barbara"}),
                Arguments.of(input3, new String[]{})
        );
    }

    @ParameterizedTest
    @MethodSource("testFindEmployeeCycle")
    void testFindEmployeeCycle(List<Employee> employees, String[] expectedCycle) {
        List<String> employeeCycle = employeeValidationService.findEmployeeCycle(employees);
        Assertions.assertArrayEquals(expectedCycle, employeeCycle.toArray());
    }

}
