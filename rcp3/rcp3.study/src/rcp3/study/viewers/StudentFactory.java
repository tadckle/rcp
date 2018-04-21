/**
 * 
 */
package rcp3.study.viewers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.RGB;

import com.google.common.collect.Lists;

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
  public static List<Student> tableInput() {
    String[] names = { "Bird", "Pig", "Dog", "Cat", "Parrot" };
    Sex[] sexes = { Sex.MALE, Sex.FEMALE, Sex.FEMALE, Sex.MALE, Sex.FEMALE };
    String[] countries = { "French", "Holland", "America", "Sweeden", "Germany" };
    Double[] heights = { 1.43, 1.67, 1.89, 1.75, 1.82 };
    Boolean[] isMarrieds = { true, false, true, true, false };
    RGB[] favoriteColors = { new RGB(255, 0, 0), new RGB(255, 0, 255), new RGB(0, 0, 255), new RGB(255, 128, 0),
        new RGB(0, 0, 0) };

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

    return students;
  }

  /**
   * Create a tree input students. One parent node with five children nodes.
   * 
   * @return a list of Student.
   */
  public static List<Student> treeInput() {
    List<Student> students = tableInput();

    Student parent = new Student();
    parent.setName("Parent");
    parent.setSex(Sex.FEMALE);
    parent.setCountry("Russia");
    parent.setHeight(0.34);
    parent.setMarried(true);
    parent.setFavoriteColor(new RGB(255, 0, 0));

    parent.setStudents(students);
    students.forEach(st -> st.setParent(parent));

    return Lists.newArrayList(parent);
  }

}
