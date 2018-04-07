/**
 * 
 */
package rcp3.study.viewers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.graphics.RGB;

import rcp3.study.viewers.Student.Sex;

/**
 * A Factory to create a list of students.
 * 
 * @author Alex
 */
public class StudentFactory {
	
	/**
	 * Create a list with five Student.
	 * 
	 * @return a list of Student.
	 */
	public static List<Student> create() {
		String[] names = {"Bird", "Pig", "Dog", "Cat", "Parrot"};
		Sex[] sexes = {Sex.MALE, Sex.FEMALE, Sex.FEMALE, Sex.MALE, Sex.FEMALE};
		String[] countries = {"French", "Holland", "America", "Sweeden", "Germany"};
		Double[] heights = {1.43, 1.67, 1.89, 1.75, 1.82};
		Boolean[] isMarrieds = {true, false, true, true, false};
		RGB[] favoriteColors = {new RGB(255, 0, 0), new RGB(255, 0, 255), new RGB(0, 0, 255),
				new RGB(255, 128, 0), new RGB(0, 0, 0)};
		
		List<Student> students = new ArrayList<>();
		Student student;
		for (int i = 0; i < names.length; i++) {
			student = new Student();
			student.setName(names[i]);
			student.setSex(sexes[i]);
			student.setCountry(countries[i]);
			student.setHeight(heights[i]);
			student.setMarried(isMarrieds[i]);
			student.setFavoriteColor(favoriteColors[i]);
			
			students.add(student);
		}
		
		Student child = new Student();
		child.setName("Child");
		child.setSex(Sex.FEMALE);
		child.setCountry("Russia");
		child.setHeight(0.34);
		child.setMarried(true);
		child.setFavoriteColor(new RGB(255, 0, 0));

		students.get(0).setStudents(Arrays.asList(child));
		child.setParent(students.get(0));
		
		return students;
	}

}