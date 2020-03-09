package com.traveloka.payctx.web.api;

import com.traveloka.payctx.common.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sandeepandey
 */
@WebAdapter
@RestController
@RequiredArgsConstructor
public class HomeController {

  @GetMapping(path = "/home")
  public String sayHello() {
    return "Hello";
  }
}
