package rcp3.study.viewers;

import org.eclipse.core.runtime.Platform;
import org.eclipse.nebula.widgets.grid.Grid;
import org.eclipse.nebula.widgets.grid.GridColumn;
import org.osgi.framework.Bundle;

public class ViewerClasses {

  public Class<Grid> gridClz;
  public Class<GridColumn> gridColumnClz;

  private static final class SingleInstance {
    static final ViewerClasses INSTANCE = new ViewerClasses();
  }

  public ViewerClasses() {
    try {
      init();
    } catch (Exception e) {
      throw new IllegalStateException("");
    }
  }

  public static ViewerClasses instance() {
    return SingleInstance.INSTANCE;
  }

  @SuppressWarnings("unchecked")
  private void init() throws ClassNotFoundException {
    Bundle nebulaGridBundle = Platform.getBundle("org.eclipse.nebula.widgets.grid");
    if (nebulaGridBundle != null) {
      gridClz = (Class<Grid>) nebulaGridBundle.loadClass("org.eclipse.nebula.widgets.grid.Grid");
      gridColumnClz = (Class<GridColumn>) nebulaGridBundle.loadClass("org.eclipse.nebula.widgets.grid.GridColumn");
    } else {
      gridClz = Grid.class;
      gridColumnClz = GridColumn.class;
    }
  }

}
