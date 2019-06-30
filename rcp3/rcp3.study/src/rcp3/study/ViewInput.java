package rcp3.study;

import rcp3.service.api.Calculate;

public class ViewInput {

  private Calculate calculate1;

  public void setCalculate(Calculate calculate) {
    System.out.println("set calculate");
    this.calculate1 = calculate;
  }

  public void unsetCalculate(Calculate calculate) {
    System.out.println("unset calculate...");
  }

  public int getSum() {
    System.out.print("calculated");
//    return 1;
    return calculate1.add(10, 20) * 3;
  }

}
