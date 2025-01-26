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
                    System.out.println(" " + (i + 1) + "." + tasks[i].toString());
                }
                System.out.println(line);

            } else if (userCommand.startsWith("mark ")) {
                String[] parts = userCommand.split(" ");
                int taskNum = Integer.parseInt(parts[1]);
                tasks[taskNum - 1].markAsDone();
                System.out.println(line);
                System.out.println(" Nice! I've marked this task as done:");
                System.out.println("   " + tasks[taskNum - 1]);
                System.out.println(line);

            } else if (userCommand.startsWith("unmark ")) {
                String[] parts = userCommand.split(" ");
                int taskNum = Integer.parseInt(parts[1]);
                tasks[taskNum - 1].markAsNotDone();
                System.out.println(line);
                System.out.println(" OK, I've marked this task as not done yet:");
                System.out.println("   " + tasks[taskNum - 1]);
                System.out.println(line);

            } else if (userCommand.startsWith("todo ")) {
                String description = userCommand.substring("todo".length()).trim();

                Todo newTodo = new Todo(description);
                tasks[taskCount] = newTodo;
                taskCount++;
                System.out.println(line);
                System.out.println(" Got it. I've added this task:");
                System.out.println("   " + newTodo.toString());
                System.out.println(" Now you have " + taskCount + " tasks in the list.");
                System.out.println(line);

            } else if (userCommand.startsWith("deadline ")) {

                String withoutKeyword = userCommand.substring("deadline".length()).trim();

                String[] parts = withoutKeyword.split("/by");
                String description = parts[0].trim();
                String by = parts[1].trim();

                Deadline newDeadline = new Deadline(description, by);
                tasks[taskCount] = newDeadline;
                taskCount++;

                System.out.println(line);
                System.out.println(" Got it. I've added this task:");
                System.out.println("   " + newDeadline);
                System.out.println(" Now you have " + taskCount + " tasks in the list.");
                System.out.println(line);

            } else if (userCommand.startsWith("event ")) {

                String withoutKeyword = userCommand.substring("event".length()).trim();

                String[] fromSplit = withoutKeyword.split("/from");
                String description = fromSplit[0].trim();
                String fromAndTo = fromSplit[1].trim();


                String[] toSplit = fromAndTo.split("/to");
                String from = toSplit[0].trim();
                String to = toSplit[1].trim();

                Event newEvent = new Event(description, from, to);

                tasks[taskCount] = newEvent;
                taskCount++;

                System.out.println(line);
                System.out.println(" Got it. I've added this task:");
                System.out.println("   " + newEvent);
                System.out.println(" Now you have " + taskCount + " tasks in the list.");
                System.out.println(line);

            } else {
                System.out.println(line);
                System.out.println(" OOPS!!! I'm sorry, but I don't know what that means :-(");
                System.out.println(line);
            }
        }

        sc.close();
    }
}
