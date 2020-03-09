package com.traveloka.payctx.configuration;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author sandeepandey
 */
@SpringBootApplication(scanBasePackages = "com.traveloka.*")
public class PayctxApplication {
  public static void main(String[] args) {
    SpringApplication.run(PayctxApplication.class, args);
  }
}
