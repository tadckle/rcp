package rcp3.study.chartdirector;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Frame;
import java.awt.Panel;

import javax.swing.JRootPane;
import javax.swing.SwingConstants;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.google.common.base.Strings;

import ChartDirector.Chart;
import ChartDirector.ChartViewer;
import ChartDirector.ViewPortAdapter;
import ChartDirector.ViewPortChangedEvent;
import ChartDirector.XYChart;
import rcp3.study.composite.SashComposite;
import rcp3.study.composite.SashComposite.HideStyle;
import rcp3.study.resource.ImageResource;

/**
 * Base composite for showing a ChartViewer of ChartDirector.
 *
 * @author Alex
 */
public class ChartComposite extends Composite {

  private final FormToolkit toolkit = new FormToolkit(Display.getDefault());

  private ChartViewer chartViewer;

  private IChartCreator chartCreator;

  private Composite plotComp;

  private String chartHTMLImageMapFormat = StringUtils.EMPTY;

  private ToolBar toolBar;

  private Label separator;

  private SashComposite sashComp;

  /**
   * Construct a ChartComposite.
   *
   * @param parent
   *          the parent composite.
   * @param style
   *          the appearance style.
   */
  public ChartComposite(Composite parent, int style) {
    super(parent, style);
    this.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
    this.setBackgroundMode(SWT.INHERIT_DEFAULT);
    GridLayoutFactory.fillDefaults().spacing(0, 0).applyTo(this);

    toolBar = new ToolBar(this, SWT.FLAT);
    GridDataFactory.swtDefaults().align(SWT.LEFT, SWT.TOP).grab(true, false).indent(5, 0).applyTo(toolBar);

    ToolItem pointerItem = new ToolItem(toolBar, SWT.PUSH);
    pointerItem.setImage(ImageResource.POINTER);
    pointerItem.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        pointerAction();
      }
    });

    ToolItem zoomInItem = new ToolItem(toolBar, SWT.PUSH);
    zoomInItem.setImage(ImageResource.ZOOMIN);
    zoomInItem.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        zoomInAction();
      }
    });

    ToolItem zoomOutItem = new ToolItem(toolBar, SWT.PUSH);
    zoomOutItem.setImage(ImageResource.ZOOMOUT);
    zoomOutItem.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        zoomOutAction();
      }
    });

    separator = toolkit.createSeparator(this, SWT.SEPARATOR | SWT.HORIZONTAL);
    GridDataFactory.swtDefaults().align(SWT.FILL, SWT.TOP).grab(true, false).applyTo(separator);

    sashComp = new SashComposite(this, SWT.NONE, new HideStyle(SWT.RIGHT, 100, true));
    sashComp.setSashText("LEGEND");
    GridDataFactory.fillDefaults().grab(true, true).applyTo(sashComp);

    createPlotComp(sashComp);

    createLegendComp(sashComp);
  }

  private void createPlotComp(SashComposite sashComp) {
    Composite mainComp = sashComp.getMainComp();
    GridLayoutFactory.fillDefaults().applyTo(mainComp);

    plotComp = new Composite(mainComp, SWT.EMBEDDED | SWT.NO_BACKGROUND);
    GridDataFactory.fillDefaults().grab(true, true).applyTo(plotComp);
    Frame frame = SWT_AWT.new_Frame(plotComp);

    Panel plotPanel = new Panel();
    plotPanel.setLayout(new BorderLayout(0, 0));
    frame.add(plotPanel);

    JRootPane rootPane = new JRootPane();
    plotPanel.add(rootPane);

    chartViewer = new ChartViewer();
    chartViewer.setBackground(Color.white);
    chartViewer.setOpaque(true);
    chartViewer.setHorizontalAlignment(SwingConstants.LEFT);
    chartViewer.setVerticalAlignment(SwingConstants.TOP);
    chartViewer.setHotSpotCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    chartViewer.setZoomDirection(Chart.DirectionHorizontalVertical);

    rootPane.getContentPane().add(chartViewer, BorderLayout.CENTER);

    frame.pack();
    frame.setVisible(true);

    chartViewer.addViewPortListener(new ViewPortAdapter() {
      @Override
      public void viewPortChanged(ViewPortChangedEvent event) {
        viewPortChangedAction(event);
      }
    });
  }

  private void createLegendComp(SashComposite sashComp) {

  }

  private void viewPortChangedAction(ViewPortChangedEvent event) {
    if (event.needUpdateChart()) {
      createChart();
    }
  }

  private void pointerAction() {
    chartViewer.setMouseUsage(Chart.MouseUsageDefault);
  }

  private void zoomInAction() {
    chartViewer.setMouseUsage(Chart.MouseUsageZoomIn);
  }

  private void zoomOutAction() {
    chartViewer.setMouseUsage(Chart.MouseUsageZoomOut);
  }

  public void setChartCreator(IChartCreator chartCreator) {
    if (chartCreator != null) {
      this.chartCreator = chartCreator;
    }

    plotComp.addListener(SWT.Resize, evt -> createChart());
  }

  private Point plotSize() {
    Point size = new Point(0, 0);
    Display.getDefault().syncExec(() -> {
      Point p = plotComp.getSize();
      size.x = p.x;
      size.y = p.y;
    });

    return size;
  }

  /**
   * Normally you don't need to call createChart(...). It will automatically be called when
   * plotComp size is changed. But when you update chart data when chart is open,
   * you shall call createChart(...).
   */
  public void createChart() {
    if (chartCreator == null) {
      return;
    }

    Point size = plotSize();

    XYChart chart = (XYChart) chartCreator.create(size);
    chart.recycle(chartViewer.getChart());
    chart.setClipping();

    chartViewer.syncLinearAxisWithViewPort("x", chart.xAxis());
    chartViewer.syncLinearAxisWithViewPort("y", chart.yAxis());

    chartViewer.setChart(chart);
    if (!Strings.isNullOrEmpty(chartHTMLImageMapFormat)) {
      chartViewer.setImageMap(chart.getHTMLImageMap("clickable", "", chartHTMLImageMapFormat));
    }
  }

  public void setChartHtmlImageMap(String format) {
    this.chartHTMLImageMapFormat = format;
  }

  /**
   * Get the the chartViewer.
   *
   * @return the chartViewer.
   */
  public ChartViewer getChartViewer() {
    return chartViewer;
  }

  /**
   * Set the visible of tool bar.
   *
   * @param visible true means show tool bar.
   */
  public void setToolBarVisible(boolean visible) {
    toolBar.setVisible(visible);
    ((GridData) toolBar.getLayoutData()).exclude = !visible;

    separator.setVisible(visible);
    ((GridData) separator.getLayoutData()).exclude = !visible;

    this.layout(true);
  }

  /**
   * Set the visible of legend.
   *
   * @param visible true means show legend.
   */
  public void setLegendVisible(boolean visible) {
    sashComp.setOnlyShowMainComposite(!visible);
  }

}
