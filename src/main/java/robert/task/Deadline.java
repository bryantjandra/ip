package robert.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task that must be done before a specific date.
 */
public class Deadline extends Task {

    protected LocalDate byDate;

    /**
     * Constructs a robert.task.Deadline task with a description and a 'by' date.
     *
     * @param description The task description.
     * @param by          The deadline date (as a string).
     * @throws DateTimeParseException If the provided date string cannot be parsed into a valid LocalDate.
     */
    public Deadline(String description, String by) throws DateTimeParseException {
        super(description);
        assert description != null : "Deadline description cannot be null";
        assert by != null : "Deadline 'by' date string cannot be null";
        this.byDate = LocalDate.parse(by);
    }

    public LocalDate getByDate() {
        return byDate;
    }


    /**
     * Returns the string representation of the deadline. This includes
     * the status, description, and deadline date formatted as "MMM dd yyyy".
     *
     * @return The string representation of the deadline in the format: [D][status] description (by: MMM dd yyyy)
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: "
                + byDate.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")";
    }
}
