package rcp3.study.viewers;

import java.util.Collection;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * Content provider for student table. It's same implementation of
 * ArrayContentProvider.
 * 
 * @author Alex
 */
public class StudentTableContentProvider implements IStructuredContentProvider {

  @Override
  public void dispose() {
    // Dispose some resource.
  }

  @Override
  public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    // Do something when input is changed.
  }

  @SuppressWarnings("rawtypes")
  @Override
  public Object[] getElements(Object inputElement) {
    if (inputElement instanceof Object[]) {
      return (Object[]) inputElement;
    }
    if (inputElement instanceof Collection) {
      return ((Collection) inputElement).toArray();
    }
    return new Object[0];
  }
}
