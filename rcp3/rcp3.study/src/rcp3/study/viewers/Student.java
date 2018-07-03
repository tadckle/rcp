package rcp3.study.viewers;

import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.graphics.RGB;

/**
 * Student entity.
 *
 * @author Alex
 */
public class Student {
  /**
   * Sex enum.
   *
   * @author Alex
   */
  public enum Sex {
    MALE("male"), FEMALE("female"), LALA("lala");

    private final String name;

    private Sex(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }

    /**
     * Convert a name to enum.
     *
     * @param name
     *          sex name.
     * @return sex enum.
     */
    public static Sex fromName(String name) {
      return Arrays.stream(Sex.values()).filter(sex -> sex.name.equals(name)).findAny().orElse(Sex.LALA);
    }

    /**
     * Find a Sex whose ordinal is same as the input.
     *
     * @param ordinal
     *          sex ordinal.
     * @return sex.
     */
    public static Sex fromOrdinal(int ordinal) {
      return Arrays.stream(Sex.values()).filter(sex -> sex.ordinal() == ordinal).findAny().orElse(Sex.LALA);
    }
  }

  private String name;

  private Sex sex;

  private String country;

  private double height;

  private boolean isMarried;

  private String date;

  private RGB favoriteColor;

  private Student parent;

  private List<Student> students;

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name
   *          the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the sex
   */
  public Sex getSex() {
    return sex;
  }

  /**
   * @param sex
   *          the sex to set
   */
  public void setSex(Sex sex) {
    this.sex = sex;
  }

  /**
   * @return the country
   */
  public String getCountry() {
    return country;
  }

  /**
   * @param country
   *          the country to set
   */
  public void setCountry(String country) {
    this.country = country;
  }

  /**
   * @return the height
   */
  public double getHeight() {
    return height;
  }

  /**
   * @param height
   *          the height to set
   */
  public void setHeight(double height) {
    this.height = height;
  }

  /**
   * @return the isMarried
   */
  public boolean isMarried() {
    return isMarried;
  }

  /**
   * @param isMarried
   *          the isMarried to set
   */
  public void setMarried(boolean isMarried) {
    this.isMarried = isMarried;
  }

  /**
   * Get the the date.
   *
   * @return the date.
   */
  public String getDate() {
    return date;
  }

  /**
   * Set the date.
   *
   * @param date the date to set
   */
  public void setDate(String date) {
    this.date = date;
  }

  /**
   * @return the favoriteColor
   */
  public RGB getFavoriteColor() {
    return favoriteColor;
  }

  /**
   * @param favoriteColor
   *          the favoriteColor to set
   */
  public void setFavoriteColor(RGB favoriteColor) {
    this.favoriteColor = favoriteColor;
  }

  public Student getParent() {
    return parent;
  }

  public void setParent(Student parent) {
    this.parent = parent;
  }

  public List<Student> getStudents() {
    return students;
  }

  public void setStudents(List<Student> students) {
    this.students = students;
  }

}
