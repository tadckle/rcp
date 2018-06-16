package rcp3.study.xmlunit;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.ElementNameAndAttributeQualifier;
import org.custommonkey.xmlunit.ElementNameAndTextQualifier;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Assert;
import org.xml.sax.SAXException;

/**
 * Using XmlUnit to compare actual XML with expected XML.
 *
 * @author alzhang
 */
public class XMLTestUtil {


  private XMLTestUtil() {
    throw new UnsupportedOperationException("Don't instantiate me!");
  }

  public static void compare(String expected, String actual) {
    try {
      Diff diff = XMLUnit.compareXML(expected, actual);
      diff.overrideElementQualifier(new ElementNameAndAttributeQualifier());
      if (diff.similar()) {
        return;
      }

      // Create a new Diff since Diff is cached once similar() or identical() method is called.
      diff = XMLUnit.compareXML(expected, actual);
      diff.overrideElementQualifier(new ElementNameAndTextQualifier());
      assertTrue(diff.toString(), diff.similar());
    } catch (SAXException | IOException e) {
      Assert.fail("Invalid XML format.");
    }
  }

}
