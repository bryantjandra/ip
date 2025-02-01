package robert.task;

/**
 * Represents a task that starts at a specific time and ends at a specific time.
 */
public class Event extends Task {

    protected String from;
    protected String to;

    /**
     * Constructs an robert.task.Event with a description, a start time, and an end time.
     *
     * @param description The event's description.
     * @param from        The start time.
     * @param to          The end time.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }


    /**
     * Returns the string representation of the event. This includes the
     * status, the description, and time.
     *
     * @return The string representation of the event.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
