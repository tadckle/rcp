package rcp3.study.chartdirector;

import org.eclipse.swt.graphics.Point;

import ChartDirector.BaseChart;

public interface IChartCreator {

  /**
   * Create a BaseChart.
   *
   * @return a BaseChart.
   */
  BaseChart create(Point size);
}
