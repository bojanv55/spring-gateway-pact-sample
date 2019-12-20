package me.vukas.addservice.repository;

import me.vukas.addservice.domain.Adder;
import org.springframework.stereotype.Repository;

@Repository
public class AdderRepositoryImpl implements AdderRepository {

  @Override
  public Adder getNewAdder() {
    return new Adder();
  }
}
