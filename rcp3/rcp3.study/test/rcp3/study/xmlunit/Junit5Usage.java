package rcp3.study.xmlunit;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test how to use Junit5.
 *
 * @author alzhang
 */
@DisplayName("Testing using JUnit 5")
class Junit5Usage {

  private static final Logger log = LoggerFactory.getLogger(Junit5Usage.class);

  @BeforeAll
  public static void init() {
    // Do something before ANY test is run in this class
  }

  @AfterAll
  public static void done() {
    // Do something after ALL tests in this class are run
  }

  @BeforeEach
  public void setUp() throws Exception {
  }

  @AfterEach
  public void tearDown() throws Exception {
  }

  @Test
  @DisplayName("Dummy test")
  void aTest() {
    int expected = 4;
    int actual = 2 + 2;
    Object nullValue = null;


    assertAll(
        "Assert All of these",
        () -> assertEquals(expected, actual, "INCONCEIVABLE!"),
        () -> assertFalse(nullValue == null),
        () -> assertNull(nullValue),
        () -> assertNotNull("A String", "INCONCEIVABLE!"),
        () -> assertTrue(nullValue != null));
  }

  @Test
  @Disabled
  @DisplayName("A disabled test")
  void testNotRun() {
    log.info("This test will not run (it is disabled, silly).");
  }

}
