package rcp3.study.viewers;

import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

/**
 * Hierarchical content provider of Student.
 * 
 * @author Alex
 */
public class StudentTreeContentProvider extends ArrayContentProvider implements ITreeContentProvider {
	@Override
	public Object[] getChildren(Object parentElement) {
		return ((Student) parentElement).getStudents().toArray();
	}

	@Override
	public Object getParent(Object element) {
		return ((Student) element).getParent();
	}

	@Override
	public boolean hasChildren(Object element) {
		List<Student> students = ((Student) element).getStudents();
		return students != null && !students.isEmpty();
	}
}