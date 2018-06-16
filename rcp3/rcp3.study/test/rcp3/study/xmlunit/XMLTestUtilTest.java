package rcp3.study.xmlunit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.Test;

public class XMLTestUtilTest {

  @Test
  public void testCompare() {
    XMLTestUtil.compare(readFile("file1.xml"), readFile("file2.xml"));
  }

  private String readFile(String fileName) {
    try {
      return String.join(System.lineSeparator(), Files.readAllLines(
          new File(XMLTestUtilTest.class.getResource(fileName).getPath()).toPath()));
    } catch (IOException e) {
      e.printStackTrace();
      return "";
    }
  }

}
