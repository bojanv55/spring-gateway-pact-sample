package me.vukas.addservice.controller;

import me.vukas.addservice.domain.Adder;
import me.vukas.addservice.repository.AdderRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddController {

  public AdderRepository repository;

  public AddController(AdderRepository repository) {
    this.repository = repository;
  }

  @GetMapping("add")
  public Integer add(Integer a, Integer b) throws InterruptedException {
    Thread.sleep(5_000);
    Adder adder = repository.getNewAdder();
    return adder.add(a,b);
  }

}
