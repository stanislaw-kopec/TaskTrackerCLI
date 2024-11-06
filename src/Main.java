public class Main {
    public static void main(String[] args) {
        Service service = new Service();


        if(args.length < 1) {
            System.out.println("Error please provide at least one command.");
            return;
        }

        String command = args[0];

        switch (command) {
            case "add" -> service.addTask("Nowy");
            case "update" -> service.updateTask("1","Marta ma kota i psa.");
            case "delete" -> service.deleteTask("3");
            case "mark-in-progress" -> System.out.println("Marking task in progress...");
            case "mark-done" -> System.out.println("Marking task done...");
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