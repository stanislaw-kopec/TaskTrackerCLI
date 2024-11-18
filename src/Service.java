import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

public class Service {
    ArrayList<Task> tasks = unloadJSON();

    public void addTask(String description) {
        System.out.println("Adding task...");
        Task task = new Task(description);
        tasks.add(task);
    }

    public void updateTask(String id, String description) {
        try {
            int taskId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            System.err.println("Invalid task ID: " + id);
            return;
        }
        Task task = findTask(id).orElseThrow(() -> new IllegalStateException("Task with " + id + " not found."));
        task.setDescription(description);
        task.setUpdatedAt();
    }

    public void deleteTask(String id) {
        try {
            tasks.remove(Integer.parseInt(id));
            System.out.println("Task where id is " + id + " was deleted.");
        } catch (NumberFormatException e) {
            System.out.println("Incorrect id: " + id);
        }
    }

    public Optional<Task> findTask(String id) {
        return tasks.stream().filter((task) -> task.getId() == Integer.parseInt(id)).findFirst();
    }

    public static void displayTask(Task task) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        System.out.println("Id: " + task.getId());
        System.out.println("Description: " + task.getDescription());
        System.out.println("Status: " + task.getStatus());
        System.out.println("Created at: " + task.getCreatedAt().format(format));
        System.out.println("Updated at: " + task.getUpdatedAt().format(format) + "\n");
    }

    public void listAllTasks() {
        for (Task task : tasks) {
            displayTask(task);
        }
    }

    public void listDoneTasks() {
        for (Task task : tasks) {
            if (task.getStatus().toString().equals("Done")) {
                displayTask(task);
            }
        }
    }

    public void listInProgressTasks() {
        for (Task task : tasks) {
            if (task.getStatus().toString().equals("In progress")) {
                displayTask(task);
            }
        }
    }

    public void listTodoTasks() {
        for (Task task : tasks) {
            if (task.getStatus().toString().equals("Todo")) {
                displayTask(task);
            }
        }
    }

    public void markTaskToInProgress(String id) {
        Task task = findTask(id).orElseThrow(() -> new IllegalStateException("Task with " + id + " not found."));
        task.setUpdatedAt();
        task.setStatus(Status.IN_PROGRESS);
    }

    public void markTaskToDone(String id) {
        Task task = findTask(id).orElseThrow(() -> new IllegalStateException("Task with " + id + " not found."));
        task.setUpdatedAt();
        task.setStatus(Status.DONE);
    }

    private static ArrayList<Task> unloadJSON() {
        ArrayList<Task> tasks = new ArrayList<>();

        File jsonFile = new File("tasks.json");

        if (!jsonFile.exists()) {
            System.out.println("Json file initialization...");
            return tasks;
        }

        StringBuilder jsonBuilder = new StringBuilder();

        try(BufferedReader reader = new BufferedReader(new FileReader(jsonFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String json = jsonBuilder.toString().trim();

        if(json.isEmpty()){
            return tasks;
        }

        System.out.println("Downloading data from JSON...");

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
            String createdAt = extractJsonValue(taskJson, "createdAt");
            String updatedAt = extractJsonValue(taskJson, "updatedAt");

            Task task = new Task(description);
            task.setStatus(Status.fromString(status));
            task.setCreatedAt(createdAt);
            task.setUpdatedAt(updatedAt);
            tasks.add(task);
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
