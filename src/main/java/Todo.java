/**
 * Represents a task without any date/time attached (a simple Todo).
 */
public class Todo extends Task {

    /**
     * Constructs a Todo with the specified description.
     *
     * @param description The description of the Todo task.
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
