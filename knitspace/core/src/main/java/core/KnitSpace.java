package core;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Handles basic file creation logic.
 */
@SuppressWarnings({"S4276", "java:S4276"})
public class KnitSpace {

  /** 
   * Retrieves the filepath for {@code recipe.txt} and creates a file in that filepath.
   * @return "File created successfully!" if file is created or "File already exists!" if file already exists
   * @throws IOException if something goes wrong in the process
   */
  public static String createFile() {
    
    try {
      Path filePath = Paths.get("recipe.txt");
      Files.createFile(filePath);
      return "File created successfully!";

    } catch (FileAlreadyExistsException f) {
      return "File already exists!";

    } catch (IOException e) {
      return "Something went wrong: " + e.getMessage();

    }
    
  }

}