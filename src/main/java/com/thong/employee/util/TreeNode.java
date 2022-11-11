package com.thong.employee.util;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public CycleData<TreeNode<T>> getCycleData() {
        Set<TreeNode<T>> visitedNodes = new HashSet<>();
        boolean hasCycle = dfs(this, visitedNodes);
        return new CycleData<>(hasCycle, visitedNodes);
    }

    private boolean dfs(TreeNode<T> currentNode, Set<TreeNode<T>> visitedNodes) {
        if (visitedNodes.contains(currentNode)) {
            return true;
        }

        visitedNodes.add(currentNode);

        boolean hasCycle = false;
        for (TreeNode<T> childNode : currentNode.getChildNodes()) {
            hasCycle = hasCycle || dfs(childNode, visitedNodes);
        }
        return hasCycle;
    }

    @Getter
    @Setter
    public static class CycleData<T> {

        private boolean hasCycle;
        private Set<T> nodesInCycle;

        public CycleData(boolean hasCycle, Set<T> nodesInCycle) {
            this.hasCycle = hasCycle;
            this.nodesInCycle = nodesInCycle;
        }

    }

}
