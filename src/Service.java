import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Service {
    ArrayList<Task> tasks = new ArrayList<>();

    public void addTask(String description) {
        System.out.println("Adding task...");
        Task task = new Task(description);
        tasks.add(task);
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
