package com.thong.employee.util;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TreeNode<T> {

    private T data;

    private List<TreeNode<T>> childNodes = new ArrayList<>();

    public TreeNode(T data) {
        this.data = data;
    }

    public void addChildNodes(List<TreeNode<T>> childNodes) {
        this.childNodes.addAll(childNodes);
    }

    public void addChildNode(TreeNode<T> childNode) {
        this.childNodes.add(childNode);
    }

    public boolean hasNoChildNode() {
        return this.childNodes.isEmpty();
    }

}
