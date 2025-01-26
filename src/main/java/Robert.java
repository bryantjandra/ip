import java.util.ArrayList;
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

        ArrayList<Task> tasks = new ArrayList<>();

        while (true) {
            String userCommand = sc.nextLine().trim();
            if (userCommand.isEmpty()) {
                try {
                    throw new RobertException("OOPS!!! You typed an empty command!");
                } catch (RobertException e) {
                    System.out.println(e.getMessage());
                    System.out.println(line);
                }
                continue;
            }

            String[] parts = userCommand.split(" ", 2);
            CommandType commandType = CommandType.parseCommand(parts[0]);
            String arguments = parts.length > 1 ? parts[1].trim() : "";

            try {
                switch (commandType) {
                    case BYE:
                        System.out.println(" Bye. Hope to see you again soon!");
                        System.out.println(line);
                        sc.close();
                        return;

                    case LIST:
                        System.out.println(" Here are the tasks in your list:");
                        for (int i = 0; i < tasks.size(); i++) {
                            System.out.println(" " + (i + 1) + "." + tasks.get(i));
                        }
                        System.out.println(line);
                        break;

                    case MARK:
                        if (arguments.isEmpty()) {
                            throw new RobertException("Please specify which task to mark!");
                        }
                        int markIdx = Integer.parseInt(arguments);
                        if (markIdx < 1 || markIdx > tasks.size()) {
                            throw new RobertException("Task number is out of range!");
                        }
                        tasks.get(markIdx - 1).markAsDone();
                        System.out.println(" Nice! I've marked this task as done:");
                        System.out.println("   " + tasks.get(markIdx - 1));
                        System.out.println(line);
                        break;

                    case UNMARK:
                        if (arguments.isEmpty()) {
                            throw new RobertException("Please specify which task to unmark!");
                        }
                        int unmarkIdx = Integer.parseInt(arguments);
                        if (unmarkIdx < 1 || unmarkIdx > tasks.size()) {
                            throw new RobertException("Task number is out of range!");
                        }
                        tasks.get(unmarkIdx - 1).markAsNotDone();
                        System.out.println(" OK, I've marked this task as not done yet:");
                        System.out.println("   " + tasks.get(unmarkIdx - 1));
                        System.out.println(line);
                        break;

                    case DELETE:
                        if (arguments.isEmpty()) {
                            throw new RobertException("Please specify which task to delete!");
                        }
                        int delIdx = Integer.parseInt(arguments);
                        if (delIdx < 1 || delIdx > tasks.size()) {
                            throw new RobertException("Task number is out of range!");
                        }
                        Task removedTask = tasks.remove(delIdx - 1);
                        System.out.println(" Noted. I've removed this task:");
                        System.out.println("   " + removedTask);
                        System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
                        System.out.println(line);
                        break;

                    case TODO:
                        if (arguments.isEmpty()) {
                            throw new RobertException("OOPS!!! The description of a todo should not be empty.");
                        }
                        Todo newTodo = new Todo(arguments);
                        tasks.add(newTodo);
                        System.out.println(" Got it. I've added this task:");
                        System.out.println("   " + newTodo);
                        System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
                        System.out.println(line);
                        break;

                    case DEADLINE:
                        if (!arguments.contains("/by")) {
                            throw new RobertException("OOPS!!! A deadline must have '/by <time>'!");
                        }
                        String[] deadlineParts = arguments.split("/by");
                        if (deadlineParts.length < 2) {
                            throw new RobertException("OOPS!!! A deadline must have a description and a time after '/by'.");
                        }
                        String deadlineDesc = deadlineParts[0].trim();
                        String byTime = deadlineParts[1].trim();
                        if (deadlineDesc.isEmpty()) {
                            throw new RobertException("OOPS!!! The description of a deadline cannot be empty.");
                        }
                        if (byTime.isEmpty()) {
                            throw new RobertException("OOPS!!! The time of a deadline cannot be empty.");
                        }
                        Deadline newDeadline = new Deadline(deadlineDesc, byTime);
                        tasks.add(newDeadline);
                        System.out.println(" Got it. I've added this task:");
                        System.out.println("   " + newDeadline);
                        System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
                        System.out.println(line);
                        break;

                    case EVENT:
                        if (!arguments.contains("/from") || !arguments.contains("/to")) {
                            throw new RobertException("OOPS!!! An event must have '/from <start>' and '/to <end>'!");
                        }
                        String[] fromSplit = arguments.split("/from");
                        if (fromSplit.length < 2) {
                            throw new RobertException("OOPS!!! Missing '/from' portion for event.");
                        }
                        String eventDesc = fromSplit[0].trim();
                        String fromAndTo = fromSplit[1].trim();
                        String[] toSplit = fromAndTo.split("/to");
                        if (toSplit.length < 2) {
                            throw new RobertException("OOPS!!! Missing '/to' portion for event.");
                        }
                        String from = toSplit[0].trim();
                        String to = toSplit[1].trim();
                        if (eventDesc.isEmpty()) {
                            throw new RobertException("OOPS!!! The description of an event cannot be empty.");
                        }
                        if (from.isEmpty() || to.isEmpty()) {
                            throw new RobertException("OOPS!!! The start and end times for an event cannot be empty.");
                        }
                        Event newEvent = new Event(eventDesc, from, to);
                        tasks.add(newEvent);
                        System.out.println(" Got it. I've added this task:");
                        System.out.println("   " + newEvent);
                        System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
                        System.out.println(line);
                        break;

                    default:
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
    }
}
