package robert.task;

/**
 * Represents a task without any date/time attached (a simple robert.task.Todo).
 */
public class Todo extends Task {

    /**
     * Constructs a robert.task.Todo with the specified description.
     *
     * @param description The description of the robert.task.Todo task.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns the string representation of the todo. This includes the
     * status and description.
     *
     * @return The string representation of the todo.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
