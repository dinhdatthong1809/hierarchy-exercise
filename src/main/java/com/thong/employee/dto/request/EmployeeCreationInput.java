package com.thong.employee.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.thong.employee.dto.request.deserializer.ConvertEmployeeHierarchyInputDeserializer;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@JsonDeserialize(using = ConvertEmployeeHierarchyInputDeserializer.class)
public class EmployeeCreationInput {

    @NotEmpty(message = "Employee list must not be empty")
    private List<@Valid EmployeeDto> employeeDtos;

    public EmployeeCreationInput() {

    }

    public EmployeeCreationInput(List<EmployeeDto> employeeDtos) {
        this.employeeDtos = employeeDtos;
    }

    @Getter
    @Setter
    public static class EmployeeDto {

        @NotEmpty(message = "Employee id must not be null or empty")
        private String id;
        @NotEmpty(message = "Employee managerId must not be null or empty")
        private String managerId;

        public EmployeeDto(String id, String managerId) {
            this.id = id;
            this.managerId = managerId;
        }

    }
    
}
