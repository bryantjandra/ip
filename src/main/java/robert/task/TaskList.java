package robert.task;

import java.util.ArrayList;

/**
 * Maintains a list of Task objects and provides operations to modify them.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList with an existing list of tasks.
     *
     * @param tasks The initial list of tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the end of the list.
     *
     * @param t The Task to add.
     */
    public void add(Task t) {
        assert t != null : "Cannot add a null Task to TaskList";
        tasks.add(t);
    }

    /**
     * Returns the task at the specified index.
     *
     * @param index The index of the task to retrieve.
     * @return The Task at that index.
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Removes and returns the task at the specified index.
     *
     * @param index The index of the task to remove.
     * @return The removed Task.
     */
    public Task remove(int index) {
        return tasks.remove(index);
    }

    /**
     * Returns the current number of tasks in the list.
     *
     * @return The size of the TaskList.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the underlying list of tasks.
     *
     * @return An ArrayList of Task objects.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
