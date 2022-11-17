package com.thong.employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thong.employee.dto.request.EmployeeCreationInput;
import com.thong.employee.dto.response.EmployeeTree;
import com.thong.employee.entity.Employee;
import com.thong.employee.mapper.InputMapper;
import com.thong.employee.service.EmployeeService;
import com.thong.employee.util.JsonUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@SpringBootTest
@Transactional
class ConvertToEmployeeTreeIT {

    private static final String TEST_CASES_FOLDER = "convert-to-employee-tree";

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private InputMapper inputMapper;

    private JacksonTester<EmployeeTree> jacksonTester;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, objectMapper);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/" + TEST_CASES_FOLDER + "/happy-cases/happy-cases.csv", numLinesToSkip = 1)
    void testHappyCases(String jsonFile) throws IOException {

        String input = JsonUtils.readJsonString(TEST_CASES_FOLDER + "/happy-cases/input/" + jsonFile);
        String expected = JsonUtils.readJsonString(TEST_CASES_FOLDER + "/happy-cases/expected/" + jsonFile);

        employeeService.saveEmployees(buildSaveEmployeeInput(input));
        EmployeeTree employeeTree = employeeService.getEmployeeTree();

        String actual = jacksonTester.write(employeeTree).getJson();
        Assertions.assertEquals(expected, actual);
    }

    private List<Employee> buildSaveEmployeeInput(String jsonString) {
        var map = JsonUtils.toMap(jsonString);

        var employeeDtos = map.entrySet().stream()
                .map(entry -> new EmployeeCreationInput.EmployeeDto((String) entry.getKey(), (String) entry.getValue()))
                .toList();

        return inputMapper.map(new EmployeeCreationInput(employeeDtos));
    }

}
