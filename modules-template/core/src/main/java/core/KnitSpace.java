package core;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Core logic.
 */
@SuppressWarnings({"S4276", "java:S4276"})
public class KnitSpace {


  static Path filePath = Paths.get("recipe.txt");

  public static void createFile() throws java.io.IOException {
    Files.createFile(filePath);
  }

}