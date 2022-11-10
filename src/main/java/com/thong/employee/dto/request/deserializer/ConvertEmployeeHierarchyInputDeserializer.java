package com.thong.employee.dto.request.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.thong.employee.dto.request.SaveEmployeeInput;
import com.thong.employee.dto.request.SaveEmployeeInput.EmployeeDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ConvertEmployeeHierarchyInputDeserializer extends StdDeserializer<SaveEmployeeInput> {

    public ConvertEmployeeHierarchyInputDeserializer() {
        this(null);
    }

    public ConvertEmployeeHierarchyInputDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public SaveEmployeeInput deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        List<EmployeeDto> employeeDtos = new ArrayList<>();

        Iterator<String> fieldNames = node.fieldNames();
        while (fieldNames.hasNext()) {
            String key = fieldNames.next();
            String value = node.get(key).asText();
            employeeDtos.add(new EmployeeDto(key, value));
        }

        return new SaveEmployeeInput(employeeDtos);
    }
}