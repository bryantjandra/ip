package robert.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the TaskList class.
 */
public class TaskListTest {

    private TaskList taskList;

    /**
     * Creates a new (empty) TaskList before each test.
     */
    @BeforeEach
    public void setUp() {
        taskList = new TaskList();
    }

    /**
     * Tests adding a task increases the size by 1.
     */
    @Test
    public void add_singleTask_sizeIncreases() {
        Todo todo = new Todo("Buy milk");
        taskList.add(todo);
        assertEquals(1, taskList.size());
    }

    /**
     * Tests remove() returns the correct task and reduces size.
     */
    @Test
    public void remove_indexZero_returnsTaskAndReducesSize() {
        Todo first = new Todo("task1");
        Todo second = new Todo("task2");
        taskList.add(first);
        taskList.add(second);

        Task removed = taskList.remove(0);
        assertSame(first, removed, "Removed task should be the first one added");
        assertEquals(1, taskList.size(), "TaskList size should be 1 after removal");
    }

    /**
     * Tests get() returns the correct Task.
     */
    @Test
    public void get_validIndex_returnsCorrectTask() {
        Todo first = new Todo("task1");
        taskList.add(first);

        Task t = taskList.get(0);
        assertSame(first, t);
    }

    /**
     * Tests getTasks() returns the underlying ArrayList with correct tasks.
     */
    @Test
    public void getTasks_nonEmptyList_correctTasks() {
        Todo t1 = new Todo("t1");
        Todo t2 = new Todo("t2");
        taskList.add(t1);
        taskList.add(t2);

        ArrayList<Task> actualList = taskList.getTasks();
        assertEquals(2, actualList.size());
        assertTrue(actualList.contains(t1));
        assertTrue(actualList.contains(t2));
    }
}
