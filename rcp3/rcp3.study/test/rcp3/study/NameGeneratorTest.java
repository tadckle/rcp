package rcp3.study;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

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
    List<String> existingNames = Lists.newArrayList();
    NameGenerator nameGenerator = new NameGenerator("NewName");
    assertEquals("NewName1", nameGenerator.next(existingNames));

  }

}
