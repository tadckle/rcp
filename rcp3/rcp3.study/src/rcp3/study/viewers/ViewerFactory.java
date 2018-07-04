package rcp3.study.viewers;

import java.lang.reflect.Method;

import javax.annotation.CheckForNull;

import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.nebula.jface.gridviewer.GridTableViewer;
import org.eclipse.nebula.jface.gridviewer.GridTreeViewer;
import org.eclipse.nebula.widgets.grid.Grid;
import org.eclipse.nebula.widgets.grid.GridColumn;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * The factory that is used to create TableViewer, TreeViewer, GridTableViewer and GridTreeViewer.
 *
 * @author alzhang
 */
public class ViewerFactory {

  @CheckForNull
  private ViewerComparator comparator = null;

  private static class ViewerControlMethodInterceptor implements MethodInterceptor {
    @CheckForNull
    private ColumnViewer columnViewer;

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
      if (columnViewer != null && columnViewer.getComparator() != null) {
        addSortListener(method, args);
      }
      return methodProxy.invokeSuper(obj, args);
    }

    private void addSortListener(Method method, Object[] args) throws NoSuchMethodException {
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

  private <T extends ColumnViewer> T doCreateViewer(Composite parent, int style, Class<?> proxyClass,
      Class<T> viewerClass) {
    Enhancer enhancer = new Enhancer();
    enhancer.setSuperclass(proxyClass);
    ViewerControlMethodInterceptor methodInterceptor = new ViewerControlMethodInterceptor();
    enhancer.setCallback(methodInterceptor);

    try {
      T viewer = viewerClass.getConstructor(proxyClass).newInstance(proxyClass.cast(
          enhancer.create(new Class[] { Composite.class, int.class }, new Object[] { parent, style })));
      viewer.setComparator(comparator);
      methodInterceptor.columnViewer = viewer;
      return viewer;
    } catch (Exception e) {
      throw new IllegalStateException("Cannot create viewer.", e);
    }
  }

  /**
   * Set the comparator.
   *
   * @param comparator the comparator to set
   */
  public ViewerFactory setComparator(ViewerComparator comparator) {
    this.comparator = comparator;
    return this;
  }

}
