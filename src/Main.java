public class Main {
    public static void main(String[] args) {
        Service service = new Service();


        if(args.length < 1) {
            System.out.println("Error please provide at least one command.");
            return;
        }

        String command = args[0];

        switch (command) {
            case "add" -> {
                if(args.length < 2) {
                    System.out.println("Error please provide at least two command.");
                    return;
                }
                service.addTask(args[1]);
            }
            case "update" -> {
                if(args.length < 3) {
                    System.out.println("Error please provide at least three command.");
                    return;
                }
                service.updateTask(args[1],args[2]);
            }
            case "delete" -> {
                if(args.length < 2) {
                    System.out.println("Error please provide at least two command.");
                    return;
                }
                service.deleteTask(args[1]);
            }
            case "mark-in-progress" -> {
                if(args.length < 2) {
                    System.out.println("Error please provide at least two command.");
                    return;
                }
                service.markTaskToInProgress(args[1]);
            }
            case "mark-done" -> {
                if(args.length < 2) {
                    System.out.println("Error please provide at least two command.");
                    return;
                }
                service.markTaskToDone(args[1]);
            }
            case "list" -> {
                command = args.length > 1 ? args[1] : "";
                switch (command) {
                case "" -> service.listAllTasks();
                case "done" -> service.listDoneTasks();
                case "todo" -> service.listTodoTasks();
                case "in-progress" -> service.listInProgressTasks();
                default -> throw new IllegalStateException("Unexpected value: " + command);}
            }
            default -> throw new IllegalStateException("Unexpected value: " + command);
        }
        service.saveToJSON();
    }
}