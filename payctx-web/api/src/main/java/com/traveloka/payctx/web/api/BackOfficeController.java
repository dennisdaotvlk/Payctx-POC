package com.traveloka.payctx.web.api;

import com.traveloka.payctx.application.request.MultiPaymentConfigRequest;
import com.traveloka.payctx.application.service.MultiPaymentConfigUseCase;

import com.traveloka.payctx.application.service.impl.SimpleTreeManager;
import com.traveloka.payctx.common.WebAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sandeepandey
 */
@WebAdapter
@RestController(value = "/backoffice")
public class BackOfficeController {

  private final MultiPaymentConfigUseCase multiPaymentConfigUserCaseService;

  @Autowired
  public BackOfficeController(MultiPaymentConfigUseCase multiPaymentConfigUserCase) {
    this.multiPaymentConfigUserCaseService = multiPaymentConfigUserCase;
  }

  @PostMapping(path = "/store", consumes = "application/json")
  public void storeMultiPaymentConfig(@RequestBody MultiPaymentConfigRequest request) {
    multiPaymentConfigUserCaseService.handleSaveUseCase(request);
  }


}
