package com.traveloka.payctx.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author sandeepandey
 */

public enum PaymentMethod {
  UANGKU("UANGKU"),
  OTC("OTC"),
  BANK_TRANSFER("BANK_TRANSFER"),
  MYCARD("MYCARD"),
  PAYLATER("PAYLATER"),
  TRANSFER("Transfer"),
  BCA_KLIKPAY("BCA KlikPay"),
  MANDIRI_CLICKPAY("Mandiri ClickPay"),
  MANDIRI_CLICKPAY_WEB("Mandiri ClickPay"),
  CREDIT_CARD("Credit Card"),
  CIMB_CLICKS("CIMB Clicks"),
  PAYPAL("Paypal"),
  DOKU("Doku"),
  INAPAY("Inapay"),
  VIRTUAL_ACCOUNT("Transfer via Virtual Account"),
  MANDIRI_EMONEY("Mandiri e-cash"),
  POINT("Traveloka Point"),
  MANDIRI_DEBIT("Mandiri Debit"),
  BNI_DEBIT("BNI Debit"),
  PAYMENT_POINT("Indomaret"),
  ALFAMART("Alfamart"),
  MOCK_PAYMENT("Mock Payment"),
  AFFILIATE("Affiliate"),
  AFFILIATE_TRANSFER("Affiliate Transfer"),
  OTHERS("Others"),
  MAYBANK2U("Maybank2u"),
  ONETWOTHREE_ATM("ATM"),
  ONETWOTHREE_COUNTER("Counter Payment"),
  ONETWOTHREE_WEBPAY("Internet Banking"),
  ONEPAY("OnePay"),
  PAYOO("Counter Payment"),
  DRAGON_PAY("Dragon Pay"),
  SEVEN_ELEVEN("7-Eleven"),
  COINS("Coins"),
  MOLPAY_COUNTER("Counter Payment"),
  MOLPAY_EBANKING("Internet Banking"),
  POSTOFFICE("Post Office"),
  AIRPAY("Airpay"),
  PAYNAMICS("Paynamics"),
  CASH("Cash On Delivery"),
  WALLET_CASH("Wallet cash balance"),
  CREDIT_LOAN("Credit loan"),
  VIRTUAL_CREDIT("Virtual Credit"),
  DIRECT_DEBIT("Direct Debit"),
  CORPORATE_TRANSFER("Corporate Transfer"),
  CORPORATE_MANUAL("Manual Corporate"),
  LINKAJA("LinkAja");


  private static final Map<String, PaymentMethod> map = new HashMap<>();

  private String paymentMethodName;

  static {
    for (PaymentMethod pme : PaymentMethod.values()) {
      map.put(pme.paymentMethodName, pme);
    }
  }


  PaymentMethod(String paymentMethodName) {
    this.paymentMethodName = paymentMethodName;
  }

  public static Optional<PaymentMethod> from(String paymentMethodName) {

    return Optional.ofNullable(map.get(paymentMethodName));
  }
}
