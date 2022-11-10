package com.thong.employee.dto.response.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.thong.employee.dto.response.EmployeeHierarchyTree;
import com.thong.employee.dto.response.EmployeeHierarchyTree.EmployeeDto;
import com.thong.employee.util.TreeNode;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class EmployeeHierarchyTreeSerializer extends JsonSerializer<EmployeeHierarchyTree> {

    @Override
    public void serialize(EmployeeHierarchyTree tree,
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
