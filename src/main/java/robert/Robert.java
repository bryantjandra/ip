package robert;

import java.io.IOException;
import java.util.ArrayList;

import robert.command.CommandType;
import robert.parser.Parser;
import robert.storage.Storage;
import robert.task.Deadline;
import robert.task.Event;
import robert.task.Task;
import robert.task.TaskList;
import robert.task.Todo;

/**
 * Main class of the Robert chatbot application, refactored to remove all console prints.
 * Instead, methods return strings that the GUI can display.
 */
public class Robert {
    private Storage storage;
    private TaskList tasks;

    /**
     * Creates a Robert chatbot with the specified file path for data storage.
     * Loads tasks from file. If loading fails, an empty TaskList is used.
     *
     * @param filePath The path to the file where tasks will be saved/loaded.
     */
    public Robert(String filePath) {
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (IOException e) {
            // If loading fails, just start with an empty TaskList
            tasks = new TaskList();
        }
    }

    /**
     * Returns the "welcome" message that was previously printed on startup.
     * Use this immediately in the GUI (e.g., after creating the Robert object)
     * so that the user sees the greeting without typing anything.
     *
     * @return The multiline welcome message as a String.
     */
    public String getStartupMessage() {
        // No divider line, as requested
        return " Hello! I'm Robert\n What can I do for you?";
    }

    /**
     * Takes a user input string, parses it, executes the command, and returns
     * the exact text that was previously printed to the console.
     *
     * @param input Full user input string (e.g., "todo read book").
     * @return The response lines that should be displayed in the GUI.
     */
    public String getResponse(String input) {
        StringBuilder sb = new StringBuilder();
        try {
            CommandType commandWord = Parser.parse(input);
            switch (commandWord) {
            case BYE:
                sb.append(" Bye. Hope to see you again soon!");
                break;

            case LIST:
                sb.append(" Here are the tasks in your list:\n");
                for (int i = 0; i < tasks.size(); i++) {
                    sb.append(" ").append(i + 1).append(".").append(tasks.get(i)).append("\n");
                }
                break;

            case TODO:
                sb.append(handleTodo(input.substring("todo".length()).trim()));
                break;

            case DEADLINE:
                sb.append(handleDeadline(input.substring("deadline".length()).trim()));
                break;

            case EVENT:
                sb.append(handleEvent(input.substring("event".length()).trim()));
                break;

            case MARK:
                sb.append(handleMark(input.substring("mark".length()).trim()));
                break;

            case UNMARK:
                sb.append(handleUnmark(input.substring("unmark".length()).trim()));
                break;

            case DELETE:
                sb.append(handleDelete(input.substring("delete".length()).trim()));
                break;

            case FIND:
                sb.append(handleFind(input.substring("find".length()).trim()));
                break;

            case EMPTY:
                throw new RobertException("OOPS!!! You typed an empty command!");

            default:
                throw new RobertException("OOPS!!! What do you mean by that?");
            }
        } catch (RobertException e) {
            sb.append(e.getMessage());
        } catch (IOException e) {
            sb.append("OOPS!!! Unable to save tasks!");
        }
        return sb.toString().trim();
    }

    /**
     * Creates and adds a new {@code Todo} task, then returns the textual response.
     *
     * @param description The description of the ToDo task (must not be empty).
     * @return A string containing the lines that would have been printed previously.
     * @throws RobertException If the description is empty.
     * @throws IOException     If saving tasks fails.
     */
    private String handleTodo(String description) throws RobertException, IOException {
        if (description.isEmpty()) {
            throw new RobertException("OOPS!!! The description of a todo should not be empty.");
        }
        Todo t = new Todo(description);
        tasks.add(t);
        storage.save(tasks.getTasks());

        StringBuilder sb = new StringBuilder();
        sb.append(" Got it. I've added this task:\n");
        sb.append("   ").append(t).append("\n");
        sb.append(" Now you have ").append(tasks.size()).append(" tasks in the list.");
        return sb.toString();
    }

    /**
     * Creates and adds a new {@code Deadline} task, then returns the textual response.
     *
     * @param desc The raw input after the "deadline" keyword, e.g., "return book /by 2025-01-01".
     * @return A string containing the lines that would have been printed previously.
     * @throws RobertException If the format is invalid or fields are empty.
     * @throws IOException     If saving tasks fails.
     */
    private String handleDeadline(String desc) throws RobertException, IOException {
        if (!desc.contains("/by")) {
            throw new RobertException("OOPS!!! A deadline must have '/by <time>'!");
        }
        String[] parts = desc.split("/by");
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
        Deadline d = new Deadline(description, by);
        tasks.add(d);
        storage.save(tasks.getTasks());

        StringBuilder sb = new StringBuilder();
        sb.append(" Got it. I've added this task:\n");
        sb.append("   ").append(d).append("\n");
        sb.append(" Now you have ").append(tasks.size()).append(" tasks in the list.");
        return sb.toString();
    }

