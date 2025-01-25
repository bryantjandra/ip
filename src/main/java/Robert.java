import java.util.Scanner;

public class Robert {
    public static void main(String[] args) {
        String line = "____________________________________________________________";
        Scanner sc = new Scanner(System.in);

        System.out.println(line);
        System.out.println(" Hello! I'm Robert");
        System.out.println(" What can I do for you?");
        System.out.println(line);

        Task[] tasks = new Task[100];
        int taskCount = 0;

        while (true) {
            String userCommand = sc.nextLine().trim();

            if (userCommand.equals("bye")) {
                System.out.println(line);
                System.out.println(" Bye. Hope to see you again soon!");
                System.out.println(line);
                break;

            } else if (userCommand.equals("list")) {
                System.out.println(line);
                System.out.println(" Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    Task t = tasks[i];
                    System.out.println(" " + (i + 1) + ".["
                            + t.getStatusIcon() + "] "
                            + t.getDescription());
                }
                System.out.println(line);

            } else if (userCommand.startsWith("mark ")) {
                String[] parts = userCommand.split(" ");
                int taskNum = Integer.parseInt(parts[1]);
                int index = taskNum - 1;

                tasks[index].markAsDone();

                System.out.println(line);
                System.out.println(" Nice! I've marked this task as done:");
                System.out.println("   [X] " + tasks[index].getDescription());
                System.out.println(line);

            } else if (userCommand.startsWith("unmark ")) {
                String[] parts = userCommand.split(" ");
                int taskNum = Integer.parseInt(parts[1]);
                int index = taskNum - 1;

                tasks[index].markAsNotDone();

                System.out.println(line);
                System.out.println(" OK, I've marked this task as not done yet:");
                System.out.println("   [ ] " + tasks[index].getDescription());
                System.out.println(line);

            } else {
                Task newTask = new Task(userCommand);
                tasks[taskCount] = newTask;
                taskCount++;

                System.out.println(line);
                System.out.println(" added: " + userCommand);
                System.out.println(line);
            }
        }

        sc.close();
    }
}
