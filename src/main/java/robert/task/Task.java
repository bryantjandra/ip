package robert.task;

public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a robert.task.Task with the given description.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Marks this task as done.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Marks this task as not done.
     */
    public void markAsNotDone() {
        isDone = false;
    }

    /**
     * Returns the status icon of the task (X if done, blank otherwise).
     *
     * @return "X" if done, otherwise " ".
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Returns the description of the task.
     *
     * @return The task's description string.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the string representation of this task, including done-status.
     *
     * @return A string representation of the task, e.g., "[X] read book".
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
