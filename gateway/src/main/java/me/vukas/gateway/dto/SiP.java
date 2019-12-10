package me.vukas.gateway.dto;

public class SiP {
  private Integer sum;
  private Integer product;

  public SiP(Integer sum, Integer product) {
    this.sum = sum;
    this.product = product;
  }

  public Integer getSum() {
    return sum;
  }

  public Integer getProduct() {
    return product;
  }
}
