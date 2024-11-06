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
            case "update" -> service.updateTask("1","Marta ma psa.");
            case "delete" -> System.out.println("Deleting task...");
            case "mark-in-progress" -> System.out.println("Marking task in progress...");
            case "mark-done" -> System.out.println("Marking task done...");
            case "list" -> {
                command = args.length > 1 ? args[1] : "";
                switch (command) {
                case "" -> System.out.println("Listing all tasks...");
                case "done" -> System.out.println("Listing done tasks...");
                case "todo" -> System.out.println("Listing todo tasks...");
                case "in-progress" -> System.out.println("Listing in tasks...");
                default -> throw new IllegalStateException("Unexpected value: " + command);}
            }
            default -> throw new IllegalStateException("Unexpected value: " + command);
        }
        service.saveToJSON();
    }
}