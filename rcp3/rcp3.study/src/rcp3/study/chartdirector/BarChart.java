package rcp3.study.chartdirector;

import org.eclipse.swt.graphics.Point;

import ChartDirector.BaseChart;
import ChartDirector.XYChart;

public class BarChart implements IChartCreator {

  private double[] barData = {};

  private String[] labels = {};

  /**
   * Set the barData.
   *
   * @param barData the barData to set
   */
  public void setBarData(double[] barData) {
    this.barData = barData;
  }

  /**
   * Set the labels.
   *
   * @param labels the labels to set
   */
  public void setLabels(String[] labels) {
    this.labels = labels;
  }

  @Override
  public BaseChart create(Point size) {
    XYChart chart = new XYChart(size.x, size.y);
    chart.setPlotArea(30, 20, size.x - 40, size.y - 50);
    chart.addBarLayer(barData);
    chart.xAxis().setLabels(labels);

    return chart;
  }


}
