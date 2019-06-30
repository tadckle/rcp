package rcp3.service.api.impl;

import rcp3.service.api.Calculate;

public class CalculateImpl implements Calculate {

  @Override
  public int add(int i, int j) {
    return i + j;
  }

}
