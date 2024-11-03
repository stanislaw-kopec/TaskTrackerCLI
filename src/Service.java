import java.io.*;
import java.util.ArrayList;

public class Service {
    //ArrayList<Task> tasks = new ArrayList<>();
    ArrayList<Task> tasks = unloadJSON();

    public void addTask(String description) {
        System.out.println("Adding task...");
        Task task = new Task(description);
        tasks.add(task);
    }

    public static ArrayList<Task> unloadJSON() {
        System.out.println("Downloading data from JSON...");
        ArrayList<Task> tasks = new ArrayList<>();

        StringBuilder jsonBuilder = new StringBuilder();

        try(BufferedReader reader = new BufferedReader(new FileReader("tasks.json"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String json = jsonBuilder.toString().trim();

        if (json.startsWith("[") && json.endsWith("]")) {
            json = json.substring(1,json.length() - 1);
        }

        String[] taskJsonArray = json.split("\\},\\s*\\{");

        for (String taskJson : taskJsonArray) {
            taskJson = taskJson.trim();
            if (!taskJson.startsWith("{")) taskJson = "{" + taskJson;
            if (!taskJson.endsWith("}")) taskJson = taskJson + "}";

            String description = extractJsonValue(taskJson, "description");
            String status = extractJsonValue(taskJson, "status");
            //String description = extractJsonValue(taskJson, "description");

            tasks.add(new Task(description));
        }

        return tasks;
    }

    private static String extractJsonValue(String json, String key) {
        String[] parts = json.split("\"" + key + "\"\\s*:\\s*");
        if (parts.length > 1) {
            String valuePart = parts[1].split(",|\\}")[0].trim();
            if (valuePart.startsWith("\"") && valuePart.endsWith("\"")) {
                return valuePart.substring(1,valuePart.length() - 1);
            } else {
                return valuePart;
            }
        }
        return "";
    }

    public void saveToJSON() {
        System.out.println("Saving data to JSON...");
        String json = convertTasksToJSON(tasks);

        try (FileWriter fileWriter = new FileWriter("tasks.json")) {
            fileWriter.write(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String convertTasksToJSON(ArrayList<Task> tasks) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("[\n");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            jsonBuilder.append("  {\n");
            jsonBuilder.append("    \"id\": \"").append(task.getId()).append("\",\n");
            jsonBuilder.append("    \"description\": \"").append(task.getDescription()).append("\",\n");
            jsonBuilder.append("    \"status\": \"").append(task.getStatus()).append("\",\n");
            jsonBuilder.append("    \"createdAt\": \"").append(task.getCreatedAt()).append("\",\n");
            jsonBuilder.append("    \"updatedAt\": \"").append(task.getUpdatedAt()).append("\"\n");
            jsonBuilder.append("  }");

            if(i < tasks.size() - 1) {
                jsonBuilder.append(",");
            }
            jsonBuilder.append("\n");
        }
        jsonBuilder.append("]");
        return jsonBuilder.toString();
    }
}
