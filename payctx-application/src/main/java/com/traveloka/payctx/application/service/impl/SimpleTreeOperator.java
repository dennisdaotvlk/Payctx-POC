package com.traveloka.payctx.application.service.impl;

import com.traveloka.payctx.application.domain.ReadOnlyTreeState;
import com.traveloka.payctx.application.domain.TreeNode;
import com.traveloka.payctx.application.domain.TreeStateObj;
import com.traveloka.payctx.application.request.MultiPaymentConfigPayload;
import com.traveloka.payctx.application.domain.TreeLevel;
import com.traveloka.payctx.specification.TreeOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author sandeepandey
 */

@Service
@Slf4j
public class SimpleTreeOperator implements TreeOperator<List<MultiPaymentConfigPayload>> {

  private TreeStateObj stateObj;


  @Override
  public void preInit() {
    stateObj = TreeStateObj.empty();
  }

  @Override
  public void initState(List<MultiPaymentConfigPayload> inputConfigs) {
    Objects.requireNonNull(inputConfigs, "Invalid Input :: [null]");
    inputConfigs.forEach(this::addNewState);
  }

  private void addNewState(MultiPaymentConfigPayload stateInput) {
    Objects.requireNonNull(stateInput);

    if (stateObj.isNodePresent(stateInput.getPrimaryMethod())) {
      log.error("Found Duplication State ::{}", stateInput.getPrimaryMethod());
      return;
    }

    TreeNode rootNode = new TreeNode
        .Builder()
        .withInitialInput(stateInput.getPrimaryMethod(), stateInput.getNextMethods())
        .build();

    runSelfBalanceRoutine(rootNode);
    updateLevels(rootNode,new Visited());
    stateObj.addNode(rootNode, rootNode::getName);
    log.info("Tree Node {} Added successfully", stateInput.getPrimaryMethod());
  }

  //--------------------------------------------------------------------------------------------------------------

  /**
   * Self balancing routine, Ideally should be called after every update
   *
   * @param fromNode start node
   */
  private void runSelfBalanceRoutine(TreeNode fromNode) {
    TreeLevel levelInfo = TreeLevel.empty();
    runSelfBalanceRoutineInner(fromNode, new Visited(), levelInfo);
    stateObj.addLevel(fromNode::getName, levelInfo);
  }

  //--------------------------------------------------------------------------------------------------------------

  /**
   * Self balancing routine, Ideally should be called after every update
   *
   * @param fromNode   start node
   * @param visitedSet look up set MAINTAINED for duplication
   * @param levelInfo  level info object we have
   */
  private void runSelfBalanceRoutineInner(TreeNode fromNode, Set<String> visitedSet, TreeLevel levelInfo) {
    if (fromNode == null || visitedSet.contains(fromNode.getName())) {
      return;
    }

    List<TreeNode> nextNodes = fromNode.getNextNodes();

    if (nextNodes == null || nextNodes.isEmpty()) {
      // this is leaf node , record it
      levelInfo.addItem(fromNode);
      return;
    }

    visitedSet.add(fromNode.getName());
    int level = Integer.MIN_VALUE;
    for (TreeNode node : nextNodes) {
      node.getCurrentPath().addAll(fromNode.getCurrentPath());
      // lets double check
      node.getCurrentPath().add(node.getName());
      node.setCurrentLevel(fromNode.getCurrentLevel() + 1);
      runSelfBalanceRoutineInner(node, visitedSet, levelInfo);
    }
  }

  private int updateLevels(TreeNode forNode, Visited visited) {

    if (forNode == null || visited.contains(forNode.getName())) {
      return 0;
    }

    List<TreeNode> nodes = forNode.getNextNodes();
    if (nodes == null) {
      nodes = new ArrayList<>();
    }

    if (nodes.isEmpty()) {
      forNode.setMaxLevel(1);
      return 1;
    }

    int level = Integer.MIN_VALUE;
    visited.add(forNode.getName());
    for (TreeNode node : nodes) {
      level = Math.max(level, updateLevels(node, visited));
    }
    forNode.setMaxLevel(level + 1);
    return level + 1;

  }

  //--------------------------------------------------------------------------------------------------------------------


  @Override
  public void levelUp(String root, int byLevel) {
    if (byLevel > 1) {
      throw new UnsupportedOperationException("Level > 1 upgrade not supported yet. Please use overloaded method.");
    }

    if (stateObj.isNodeAbsent(root)) {
      log.error("Invalid Root {}", root);
      return;
    }

    log.info("Upgrading Root {} from bottomUp", root);
    levelUpInternal(stateObj.nodeByName(root).orElseThrow(), stateObj.levelByName(root).orElseThrow());
  }

  //---------------------------------------------------------------------------------------------------------------------

  /**
   * Method responsible for handling tree upgrade routine.
   *
   * @param root      node on which level up need to performed
   * @param lastLevel last level of subtree rooted by root. Next level would be added below last level
   */
  private void levelUpInternal(TreeNode root, TreeLevel lastLevel) {
    Objects.requireNonNull(root);
    Objects.requireNonNull(lastLevel);
    promoteLevel(root, lastLevel);
  }

  //---------------------------------------------------------------------------------------------------------------------

  /**
   * Method to promote given level
   *
   * @param root      helper node
   * @param lastLevel promotion need to be applied here.
   */
  private void promoteLevel(TreeNode root, TreeLevel lastLevel) {

    for (TreeNode tmpNode : lastLevel.asCollection()) {
      promoteNodeAt(tmpNode, root, new Visited());
    }

    //adjust tree
    runSelfBalanceRoutine(root);
    updateLevels(root,new Visited());

  }

  //---------------------------------------------------------------------------------------------------------------------

  /**
   * Method to promote given level
   *
   * @param nodeToPromoted node that need to be promoted
   * @param root           root of subtree currently being operated for
   */
  private void promoteNodeAt(TreeNode nodeToPromoted, TreeNode root, Visited visitorSet) {
    Set<String> resultSet = stateObj.levelByName(nodeToPromoted.getName()).orElseThrow()
        .asCollection()
        .stream()
        .map(TreeNode::getCurrentPath)
        .flatMap(Collection::stream)
        .collect(Collectors.toSet());
    chainWith(nodeToPromoted, resultSet, i -> !nodeToPromoted.getCurrentPath().contains(i));
  }

  private void chainWith(TreeNode onNode, Set<String> nodeToBeChained, Predicate<String> predicate) {
    List<TreeNode> transformedNode = nodeToBeChained.stream().filter(predicate).map(TreeNode::new).collect(Collectors.toList());
    onNode.setNextNodes(transformedNode);
  }


  //--------------------------------------------------------------------------------------------

  /**
   * Private Helper class for checking invalid tree transitions
   */
  private static class Visited extends HashSet<String> {

  }

  //--------------------------------------------------------------------------------------------
  public ReadOnlyTreeState toReadOnlyState() {
    return ReadOnlyTreeState.of(stateObj.getReadOnlyRootState(), stateObj.getReadOnlyLevelState());
  }
}
