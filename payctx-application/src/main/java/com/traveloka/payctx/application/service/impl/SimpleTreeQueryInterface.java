package com.traveloka.payctx.application.service.impl;

import com.traveloka.payctx.application.domain.ReadOnlyTreeState;
import com.traveloka.payctx.application.domain.TreeNode;
import com.traveloka.payctx.common.model.TreeQueryResult;
import com.traveloka.payctx.application.exceptions.InvalidQueryException;
import com.traveloka.payctx.common.PaymentMethod;

import com.traveloka.payctx.specification.TreeQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author sandeepandey
 */
@Service
@Slf4j
public class SimpleTreeQueryInterface implements TreeQuery {

  private final SimpleTreeOperator simpleTreeOperator;
  private ReadOnlyTreeState readOnlyTreeState;

  public SimpleTreeQueryInterface(SimpleTreeOperator simpleTreeOperator) {
    this.simpleTreeOperator = simpleTreeOperator;
  }

  public void refreshState() {
    readOnlyTreeState = simpleTreeOperator.toReadOnlyState();
  }

  @Override
  public TreeQueryResult nextMethodsByPrimaryMethod(PaymentMethod primaryMethodName, int uptoLevel) {
    log.info("Querying Next Eligible Payment Methods for Primary Payment Method :::{} upto level :::{}", primaryMethodName, uptoLevel);
    Objects.requireNonNull(primaryMethodName);
    if (uptoLevel == 0) {
      uptoLevel = 1;
    }
    refreshState();
    TreeNode nodeToOperate = readOnlyTreeState.getNodeByName(primaryMethodName.name());
    if (uptoLevel > nodeToOperate.getMaxLevel()) {
      log.error("Node::{} have only level upto::{} , Rejecting query", primaryMethodName.name(), nodeToOperate.getMaxLevel());
      throw new InvalidQueryException("Invalid Query");
    }

    List<Collection<String>> result = new ArrayList<>();
    int finalUptoLevel = uptoLevel;
    accumulateResult(nodeToOperate, levelCount -> levelCount == finalUptoLevel, result::add);
    return new TreeQueryResult(result);
  }

  @Override
  public Boolean checkIfCombinationPresent(PaymentMethod primary, PaymentMethod secondary) {
    Objects.requireNonNull(primary);
    Objects.requireNonNull(secondary);
    if (primary.name().equals(secondary.name())) {
      log.error("Invalid Chain ::: {} --> {}", primary.name(), secondary.name());
      return false;
    }
    refreshState();
    TreeNode nodeToOperate = readOnlyTreeState.getNodeByName(primary.name());
    return checkIfEdgeExist(nodeToOperate, secondary.name());
  }

  @Override
  public void upgradeTree(PaymentMethod forNode) {
    simpleTreeOperator.levelUp(forNode.name());
  }


  private boolean checkIfEdgeExist(TreeNode nodeToOperate, String nodeNameToLookFor) {
    Queue<String> queue = new LinkedList<>();
    queue.add(nodeToOperate.getName());
    while (!queue.isEmpty()) {
      String tmpNode = queue.poll();
      if (tmpNode.equals(nodeNameToLookFor)) {
        return true;
      }
      queue.addAll(nodeToOperate.getNextNodes().stream().map(TreeNode::getName).collect(Collectors.toList()));
    }
    return false;
  }


  private void accumulateResult(TreeNode nodeToOperate, Predicate<Integer> predicate, Consumer<Collection<String>> consumer) {

    if (nodeToOperate == null) {
      return;
    }

    if (predicate.test(nodeToOperate.getCurrentLevel())) {
      consumer.accept(nodeToOperate.getCurrentPath());
    }

    for (TreeNode tn : nodeToOperate.getNextNodes()) {
      accumulateResult(tn, predicate, consumer);
    }
  }
}
