package rcp3.study.viewers;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;

import javax.annotation.CheckForNull;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.nebula.jface.gridviewer.GridTableViewer;
import org.eclipse.nebula.jface.gridviewer.GridTreeViewer;
import org.eclipse.nebula.widgets.grid.Grid;
import org.eclipse.nebula.widgets.grid.GridColumn;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.Widget;
import org.osgi.service.prefs.Preferences;

import com.google.common.base.Strings;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * The factory that is used to create TableViewer, TreeViewer, GridTableViewer and GridTreeViewer.
 *
 * @author alzhang
 */
public class ViewerFactory {

  private static class ViewerControlMethodInterceptor implements MethodInterceptor {
    private ViewerColumnPersistor columnPersistor = ViewerColumnPersistor.instance();

    @CheckForNull
    private Preferences preferences = null;

    @CheckForNull
    private ColumnViewer columnViewer = null;

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
      if (columnViewer != null && columnViewer.getComparator() != null) {
        addSortListener(method, args);
      }
      if (preferences != null) {
        addPersistListener(method, args);
      }

      return methodProxy.invokeSuper(obj, args);
    }

    private void addPersistListener(Method method, Object[] args) throws Throwable {
      if (Table.class.getDeclaredMethod("createItem", TableColumn.class, int.class).equals(method)) {
        columnPersistor.restoreWidth((TableColumn) args[0], preferences);
      }
    }

    private void addSortListener(Method method, Object[] args) throws Throwable {
      if (Table.class.getDeclaredMethod("createItem", TableColumn.class, int.class).equals(method)) {
        ViewerComparatorUtil.addListener((TableViewer) columnViewer, (TableColumn) args[0]);
      } else if (Tree.class.getDeclaredMethod("createItem", TreeColumn.class, int.class).equals(method)) {
        ViewerComparatorUtil.addListener((TreeViewer) columnViewer, (TreeColumn) args[0]);
      } else if (Grid.class.getDeclaredMethod("newColumn", GridColumn.class, int.class).equals(method)) {
        if (columnViewer instanceof GridTableViewer) {
          ViewerComparatorUtil.addListener((GridTableViewer) columnViewer, (GridColumn) args[0]);
        } else if (columnViewer instanceof GridTreeViewer) {
          ViewerComparatorUtil.addListener((GridTreeViewer) columnViewer, (GridColumn) args[0]);
        }
      }
    }
  }

  @CheckForNull
  private ViewerComparator comparator = null;

  @CheckForNull
  private String preferenceKey = null;

  /**
   * Create a ViewerFactory instance.
   *
   * @return a ViewerFactory.
   */
  public static ViewerFactory instance() {
    return new ViewerFactory();
  }

  /**
   * Create a TableViewer.
   *
   * @param parent the parent composite.
   * @param style the appearance style.
   * @return a TableViewer.
   */
  public TableViewer createTableViewer(Composite parent, int style) {
    return doCreateViewer(parent, style, Table.class, TableViewer.class);
  }

  /**
   * Create a TreeViewer.
   *
   * @param parent the parent composite.
   * @param style the appearance style.
   * @return a TreeViewer.
   */
  public TreeViewer createTreeViewer(Composite parent, int style) {
    return doCreateViewer(parent, style, Tree.class, TreeViewer.class);
  }

  /**
   * Create a GridTableViewer.
   *
   * @param parent the parent composite.
   * @param style the appearance style.
   * @return a GridTableViewer.
   */
  public GridTableViewer createGridTableViewer(Composite parent, int style) {
    return doCreateViewer(parent, style, Grid.class, GridTableViewer.class);
  }

  /**
   * Create a GridTreeViewer.
   *
   * @param parent the parent composite.
   * @param style the appearance style.
   * @return a GridTreeViewer.
   */
  public GridTreeViewer createGridTreeViewer(Composite parent, int style) {
    return doCreateViewer(parent, style, Grid.class, GridTreeViewer.class);
  }

  private <T extends ColumnViewer> T doCreateViewer(Composite parent, int style,
      Class<? extends Widget> proxyClass, Class<T> viewerClass) {
    Enhancer enhancer = new Enhancer();
    enhancer.setClassLoader(enhancer.getClass().getClassLoader());
    enhancer.setSuperclass(proxyClass);
    ViewerControlMethodInterceptor methodInterceptor = new ViewerControlMethodInterceptor();
    enhancer.setCallback(methodInterceptor);

    try {
      Widget widget = proxyClass.cast(enhancer.create(
          new Class[] { Composite.class, int.class }, new Object[] { parent, style }));
      T viewer = viewerClass.getConstructor(proxyClass).newInstance(widget);
      if (comparator != null) {
        viewer.setComparator(comparator);
      }

      if (!Strings.isNullOrEmpty(preferenceKey)) {
        widget.addDisposeListener(this::disposeSaveWidth);
        methodInterceptor.preferences = ViewerFactory.getPreferences(preferenceKey);
      }
      methodInterceptor.columnViewer = viewer;
      return viewer;
    } catch (Exception e) {
      throw new IllegalStateException("Cannot create viewer.", e);
    }
  }

  private void disposeSaveWidth(DisposeEvent evt) {
    if (evt.widget instanceof Table) {
      ViewerColumnPersistor.instance().saveWidth((Table) evt.widget, ViewerFactory.getPreferences(preferenceKey));
    }
  }

  /**
   * Make column be sortable.
   *
   * @return a ViewerFactory.
   */
  public ViewerFactory enableSort() {
    this.comparator = new ViewerComparatorAllInOne();
    return this;
  }

  /**
   * Make column be sortable. Date column will be sorted by date.
   *
   * @param dateFormat a SimpleDateFormat.
   * @return a ViewerFactory.
   */
  public ViewerFactory enableSort(SimpleDateFormat dateFormat) {
    this.comparator = new ViewerComparatorAllInOne(dateFormat);
    return this;
  }

  /**
   * Remember column width when width is disposed. Restore column width when reopen it.
   *
   * @param preferenceKey the preference key to store column width.
   * @return a ViewerFactory.
   */
  public ViewerFactory persistColumnWidth(String preferenceKey) {
    this.preferenceKey = preferenceKey;
    return this;
  }

  private static Preferences getPreferences(String nodeName) {
    IEclipsePreferences columnWidthPreferences = InstanceScope.INSTANCE.getNode("ColumnWidthStorage");
    return columnWidthPreferences.node(nodeName);
  }

}
