package core;

import static java.nio.file.Paths.get;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static java.nio.file.Files.deleteIfExists;
import static java.nio.file.Files.exists;

import org.junit.jupiter.api.Test;

/**
 * Test suite for KnitSpace.
 */
@SuppressWarnings({"java:S5778"})
class KnitSpaceTest {
  
  @Test
  void testFileCreationOnButtonClick() throws Exception {
    var path = get("recipe.txt");

    deleteIfExists(path);

    assertFalse(
        exists(path),
        "recipe.txt should not exist before the program runs");

    KnitSpace.createFile();

    assertTrue(
        exists(path), "recipe.txt has not been created");

    deleteIfExists(path);
  }
}
