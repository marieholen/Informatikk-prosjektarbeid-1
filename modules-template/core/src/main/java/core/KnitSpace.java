package core;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

import core.persistency.Persistency;



/**
 * Core logic.
 */
@SuppressWarnings({"S4276", "java:S4276"})
public class KnitSpace {


  public static String createFile() {

    try {
      Persistency.createFile();
      return "File created successfully!";

    } catch (FileAlreadyExistsException f) {
      return "File already exists!";

    } catch (IOException e) {
      return "Something went wrong: " + e.getMessage();

    }
  }

}