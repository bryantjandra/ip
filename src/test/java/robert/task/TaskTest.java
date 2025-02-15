package robert.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests the basic Task class.
 */
public class TaskTest {

    @Test
    public void constructor_newTask_notDoneByDefault() {
        Task t = new Task("Read book");
        assertFalse(t.isDone, "New Task should not be done by default");
    }

    @Test
    public void markAsDone_changesIsDoneToTrue() {
        Task t = new Task("Do laundry");
        t.markAsDone();
        assertTrue(t.isDone);
    }

    @Test
    public void markAsNotDone_changesIsDoneToFalse() {
        Task t = new Task("Do laundry");
        t.markAsDone();
        t.markAsNotDone();
        assertFalse(t.isDone);
    }

    @Test
    public void toString_correctFormat() {
        Task t = new Task("Sample task");
        String expected = "[ ] Sample task";
        assertEquals(expected, t.toString());
    }
}
