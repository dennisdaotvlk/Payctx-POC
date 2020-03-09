package com.traveloka.payctx.web.api;

import com.traveloka.payctx.common.WebAdapter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sandeepandey
 */
@WebAdapter
@RestController
public class HealthCheckController {

  @GetMapping(path = "/soft/health")
  public String tellHealthStatus() {
    return "[Healthy]";
  }
}
