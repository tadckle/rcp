package rcp3.study.chartdirector;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * Base chart data.
 *
 * @author Alex
 */
public class ChartData<T> {

  private List<T> datas = Lists.newArrayList();

  /**
   * Get the datas.
   *
   * @return the datas.
   */
  public List<T> getDatas() {
    return datas;
  }

  /**
   * Set the datas.
   *
   * @param datas
   *          the datas to set
   */
  public void setDatas(List<T> datas) {
    this.datas = datas;
  }

}
