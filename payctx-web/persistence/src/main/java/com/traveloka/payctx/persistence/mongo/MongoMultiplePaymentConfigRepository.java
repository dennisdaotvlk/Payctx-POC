package com.traveloka.payctx.persistence.mongo;

import com.traveloka.payctx.persistence.PersistableMultiPaymentConfig;
import com.traveloka.payctx.persistence.PersistableMultiPaymentConfigEntry;
import com.traveloka.payctx.persistence.MultiplePaymentConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author sandeepandey
 */
@Repository
public class MongoMultiplePaymentConfigRepository implements MultiplePaymentConfigRepository {

  private final MongoTemplate mongoTemplate;

  @Autowired
  public MongoMultiplePaymentConfigRepository(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }


  @Override
  public PersistableMultiPaymentConfig readConfig() {
    Query query = new Query();
    return mongoTemplate.findOne(query, PersistableMultiPaymentConfig.class);
  }

  @Override
  public void save(PersistableMultiPaymentConfig persistableMultiplePaymentConfig) {
    mongoTemplate.save(persistableMultiplePaymentConfig);
  }

  @Override
  public void cleanup() {
    // TODO impl
  }


}
