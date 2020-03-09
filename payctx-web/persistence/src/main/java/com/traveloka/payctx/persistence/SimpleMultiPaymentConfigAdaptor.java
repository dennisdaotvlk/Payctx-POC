package com.traveloka.payctx.persistence;

import com.traveloka.payctx.application.request.MultiPaymentConfigPayload;
import com.traveloka.payctx.application.request.MultiPaymentConfigRequest;
import com.traveloka.payctx.application.service.MultiPaymentConfigParser;
import com.traveloka.payctx.application.service.MultiPaymentUseCaseAdaptor;
import com.traveloka.payctx.common.builder.GenericBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author sandeepandey
 */
@Component
@Slf4j
public class SimpleMultiPaymentConfigAdaptor implements MultiPaymentUseCaseAdaptor {

  private final MultiplePaymentConfigRepository repository;
  private final MultiPaymentConfigParser configParser;

  @Autowired
  public SimpleMultiPaymentConfigAdaptor(MultiplePaymentConfigRepository repository, MultiPaymentConfigParser configParser) {
    this.repository = repository;
    this.configParser = configParser;
  }


  @Override
  public void handleSaveConfigCommand(MultiPaymentConfigRequest request) {
    log.info("Parsing Config Input ....");
    configParser.parseMultiPaymentConfig(request);
    log.info("Transforming Input ...");
    PersistableMultiPaymentConfig dbObject = new PersistableMultiPaymentConfig();
    List<PersistableMultiPaymentConfigEntry> configEntry = request.getPayload()
        .stream().map(i -> GenericBuilder.of(PersistableMultiPaymentConfigEntry::new)
            .with(PersistableMultiPaymentConfigEntry::setName, i.getPrimaryMethod())
            .with(PersistableMultiPaymentConfigEntry::setNext, i.getNextMethods())
            .build()).collect(Collectors.toList());
    dbObject.setConfigs(configEntry);
    dbObject.setName(request.getConfigName());
    dbObject.setVersionId(request.getVersionId());
    dbObject.setStatus(request.getConfigState().name());
    log.info("Saving Config into the DB.");
    repository.save(dbObject);
    log.info("Config Saved Successfully.");
  }

  @Override
  public List<MultiPaymentConfigPayload> getMultiplePaymentConfig() {
    log.info("Trying to read config from persistence storage ...");
    PersistableMultiPaymentConfig multiPaymentConfig = repository.readConfig();
    return multiPaymentConfig.getConfigs().stream().map(i -> GenericBuilder.of(MultiPaymentConfigPayload::new)
        .with(MultiPaymentConfigPayload::setPrimaryMethod, i.getName())
        .with(MultiPaymentConfigPayload::setNextMethods, i.getNext())
        .build()).collect(Collectors.toList());
  }
}
