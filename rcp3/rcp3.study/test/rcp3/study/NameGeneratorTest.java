package rcp3.study;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;

import rcp3.study.common.NameGenerator;

/**
 * Test cases for NameGenerator.
 *
 * @author alzhang
 */
public class NameGeneratorTest {

  @Test
  public void testNext() {
    NameGenerator nameGenerator = new NameGenerator("NewName");
    assertEquals("NewName1", nameGenerator.next(Lists.newArrayList()));
    assertEquals("NewName1", nameGenerator.next(null));

    assertEquals("NewName5", nameGenerator.next(Lists.newArrayList(
        "NewName1", "NewName 4")));

    assertEquals("NewName7", nameGenerator.next(Lists.newArrayList(
        "abcNewName1", "dsNewName 6sd")));
    assertEquals("NewName1", nameGenerator.next(Lists.newArrayList(
        "abcNewName", "dsNewName  sd")));
  }

}
