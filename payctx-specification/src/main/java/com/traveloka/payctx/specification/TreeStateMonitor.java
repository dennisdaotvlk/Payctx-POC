package com.traveloka.payctx.specification;

import java.time.Instant;

/**
 * @author sandeepandey
 */
public interface TreeStateMonitor {
  boolean isTreeBalance();

  int updateQueryCount();

  int refreshCount();

  int readQueryCount();

  int failureQueryCount();

  Instant lastActivity();


}
