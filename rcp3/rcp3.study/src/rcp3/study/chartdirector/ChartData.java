package rcp3.study.chartdirector;

import java.util.List;

import org.apache.commons.lang3.StringUtils;


/**
 * Base chart data.
 * 
 * @author Alex
 */
public class ChartData<T> {
	
	private String chartTitle = StringUtils.EMPTY;
	
	private String xAxisTitle = StringUtils.EMPTY;
	
	private String yAxisTitle = StringUtils.EMPTY;
	
	private String zAxisTitle = StringUtils.EMPTY;
	
	private List<T> datas = Lists.newArrayList();

	/**
	 * Get the chartTitle.
	 *
	 * @return the chartTitle.
	 */
	public String getChartTitle() {
		return chartTitle;
	}

	/**
	 * Set the chartTitle.
	 *
	 * @param chartTitle the chartTitle to set
	 */
	public void setChartTitle(String chartTitle) {
		this.chartTitle = chartTitle;
	}

	/**
	 * Get the xAxisTitle.
	 *
	 * @return the xAxisTitle.
	 */
	public String getxAxisTitle() {
		return xAxisTitle;
	}

	/**
	 * Set the xAxisTitle.
	 *
	 * @param xAxisTitle the xAxisTitle to set
	 */
	public void setxAxisTitle(String xAxisTitle) {
		this.xAxisTitle = xAxisTitle;
	}

	/**
	 * Get the yAxisTitle.
	 *
	 * @return the yAxisTitle.
	 */
	public String getyAxisTitle() {
		return yAxisTitle;
	}

	/**
	 * Set the yAxisTitle.
	 *
	 * @param yAxisTitle the yAxisTitle to set
	 */
	public void setyAxisTitle(String yAxisTitle) {
		this.yAxisTitle = yAxisTitle;
	}

	/**
	 * Get the zAxisTitle.
	 *
	 * @return the zAxisTitle.
	 */
	public String getzAxisTitle() {
		return zAxisTitle;
	}

	/**
	 * Set the zAxisTitle.
	 *
	 * @param zAxisTitle the zAxisTitle to set
	 */
	public void setzAxisTitle(String zAxisTitle) {
		this.zAxisTitle = zAxisTitle;
	}

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
	 * @param datas the datas to set
	 */
	public void setDatas(List<T> datas) {
		this.datas = datas;
	}

}
