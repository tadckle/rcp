package rcp3.study.chartdirector;

import org.apache.commons.lang3.StringUtils;

/**
 * Chart title data. Some of the title would be useless for certain chart.
 * E.g. XYChart won't use zAxisTitle as it do not have Z axis.
 *
 * <pre><code>
 * // Chain style call to set each field.
 * ChartTitle chartTitle = ChartTitle.create().plotTitle("3D Chart")
        .xAxisTitle("X Axis").zAxisTitle("Z Axis").yAxisTitle("Y Axis");
 * </code></pre>
 *
 * @author alzhang
 */
public class ChartTitle {

  /**
   * Plot title.
   */
  private String plotTitle = StringUtils.EMPTY;

  /**
   * X axis title.
   */
  private String xAxisTitle = StringUtils.EMPTY;

  /**
   * Y axis title.
   */
  private String yAxisTitle = StringUtils.EMPTY;

  /**
   * Z axis title.
   */
  private String zAxisTitle = StringUtils.EMPTY;

  /**
   * Create an empty ChartTitle.
   *
   * @return ChartTitle.
   */
  public static ChartTitle create() {
    return new ChartTitle();
  }

  /**
   * Set the plot title.
   *
   * @param thePlotTitle the plot title to set.
   * @return ChartTitle.
   */
  public ChartTitle plotTitle(String thePlotTitle) {
    this.plotTitle = thePlotTitle;
    return this;
  }

  /**
   * Set the X axis title.
   *
   * @param theXAxisTitle the X axis title to set.
   * @return ChartTitle.
   */
  public ChartTitle xAxisTitle(String theXAxisTitle) {
    this.xAxisTitle = theXAxisTitle;
    return this;
  }

  /**
   * Set the Y axis title.
   *
   * @param theYAxisTitle the Y axis title to set.
   * @return ChartTitle.
   */
  public ChartTitle yAxisTitle(String theYAxisTitle) {
    this.yAxisTitle = theYAxisTitle;
    return this;
  }

  /**
   * Set the Z axis title.
   *
   * @param theZAxisTitle the Z axis title to set.
   * @return ChartTitle.
   */
  public ChartTitle zAxisTitle(String theZAxisTitle) {
    this.zAxisTitle = theZAxisTitle;
    return this;
  }

  /**
   * Get the the plotTitle.
   *
   * @return the plotTitle.
   */
  public String getPlotTitle() {
    return plotTitle;
  }

  /**
   * Get the the xAxisTitle.
   *
   * @return the xAxisTitle.
   */
  public String getxAxisTitle() {
    return xAxisTitle;
  }

  /**
   * Get the the yAxisTitle.
   *
   * @return the yAxisTitle.
   */
  public String getyAxisTitle() {
    return yAxisTitle;
  }

  /**
   * Get the the zAxisTitle.
   *
   * @return the zAxisTitle.
   */
  public String getzAxisTitle() {
    return zAxisTitle;
  }

}