    /**
     * Creates and adds a new {@code Event} task, then returns the textual response.
     *
     * @param desc The raw input after the "event" keyword, e.g., "some event /from 2025-01-01 /to 2025-01-02".
     * @return A string containing the lines that would have been printed previously.
     * @throws RobertException If format is invalid or fields are empty.
     * @throws IOException     If saving tasks fails.
     */
    private String handleEvent(String desc) throws RobertException, IOException {
        if (!desc.contains("/from") || !desc.contains("/to")) {
            throw new RobertException("OOPS!!! An event must have '/from <start>' and '/to <end>'!");
        }
        String[] fromSplit = desc.split("/from");
        if (fromSplit.length < 2) {
            throw new RobertException("OOPS!!! Missing '/from' portion for event.");
        }
        String description = fromSplit[0].trim();
        String startAndEnd = fromSplit[1].trim();
        String[] toSplit = startAndEnd.split("/to");
        if (toSplit.length < 2) {
            throw new RobertException("OOPS!!! Missing '/to' portion for event.");
        }
        String startTime = toSplit[0].trim();
        String endTime = toSplit[1].trim();
        if (description.isEmpty()) {
            throw new RobertException("OOPS!!! The description of an event cannot be empty.");
        }
        if (startTime.isEmpty() || endTime.isEmpty()) {
            throw new RobertException("OOPS!!! The start and end times for an event cannot be empty.");
        }
        Event e = new Event(description, startTime, endTime);
        tasks.add(e);
        storage.save(tasks.getTasks());

        StringBuilder sb = new StringBuilder();
        sb.append(" Got it. I've added this task:\n");
        sb.append("   ").append(e).append("\n");
        sb.append(" Now you have ").append(tasks.size()).append(" tasks in the list.");
        return sb.toString();
    }

    /**
     * Marks a task as done.
     *
     * @param arg A string representing the task number to mark, e.g., "2".
     * @return A string response containing the lines that would have been printed.
     * @throws IOException     If saving tasks fails.
     * @throws RobertException If the task number is invalid or out of range.
     */
    private String handleMark(String arg) throws IOException, RobertException {
        if (arg.isEmpty()) {
            throw new RobertException("Please specify which task to mark!");
        }
        int taskNum = Integer.parseInt(arg);
        if (taskNum < 1 || taskNum > tasks.size()) {
            throw new RobertException("Task number is out of range!");
        }
        tasks.get(taskNum - 1).markAsDone();
        storage.save(tasks.getTasks());

        StringBuilder sb = new StringBuilder();
        sb.append(" Nice! I've marked this task as done:\n");
        sb.append("   ").append(tasks.get(taskNum - 1));
        return sb.toString();
    }

    /**
     * Unmarks a task (marks as not done).
     *
     * @param arg A string representing the task number to unmark, e.g., "2".
     * @return A string response containing the lines that would have been printed.
     * @throws IOException     If saving tasks fails.
     * @throws RobertException If the task number is invalid or out of range.
     */
    private String handleUnmark(String arg) throws IOException, RobertException {
        if (arg.isEmpty()) {
            throw new RobertException("Please specify which task to unmark!");
        }
        int taskNum = Integer.parseInt(arg);
        if (taskNum < 1 || taskNum > tasks.size()) {
            throw new RobertException("Task number is out of range!");
        }
        tasks.get(taskNum - 1).markAsNotDone();
        storage.save(tasks.getTasks());

        StringBuilder sb = new StringBuilder();
        sb.append(" OK, I've marked this task as not done yet:\n");
        sb.append("   ").append(tasks.get(taskNum - 1));
        return sb.toString();
    }

    /**
     * Deletes a task from the task list.
     *
     * @param arg A string representing the task number to delete.
     * @return A string response containing the lines that would have been printed.
     * @throws IOException     If saving tasks fails.
     * @throws RobertException If the task number is invalid or out of range.
     */
    private String handleDelete(String arg) throws IOException, RobertException {
        if (arg.isEmpty()) {
            throw new RobertException("Please specify which task to delete!");
        }
        int taskNum = Integer.parseInt(arg);
        if (taskNum < 1 || taskNum > tasks.size()) {
            throw new RobertException("Task number is out of range!");
        }
        Task removedTask = tasks.remove(taskNum - 1);
        storage.save(tasks.getTasks());

        StringBuilder sb = new StringBuilder();
        sb.append(" Noted. I've removed this task:\n");
        sb.append("   ").append(removedTask).append("\n");
        sb.append(" Now you have ").append(tasks.size()).append(" tasks in the list.");
        return sb.toString();
    }

    /**
     * Finds tasks whose description matches the given keyword.
     *
     * @param keyword The search keyword.
     * @return A string listing the matching tasks, or indicating none found.
     * @throws RobertException If the keyword is empty.
     */
    private String handleFind(String keyword) throws RobertException {
        if (keyword.isEmpty()) {
            throw new RobertException("OOPS!!! The find command requires a keyword!");
        }
        ArrayList<Task> matchedTasks = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            Task current = tasks.get(i);
            if (current.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                matchedTasks.add(current);
            }
        }

        if (matchedTasks.isEmpty()) {
            return " No tasks matched your search: " + keyword;
        } else {
            StringBuilder sb = new StringBuilder(" Here are the matching tasks in your list:\n");
            for (int i = 0; i < matchedTasks.size(); i++) {
                sb.append(" ").append(i + 1).append(".").append(matchedTasks.get(i)).append("\n");
            }
            return sb.toString();
        }
    }
}
