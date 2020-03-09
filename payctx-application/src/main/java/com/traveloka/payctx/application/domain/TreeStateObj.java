package com.traveloka.payctx.application.domain;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author sandeepandey
 */
@Slf4j
public class TreeStateObj {

  private Map<String, TreeNode> rootMap;
  private Map<String, TreeLevel> levelStateMap;

  private TreeStateObj() {
    rootMap = new HashMap<>();
    levelStateMap = new HashMap<>();
  }

  public static TreeStateObj empty() {
    return new TreeStateObj();
  }

  public TreeStateObj addNode(TreeNode nodeToAdd, Supplier<String> keySupplier) {
    rootMap.put(keySupplier.get(), nodeToAdd);
    return this;
  }

  public TreeStateObj addLevel(Supplier<String> keySupplier, TreeLevel levelToAdd) {
    levelStateMap.put(keySupplier.get(), levelToAdd);
    return this;
  }


  public Optional<TreeNode> nodeByName(String name) {
    Objects.requireNonNull(name);
    return Optional.ofNullable(rootMap.get(name));
  }

  public Optional<TreeLevel> levelByName(String name) {
    Objects.requireNonNull(name);
    return Optional.ofNullable(levelStateMap.get(name));
  }


  public boolean isNodePresent(String nodeName) {
    return rootMap.containsKey(nodeName);
  }

  public boolean isNodeAbsent(String nodeName) {
    return !isNodePresent(nodeName);
  }

  public Map<String, TreeNode> getReadOnlyRootState() {
    Map<String, TreeNode> readOnlyMap = new HashMap<>();
    for (String pm : rootMap.keySet()) {
      readOnlyMap.put(pm, rootMap.get(pm).copyOf());
    }
    return readOnlyMap;
  }

  public Map<String, TreeLevel> getReadOnlyLevelState() {
    Map<String, TreeLevel> readOnlyMap = new HashMap<>();
    for (String pm : levelStateMap.keySet()) {
      TreeLevel level = levelStateMap.get(pm);
      readOnlyMap.put(pm, TreeLevel.fromList(level.copyOf()));
    }
    return readOnlyMap;
  }
}
