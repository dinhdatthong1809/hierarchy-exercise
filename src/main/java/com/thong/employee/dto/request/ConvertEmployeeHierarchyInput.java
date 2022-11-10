package com.thong.employee.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.thong.employee.dto.request.deserializer.ConvertEmployeeHierarchyInputDeserializer;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonDeserialize(using = ConvertEmployeeHierarchyInputDeserializer.class)
public class ConvertEmployeeHierarchyInput {

    private List<EmployeeDto> employeeDtos;

    public ConvertEmployeeHierarchyInput() {

    }

    public ConvertEmployeeHierarchyInput(List<EmployeeDto> employeeDtos) {
        this.employeeDtos = employeeDtos;
    }

    @Getter
    @Setter
    public static class EmployeeDto {

        private String id;
        private String managerId;

        public EmployeeDto(String id, String managerId) {
            this.id = id;
            this.managerId = managerId;
        }

    }
    
}
