package com.traveloka.payctx.application.domain;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sandeepandey
 */
@Slf4j
public class ReadOnlyTreeState {
  private Map<String, TreeNode> rootMap;
  private Map<String, TreeLevel> levelStateMap;

  private ReadOnlyTreeState() {
    this(new HashMap<>(), new HashMap<>());
  }

  private ReadOnlyTreeState(Map<String, TreeNode> rootMap, Map<String, TreeLevel> levelStateMap) {
    this.rootMap = rootMap;
    this.levelStateMap = levelStateMap;
  }

  public static ReadOnlyTreeState of(Map<String, TreeNode> rootMap, Map<String, TreeLevel> levelStateMap) {
    return new ReadOnlyTreeState(rootMap, levelStateMap);
  }


  public TreeNode getNodeByName(String nodeName) {
    return rootMap.get(nodeName);
  }

  public TreeLevel getLastLevelByNodeName(String nodeName) {
    return levelStateMap.get(nodeName);
  }


}
