package com.traveloka.payctx.application.service.impl;

import com.traveloka.payctx.application.exceptions.ConfigParserException;
import com.traveloka.payctx.application.request.MultiPaymentConfigPayload;
import com.traveloka.payctx.application.request.MultiPaymentConfigRequest;
import com.traveloka.payctx.application.service.MultiPaymentConfigParser;
import com.traveloka.payctx.common.PaymentMethod;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author sandeepandey
 */
@Service
@Slf4j
public class SimpleMultiPaymentConfigParser implements MultiPaymentConfigParser {

  private static final String DEFAULT_CONFIG_NAME = "default";

  @Override
  public void parseMultiPaymentConfig(MultiPaymentConfigRequest request) throws ConfigParserException {

    Objects.requireNonNull(request, "Multi Payment Config Object Can not be null...");

    String configName = request.getConfigName();
    if (Objects.isNull(configName)) {
      log.error("Config Name is absent. Assigning default config name");
      request.setConfigName(DEFAULT_CONFIG_NAME);
    }

    String version = request.getVersionId();
    if (version == null) {
      log.error("Version can not be null...");
      throw new ConfigParserException();
    }
    verifyPaymentMethodName(request.getPayload());
    verifyPaymentMethodNameDuplication(request.getPayload());
    verifyPaymentMethodNameChain(request.getPayload());
  }

  private void verifyPaymentMethodNameChain(List<MultiPaymentConfigPayload> configPayloads) {
    Map<String, MultiPaymentConfigPayload> nameMap =
        configPayloads.stream().collect(Collectors.toMap(
            MultiPaymentConfigPayload::getPrimaryMethod, Function.identity())
        );

    for (String primaryMethodName : nameMap.keySet()) {
      MultiPaymentConfigPayload tmpModel = nameMap.get(primaryMethodName);
      List<String> secondaryPaymentMethods = tmpModel.getNextMethods();
      for (String secondaryPaymentMethodName : secondaryPaymentMethods) {
        // if this is not present in primary
        if (!nameMap.containsKey(secondaryPaymentMethodName)) {
          log.error("Payment Method ::: {} is not present in config file <> Rejecting", secondaryPaymentMethodName);
          throw new ConfigParserException();
        }
      }
    }
  }

  private void verifyPaymentMethodNameDuplication(List<MultiPaymentConfigPayload> configPayloads) {
    Set<String> methodNameSet = new HashSet<>();
    for (MultiPaymentConfigPayload mpcp : configPayloads) {
      if (!methodNameSet.contains(mpcp.getPrimaryMethod())) {
        methodNameSet.add(mpcp.getPrimaryMethod());
        continue;
      }
      log.error("Found Duplicate Payment Method ::: Rejecting...");
      throw new ConfigParserException();
    }
  }

  private void verifyPaymentMethodName(List<MultiPaymentConfigPayload> configObjects) {
    for (MultiPaymentConfigPayload configPayload : configObjects) {
      for (String methodName : configPayload.getNextMethods()) {
        PaymentMethod.from(methodName).orElseThrow(ConfigParserException::new);
      }
      PaymentMethod.from(configPayload.getPrimaryMethod()).orElseThrow(ConfigParserException::new);
    }
    log.info("Payment Method Verification done successfully.");
  }
}
