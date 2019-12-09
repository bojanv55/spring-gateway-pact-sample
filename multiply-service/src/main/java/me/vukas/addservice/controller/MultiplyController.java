package me.vukas.addservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MultiplyController {

  @GetMapping("multiply")
  public Integer multiply(Integer a, Integer b) throws InterruptedException {
    Thread.sleep(5_000);
    return a*b;
  }

}
