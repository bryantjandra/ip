import java.io.IOException;

public class Robert {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Robert(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (IOException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            String fullCommand = ui.readCommand();
            ui.showLine();
            try {
                CommandType commandWord = Parser.parse(fullCommand);
                switch (commandWord) {
                    case BYE:
                        ui.showError(" Bye. Hope to see you again soon!");
                        ui.showLine();
                        isExit = true;
                        break;
                    case LIST:
                        ui.showError(" Here are the tasks in your list:");
                        for (int i = 0; i < tasks.size(); i++) {
                            ui.showError(" " + (i + 1) + "." + tasks.get(i));
                        }
                        break;
                    case TODO:
                        handleTodo(fullCommand.substring("todo".length()).trim());
                        break;
                    case DEADLINE:
                        handleDeadline(fullCommand.substring("deadline".length()).trim());
                        break;
                    case EVENT:
                        handleEvent(fullCommand.substring("event".length()).trim());
                        break;
                    case MARK:
                        handleMark(fullCommand.substring("mark".length()).trim());
                        break;
                    case UNMARK:
                        handleUnmark(fullCommand.substring("unmark".length()).trim());
                        break;
                    case DELETE:
                        handleDelete(fullCommand.substring("delete".length()).trim());
                        break;
                    case EMPTY:
                        throw new RobertException("OOPS!!! You typed an empty command!");
                    default:
                        throw new RobertException("OOPS!!! What do you mean by that?");
                }
            } catch (RobertException e) {
                ui.showError(e.getMessage());
            } catch (IOException e) {
                ui.showError("OOPS!!! Unable to save tasks!");
            }
        }
    }

    private void handleTodo(String description) throws RobertException, IOException {
        if (description.isEmpty()) {
            throw new RobertException("OOPS!!! The description of a todo should not be empty.");
        }
        Todo t = new Todo(description);
        tasks.add(t);
        storage.save(tasks.getTasks());
        ui.showError(" Got it. I've added this task:");
        ui.showError("   " + t);
        ui.showError(" Now you have " + tasks.size() + " tasks in the list.");
    }

    private void handleDeadline(String desc) throws RobertException, IOException {
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
        ui.showError(" Got it. I've added this task:");
        ui.showError("   " + d);
        ui.showError(" Now you have " + tasks.size() + " tasks in the list.");
    }

    private void handleEvent(String desc) throws RobertException, IOException {
        if (!desc.contains("/from") || !desc.contains("/to")) {
            throw new RobertException("OOPS!!! An event must have '/from <start>' and '/to <end>'!");
        }
        String[] fromSplit = desc.split("/from");
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
        Event e = new Event(description, from, to);
        tasks.add(e);
        storage.save(tasks.getTasks());
        ui.showError(" Got it. I've added this task:");
        ui.showError("   " + e);
        ui.showError(" Now you have " + tasks.size() + " tasks in the list.");
    }

    private void handleMark(String arg) throws IOException, RobertException {
        if (arg.isEmpty()) {
            throw new RobertException("Please specify which task to mark!");
        }
        int taskNum = Integer.parseInt(arg);
        if (taskNum < 1 || taskNum > tasks.size()) {
            throw new RobertException("Task number is out of range!");
        }
        tasks.get(taskNum - 1).markAsDone();
        storage.save(tasks.getTasks());
        ui.showError(" Nice! I've marked this task as done:");
        ui.showError("   " + tasks.get(taskNum - 1));
    }

    private void handleUnmark(String arg) throws IOException, RobertException {
        if (arg.isEmpty()) {
            throw new RobertException("Please specify which task to unmark!");
        }
        int taskNum = Integer.parseInt(arg);
        if (taskNum < 1 || taskNum > tasks.size()) {
            throw new RobertException("Task number is out of range!");
        }
        tasks.get(taskNum - 1).markAsNotDone();
        storage.save(tasks.getTasks());
        ui.showError(" OK, I've marked this task as not done yet:");
        ui.showError("   " + tasks.get(taskNum - 1));
    }

    private void handleDelete(String arg) throws IOException, RobertException {
        if (arg.isEmpty()) {
            throw new RobertException("Please specify which task to delete!");
        }
        int taskNum = Integer.parseInt(arg);
        if (taskNum < 1 || taskNum > tasks.size()) {
            throw new RobertException("Task number is out of range!");
        }
        Task removedTask = tasks.remove(taskNum - 1);
        storage.save(tasks.getTasks());
        ui.showError(" Noted. I've removed this task:");
        ui.showError("   " + removedTask);
        ui.showError(" Now you have " + tasks.size() + " tasks in the list.");
    }

    public static void main(String[] args) {
        new Robert("data/tasks.txt").run();
    }
}
