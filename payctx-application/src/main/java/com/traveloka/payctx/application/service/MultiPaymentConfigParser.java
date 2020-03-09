package com.traveloka.payctx.application.service;

import com.traveloka.payctx.application.exceptions.ConfigParserException;
import com.traveloka.payctx.application.request.MultiPaymentConfigRequest;

/**
 * @author sandeepandey
 */
public interface MultiPaymentConfigParser {

   void parseMultiPaymentConfig(MultiPaymentConfigRequest request) throws ConfigParserException;
}
