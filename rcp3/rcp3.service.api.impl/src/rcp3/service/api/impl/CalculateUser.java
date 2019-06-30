package rcp3.service.api.impl;

import rcp3.service.api.Calculate;

public class CalculateUser implements Calculate {

  private Calculate calculate;

  @Override
  public int add(int i, int j) {
    return 0;
  }

  public void setCalculate(Calculate calculate) {
    System.out.println("Register calculte to CalculateUser.");
    this.calculate = calculate;
  }

}
