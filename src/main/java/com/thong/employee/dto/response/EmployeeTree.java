package com.thong.employee.dto.response;

import com.thong.employee.util.TreeNode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeTree {

    private TreeNode<EmployeeDto> rootNode;

    public EmployeeTree(TreeNode<EmployeeDto> rootNode) {
        this.rootNode = rootNode;
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

        public EmployeeDto(String id) {
            this(id, null);
        }

    }

}
