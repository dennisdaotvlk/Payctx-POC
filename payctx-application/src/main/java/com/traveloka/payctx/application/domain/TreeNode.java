package com.traveloka.payctx.application.domain;


import com.traveloka.payctx.common.builder.GenericBuilder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author sandeepandey
 */
@Setter
@Getter
public class TreeNode {

  private String name;
  private int currentLevel;
  private int maxLevel;
  private List<TreeNode> nextNodes;
  private Set<String> currentPath;

  private TreeNode() {

  }

  public TreeNode(String name) {
    this.name = name;
    this.currentLevel = 1;
    this.maxLevel = 1;
    this.currentPath = new HashSet<>();
    this.currentPath.add(name);
    this.nextNodes = new ArrayList<>();
  }


  private TreeNode(String name, int currentLevel, List<TreeNode> nextNodes, Set<String> parentSet) {
    this.name = name;
    this.currentLevel = currentLevel;
    this.nextNodes = nextNodes;
    this.currentPath = parentSet;

  }

  public TreeNode copyOf() {

    return GenericBuilder.of(TreeNode::new)
        .with(TreeNode::setName, this.getName())
        .with(TreeNode::setCurrentLevel, this.currentLevel)
        .with(TreeNode::setCurrentPath, this.currentPath)
        .with(TreeNode::setMaxLevel, this.maxLevel)
        .with(TreeNode::setNextNodes, this.nextNodes)
        .build();

  }

  public static class Builder {
    private String name;
    private List<String> nextNodes;

    public Builder withInitialInput(String paymentMethodName, List<String> nextPaymentMethods) {
      this.name = paymentMethodName;
      this.nextNodes = nextPaymentMethods;
      return this;
    }

    public TreeNode build() {

      List<TreeNode> childs = new ArrayList<>();
      for (String node : this.nextNodes) {
        childs.add(new TreeNode(node));
      }
      TreeNode root = new TreeNode(this.name);
      root.nextNodes = childs;
      return root;
    }

  }

  @Override
  public int hashCode() {
    return this.name.hashCode();
  }

  private static Supplier<Set<String>> toSelfPath(String withElement) {
    return () -> {
      Set<String> set = new HashSet<>();
      set.add(withElement);
      return set;
    };
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }

    if (!(obj instanceof TreeNode)) {
      return false;
    }

    TreeNode other = (TreeNode) obj;

    return this.name.equals(other.name);
  }

  public void updatePathWith(String name) {
    this.currentPath.add(name);
  }


  @Override
  public String toString() {
    return new StringBuilder()
        .append("[\n")
        .append("name ::")
        .append(name)
        .append("\n")
        .append("currentLevel::")
        .append(currentLevel)
        .append("\n")
        .append("maxLevel::")
        .append(maxLevel).append("\n")
        .append("parents::")
        .append(String.join(",", currentPath)).append("\n")
        .append("nextNodes::")
        .append(nextNodes.stream().map(i -> i.name).collect(Collectors.joining(","))).append("\n")
        .append("]")
        .toString();
  }


}
