import java.util.Scanner;

/**
 * The main class of the Robert chatbot, which reads commands from the user
 * and executes them until the user types "bye".
 */
public class Robert {
    /**
     * Entry point of the Robert chatbot.
     * Reads user commands, processes them, and produces output accordingly.
     *
     * @param args Command-line arguments (not used).
     */
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

            try {
                if (userCommand.equals("bye")) {
                    System.out.println(" Bye. Hope to see you again soon!");
                    System.out.println(line);
                    break;

                } else if (userCommand.equals("list")) {
                    System.out.println(" Here are the tasks in your list:");
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println(" " + (i + 1) + "." + tasks[i].toString());
                    }
                    System.out.println(line);

                } else if (userCommand.startsWith("mark ")) {
                    String[] parts = userCommand.split(" ");
                    if (parts.length < 2) {
                        throw new RobertException("Please specify which task to mark!");
                    }
                    int taskNum = Integer.parseInt(parts[1]);
                    if (taskNum < 1 || taskNum > taskCount) {
                        throw new RobertException("Task number is out of range!");
                    }

                    tasks[taskNum - 1].markAsDone();
                    System.out.println(" Nice! I've marked this task as done:");
                    System.out.println("   " + tasks[taskNum - 1]);
                    System.out.println(line);

                } else if (userCommand.startsWith("unmark ")) {
                    String[] parts = userCommand.split(" ");
                    if (parts.length < 2) {
                        throw new RobertException("Please specify which task to unmark!");
                    }
                    int taskNum = Integer.parseInt(parts[1]);
                    if (taskNum < 1 || taskNum > taskCount) {
                        throw new RobertException("Task number is out of range!");
                    }

                    tasks[taskNum - 1].markAsNotDone();
                    System.out.println(" OK, I've marked this task as not done yet:");
                    System.out.println("   " + tasks[taskNum - 1]);
                    System.out.println(line);

                } else if (userCommand.startsWith("todo")) {
                    String description = userCommand.substring("todo".length()).trim();
                    if (description.isEmpty()) {
                        throw new RobertException("OOPS!!! The description of a todo should not be empty.");
                    }

                    Todo newTodo = new Todo(description);
                    tasks[taskCount] = newTodo;
                    taskCount++;
                    System.out.println(" Got it. I've added this task:");
                    System.out.println("   " + newTodo.toString());
                    System.out.println(" Now you have " + taskCount + " tasks in the list.");
                    System.out.println(line);

                } else if (userCommand.startsWith("deadline")) {
                    String withoutKeyword = userCommand.substring("deadline".length()).trim();
                    if (withoutKeyword.isEmpty()) {
                        throw new RobertException("OOPS!!! The description of a deadline cannot be empty.");
                    }

                    if (!withoutKeyword.contains("/by")) {
                        throw new RobertException("OOPS!!! A deadline must have '/by <time>'!");
                    }

                    String[] parts = withoutKeyword.split("/by");
                    if (parts.length < 2) {
                        throw new RobertException("OOPS!!! A deadline must have a description and a time after '/by'.");
                    }
                    String description = parts[0].trim();
                    String by = parts[1].trim();

                    if (description.isEmpty()) {
                        throw new RobertException("OOPS!!! The description of a deadline cannot be empty.");
                    }
                    if (by.isEmpty()) {
                        throw new RobertException("OOPS!!! The time of a deadline cannot be empty.");
                    }

                    Deadline newDeadline = new Deadline(description, by);
                    tasks[taskCount] = newDeadline;
                    taskCount++;

                    System.out.println(" Got it. I've added this task:");
                    System.out.println("   " + newDeadline);
                    System.out.println(" Now you have " + taskCount + " tasks in the list.");
                    System.out.println(line);

                } else if (userCommand.startsWith("event")) {
                    String withoutKeyword = userCommand.substring("event".length()).trim();
                    if (withoutKeyword.isEmpty()) {
                        throw new RobertException("OOPS!!! The description of an event cannot be empty.");
                    }

                    if (!withoutKeyword.contains("/from") || !withoutKeyword.contains("/to")) {
                        throw new RobertException("OOPS!!! An event must have '/from <start>' and '/to <end>'!");
                    }

                    String[] fromSplit = withoutKeyword.split("/from");
                    if (fromSplit.length < 2) {
                        throw new RobertException("OOPS!!! Missing '/from' portion for event.");
                    }
                    String description = fromSplit[0].trim();
                    String fromAndTo = fromSplit[1].trim();

                    String[] toSplit = fromAndTo.split("/to");
                    if (toSplit.length < 2) {
                        throw new RobertException("OOPS!!! Missing '/to' portion for event.");
                    }
                    String from = toSplit[0].trim();
                    String to = toSplit[1].trim();

                    if (description.isEmpty()) {
                        throw new RobertException("OOPS!!! The description of an event cannot be empty.");
                    }
                    if (from.isEmpty() || to.isEmpty()) {
                        throw new RobertException("OOPS!!! The start and end times for an event cannot be empty.");
                    }

                    Event newEvent = new Event(description, from, to);
                    tasks[taskCount] = newEvent;
                    taskCount++;

                    System.out.println(" Got it. I've added this task:");
                    System.out.println("   " + newEvent);
                    System.out.println(" Now you have " + taskCount + " tasks in the list.");
                    System.out.println(line);

                } else if (userCommand.isEmpty()) {
                    throw new RobertException("OOPS!!! You typed an empty command!");

                } else {
                    throw new RobertException("OOPS!!! What do you mean by that?");
                }

            } catch (RobertException e) {
                System.out.println(e.getMessage());
                System.out.println(line);
            } catch (NumberFormatException e) {
                System.out.println("OOPS!!! The task number must be a valid integer!");
                System.out.println(line);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("OOPS!!! Task number is out of range!");
                System.out.println(line);
            }
        }

        sc.close();
    }
}
