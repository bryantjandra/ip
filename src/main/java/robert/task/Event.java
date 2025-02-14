package robert.task;

/**
 * Represents a task that starts at a specific time and ends at a specific time.
 */
public class Event extends Task {

    protected String startTime;
    protected String endTime;

    /**
     * Constructs a robert.task.Event with a description, a start time, and an end time.
     *
     * @param description The event's description.
     * @param startTime        The start time.
     * @param endTime          The end time.
     */
    public Event(String description, String startTime, String endTime) {
        super(description);
        assert description != null : "Event description cannot be null";
        assert startTime != null : "Event startTime cannot be null";
        assert endTime != null : "Event endTime cannot be null";
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }


    /**
     * Returns the string representation of the event. This includes the
     * status, the description, and time.
     *
     * @return The string representation of the event.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + startTime + " to: " + endTime + ")";
    }
}
