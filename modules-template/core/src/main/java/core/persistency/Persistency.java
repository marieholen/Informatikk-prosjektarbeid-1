package core.persistency;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Persistency {

    static Path filePath = Paths.get("recipe.txt");

    public static void createFile() throws IOException {
        Files.createFile(filePath);
    }
}
