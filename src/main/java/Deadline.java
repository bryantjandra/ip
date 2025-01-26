/**
 * Represents a task that must be done before a specific date/time.
 */
public class Deadline extends Task {

    protected String by;

    /**
     * Constructs a Deadline task with a description and a 'by' time.
     *
     * @param description The task description.
     * @param by          The deadline time (as a string).
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns the string representation of the deadline. This includes
     * the status, description, and deadline time.
     *
     * @return The string representation of the deadline.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
