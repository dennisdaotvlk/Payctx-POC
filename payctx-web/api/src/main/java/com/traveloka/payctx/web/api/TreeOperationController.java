package com.traveloka.payctx.web.api;

import com.traveloka.payctx.application.request.ListPaymentCombinationRequest;
import com.traveloka.payctx.application.request.PaymentCombinationRequest;
import com.traveloka.payctx.application.request.TreeShiftUpRequest;
import com.traveloka.payctx.application.service.impl.SimpleTreeManager;
import com.traveloka.payctx.common.WebAdapter;
import com.traveloka.payctx.common.model.TreeQueryResult;
import com.traveloka.payctx.specification.TreeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Predicate;
import javax.annotation.PostConstruct;

/**
 * @author sandeepandey
 */
@RestController
@WebAdapter
public class TreeOperationController {

  private final TreeQuery treeQuery;

  private final SimpleTreeManager treeManager;

  @Autowired
  public TreeOperationController(TreeQuery treeQuery, SimpleTreeManager treeManager) {
    this.treeQuery = treeQuery;
    this.treeManager = treeManager;
  }

  @GetMapping("/warmUp")
  public ResponseEntity<String> warmUp() {
    treeManager.warmUp();
    return ResponseEntity.ok().body("SUCCESS");

  }

  @PostMapping(path = "/check", consumes = "application/json")
  @ResponseBody
  public ResponseEntity<String> testCombination(@RequestBody PaymentCombinationRequest request) {
    return ResponseEntity.ok().body(treeQuery.checkIfCombinationPresent(request.getPrimary(), request.getSecondary()) ? "EXIST" : "NOT_EXIST");
  }

  @PostMapping(path = "/listCombo", consumes = "application/json", produces = "application/json")
  @ResponseBody
  public ResponseEntity<TreeQueryResult> getAllCombination(@RequestBody ListPaymentCombinationRequest request) {
    return ResponseEntity.ok().body(treeQuery.nextMethodsByPrimaryMethod(request.getPrimary(), request.getUptoLevel(),
        s -> !s.equals(request.getPrimary().name())));
  }

  @PostMapping(value = "/shiftUpTree", consumes = "application/json")
  @ResponseBody
  public ResponseEntity<String> shiftUpTree(@RequestBody TreeShiftUpRequest request) {
    treeQuery.upgradeTree(request.getPrimary());
    return ResponseEntity.ok().body("SUCCESS");
  }

}
