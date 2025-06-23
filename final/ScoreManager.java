import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ScoreManager {
    private static final String FILE_PATH = "scores.json";

    public static void addWin(String playerName) {
        JSONObject scores = loadScores();

        Object value = scores.get(playerName);
        long current = (value instanceof Long) ? (Long) value : 0L;

        scores.put(playerName, current + 1);
        saveScores(scores);
    }

    public static String readScores() {
        JSONObject scores = loadScores();
        if (scores.isEmpty()) return "No scores yet.";

        // Convert to list and sort descending by score
        List<Map.Entry<String, Long>> entries = new ArrayList<>();
        for (Object key : scores.keySet()) {
            Object value = scores.get(key);
            if (value instanceof Long) {
                entries.add(new AbstractMap.SimpleEntry<>((String) key, (Long) value));
            }
        }

        entries.sort((a, b) -> Long.compare(b.getValue(), a.getValue())); // descending

        // Build output string
        StringBuilder sb = new StringBuilder("Number of victories:\n\n");
        for (Map.Entry<String, Long> entry : entries) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        return sb.toString();
    }

    private static JSONObject loadScores() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            return (JSONObject) new JSONParser().parse(reader);
        } catch (Exception e) {
            return new JSONObject(); // create empty if missing/corrupted
        }
    }

    private static void saveScores(JSONObject scores) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            writer.write(scores.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
