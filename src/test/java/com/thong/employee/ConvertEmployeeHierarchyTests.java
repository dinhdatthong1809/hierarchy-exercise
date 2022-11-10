package com.thong.employee;

import com.thong.employee.dto.request.ConvertEmployeeHierarchyInput;
import com.thong.employee.service.EmployeeService;
import com.thong.employee.util.JsonUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Collectors;

@SpringBootTest
class ConvertEmployeeHierarchyTests {

	private static final String TEST_CASES_PATH = "/convert-employee-hierarchy-tests-cases";

	@Autowired
	private EmployeeService employeeService;

	@ParameterizedTest
	@CsvFileSource(resources = TEST_CASES_PATH + "/happy-cases.csv", numLinesToSkip = 1, delimiterString = ";")
	void testHappyCases(String jsonString) {
		var input = buildConvertEmployeeHierarchyInput(jsonString);
		var result = employeeService.convertEmployeeHierarchy(input);
		Assertions.assertNotNull(result);
	}

	private ConvertEmployeeHierarchyInput buildConvertEmployeeHierarchyInput(String jsonString) {
		var map = JsonUtils.toMap(jsonString);

		var employeeDtos = map.entrySet().stream()
				.map(entry -> new ConvertEmployeeHierarchyInput.EmployeeDto((String) entry.getKey(), (String) entry.getValue()))
				.collect(Collectors.toList());

		return new ConvertEmployeeHierarchyInput(employeeDtos);
	}

}
