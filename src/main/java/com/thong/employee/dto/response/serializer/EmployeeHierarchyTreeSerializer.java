package com.thong.employee.dto.response.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.thong.employee.dto.response.EmployeeTree;
import com.thong.employee.dto.response.EmployeeTree.EmployeeDto;
import com.thong.employee.util.TreeNode;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class EmployeeHierarchyTreeSerializer extends JsonSerializer<EmployeeTree> {

    @Override
    public void serialize(EmployeeTree tree,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {

        TreeNode<EmployeeDto> rootNode = tree.getRootNode();

        jsonGenerator.writeStartObject();
        writeNode(rootNode, jsonGenerator);
        jsonGenerator.writeEndObject();
    }

    private void writeNode(TreeNode<EmployeeDto> parentNode, JsonGenerator jsonGenerator) throws IOException {
        jsonGenerator.writeFieldName(parentNode.getData().getId());

        jsonGenerator.writeStartObject();
        for (var childNode : parentNode.getChildNodes()) {
            writeNode(childNode, jsonGenerator);
        }
        jsonGenerator.writeEndObject();
    }

}
