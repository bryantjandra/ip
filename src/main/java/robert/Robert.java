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
        assert filePath != null : "Robert constructor must have a non-null file path";
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (IOException e) {
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
        return "Good day, sir. I am Robert, your personal butler. "
                + "How may I be of service today, sir?";
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
                sb.append("Farewell, sir. I hope to see you again soon!");
                break;

            case LIST:
                sb.append("Certainly, sir. Here are the tasks in your list:\n");
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
                throw new RobertException("Pardon me, sir, but it appears you typed an empty command.");

            case SORT:
                sb.append(handleSort());
                break;

            default:
                throw new RobertException("Pardon me, sir. I am afraid I did not understand that command.");
            }
        } catch (RobertException e) {
            // This typically means user did something invalid
            sb.append(e.getMessage());
        } catch (IOException e) {
            // This typically means we failed saving/loading
            sb.append("My apologies, sir. I'm unable to save tasks at the moment!");
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
            throw new RobertException("The description of a todo cannot be empty.");
        }
        Todo t = new Todo(description);
        tasks.add(t);
        storage.save(tasks.getTasks());

        StringBuilder sb = new StringBuilder();
        sb.append("Certainly, sir. I have added this task:\n");
        sb.append("   ").append(t).append("\n");
        sb.append("You now have ").append(tasks.size()).append(" tasks in your list, sir.");
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
            throw new RobertException("A deadline must have '/by <time>'.");
        }
        String[] parts = desc.split("/by");
        if (parts.length < 2) {
            throw new RobertException("A deadline must have a description and a time after '/by'.");
        }
        String description = parts[0].trim();
        String by = parts[1].trim();
        if (description.isEmpty()) {
            throw new RobertException("The description of a deadline cannot be empty.");
        }
        if (by.isEmpty()) {
            throw new RobertException("The time of a deadline cannot be empty.");
        }
        Deadline d = new Deadline(description, by);
        tasks.add(d);
        storage.save(tasks.getTasks());

        StringBuilder sb = new StringBuilder();
        sb.append("Certainly, sir. I have added this task:\n");
        sb.append("   ").append(d).append("\n");
        sb.append("You now have ").append(tasks.size()).append(" tasks in your list, sir.");
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
            throw new RobertException("An event must have '/from <start>' and '/to <end>'.");
        }
        String[] fromSplit = desc.split("/from");
        if (fromSplit.length < 2) {
            throw new RobertException("Missing '/from' portion for the event.");
        }
        String description = fromSplit[0].trim();
        String startAndEnd = fromSplit[1].trim();
        String[] toSplit = startAndEnd.split("/to");
        if (toSplit.length < 2) {
            throw new RobertException("Missing '/to' portion for the event.");
        }
        String startTime = toSplit[0].trim();
        String endTime = toSplit[1].trim();
        if (description.isEmpty()) {
            throw new RobertException("The description of an event cannot be empty.");
        }
        if (startTime.isEmpty() || endTime.isEmpty()) {
            throw new RobertException("The start/end times for the event cannot be empty.");
        }
        Event e = new Event(description, startTime, endTime);
        tasks.add(e);
        storage.save(tasks.getTasks());

        StringBuilder sb = new StringBuilder();
        sb.append("Certainly, sir. I have added this task:\n");
        sb.append("   ").append(e).append("\n");
        sb.append("You now have ").append(tasks.size()).append(" tasks in your list, sir.");
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
            throw new RobertException("Please specify which task to mark.");
        }
        int taskNum = Integer.parseInt(arg);
        if (taskNum < 1 || taskNum > tasks.size()) {
            throw new RobertException("That task number is out of range, sir.");
        }
        tasks.get(taskNum - 1).markAsDone();
        storage.save(tasks.getTasks());

        StringBuilder sb = new StringBuilder();
        sb.append("Certainly, sir. I've marked this task as done:\n");
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
            throw new RobertException("Please specify which task to unmark.");
        }
        int taskNum = Integer.parseInt(arg);
        if (taskNum < 1 || taskNum > tasks.size()) {
            throw new RobertException("That task number is out of range, sir.");
        }
        tasks.get(taskNum - 1).markAsNotDone();
        storage.save(tasks.getTasks());

        StringBuilder sb = new StringBuilder();
        sb.append("Certainly, sir. I've marked this task as not done yet:\n");
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
            throw new RobertException("Please specify which task to delete.");
        }
        int taskNum = Integer.parseInt(arg);
        if (taskNum < 1 || taskNum > tasks.size()) {
            throw new RobertException("That task number is out of range, sir.");
        }
        Task removedTask = tasks.remove(taskNum - 1);
        storage.save(tasks.getTasks());

        StringBuilder sb = new StringBuilder();
        sb.append("Certainly, sir. I've removed this task:\n");
        sb.append("   ").append(removedTask).append("\n");
        sb.append("You now have ").append(tasks.size()).append(" tasks in the list, sir.");
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
            throw new RobertException("The find command requires a keyword to search for, sir.");
        }
        ArrayList<Task> matchedTasks = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            Task current = tasks.get(i);
            if (current.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                matchedTasks.add(current);
            }
        }

        if (matchedTasks.isEmpty()) {
            return "My apologies, sir. No tasks matched your search: " + keyword;
        } else {
            StringBuilder sb = new StringBuilder("Certainly, sir. Here are the matching tasks:\n");
            for (int i = 0; i < matchedTasks.size(); i++) {
                sb.append(" ").append(i + 1).append(".").append(matchedTasks.get(i)).append("\n");
            }
            return sb.toString();
        }
    }

    /**
     * Sorts all Deadlines by ascending date, placing them first in the list,
     * followed by all other tasks in their current order.
     *
     * @return A string response indicating the new order of tasks.
     * @throws IOException If saving tasks fails.
     */
    private String handleSort() throws IOException {
        ArrayList<Task> deadlines = new ArrayList<>();
        ArrayList<Task> others = new ArrayList<>();

        for (Task t : tasks.getTasks()) {
            if (t instanceof Deadline) {
                deadlines.add(t);
            } else {
                others.add(t);
            }
        }

        deadlines.sort((a, b) -> {
            Deadline da = (Deadline) a;
            Deadline db = (Deadline) b;
            return da.getByDate().compareTo(db.getByDate());
        });

        tasks.getTasks().clear();
        tasks.getTasks().addAll(deadlines);
        tasks.getTasks().addAll(others);

        storage.save(tasks.getTasks());

        StringBuilder sb = new StringBuilder("Certainly, sir. Deadlines have now been sorted by date.\n");
        sb.append("Here is your newly arranged list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(" ").append(i + 1).append(".").append(tasks.get(i)).append("\n");
        }
        return sb.toString();
    }
}
