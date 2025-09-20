package persistency;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

public class PatternPersistence {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final File file = new File("patterns.json");

    public static void savePatterns(List<Pattern> patterns) throws Exception {
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, patterns);
    }

    public static List<Pattern> loadPatterns() throws Exception {
        if (file.exists()) {
            return List.of(mapper.readValue(file, Pattern[].class));
        }
        return List.of(); 
    }


    public static void addPattern(Pattern newPattern) throws Exception {
        List<Pattern> patterns = loadPatterns();
        boolean exists = patterns.stream()
                .anyMatch(p -> p.getId().equals(newPattern.getId()));
        if (!exists) {
            patterns.add(newPattern);
            savePatterns(patterns);
        }
    }


    public static void deletePattern(String id) throws Exception {
        List<Pattern> patterns = loadPatterns();
        patterns.removeIf(p -> p.getId().equals(id));
        savePatterns(patterns);
    }


    public static void updatePattern(String id, Pattern updatedPattern) throws Exception {
        List<Pattern> patterns = loadPatterns();
        for (int i = 0; i < patterns.size(); i++) {
            if (patterns.get(i).getId().equals(id)) {
                updatedPattern.setId(id); 
                patterns.set(i, updatedPattern);
                break;
            }
        }
        savePatterns(patterns);
    }

    public static Pattern findById(String id) throws Exception {
        return loadPatterns().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    
    public static List<Pattern> getAllPatterns() throws Exception {
        return loadPatterns();

    }
}

