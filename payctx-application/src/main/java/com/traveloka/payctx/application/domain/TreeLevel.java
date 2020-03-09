package com.traveloka.payctx.application.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * @author sandeepandey
 */
public class TreeLevel {

  private Queue<TreeNode> underlyingQueue;

  private TreeLevel() {
    this(new LinkedList<>());
  }

  private TreeLevel(Queue<TreeNode> queue) {
    this.underlyingQueue = queue;
  }

  public static TreeLevel empty() {
    return new TreeLevel();
  }

  public static TreeLevel fromList(List<TreeNode> nodes) {
    Queue<TreeNode> queue = new LinkedList<>(nodes);
    return new TreeLevel(queue);
  }

  public static TreeLevel fromSet(Set<TreeNode> nodes) {
    Queue<TreeNode> queue = new LinkedList<>(nodes);
    return new TreeLevel(queue);
  }

  public Collection<TreeNode> asCollection() {
    return Collections.unmodifiableCollection(this.underlyingQueue);
  }

  public List<TreeNode> asList() {
    return List.copyOf(this.underlyingQueue);
  }

  public Set<TreeNode> asSet() {
    return Set.copyOf(this.underlyingQueue);
  }

  public void addItem(TreeNode nodeToAdd) {
    this.underlyingQueue.add(nodeToAdd);
  }

  public List<TreeNode> copyOf() {
    List<TreeNode> resultList = new ArrayList<>();
    for(TreeNode tn : asList()) {
      resultList.add(tn.copyOf());
    }
    return resultList;
  }


}
