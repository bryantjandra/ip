package robert.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests the Event class.
 */
public class EventTest {

    @Test
    public void constructor_validInputs_success() {
        Event e = new Event("Concert", "2025-01-01", "2025-01-02");
        assertEquals("2025-01-01", e.getStartTime());
        assertEquals("2025-01-02", e.getEndTime());
    }

    @Test
    public void toString_showsCorrectFormat() {
        Event e = new Event("Conference", "2025-03-05", "2025-03-07");
        String expected = "[E][ ] Conference (from: 2025-03-05 to: 2025-03-07)";
        assertEquals(expected, e.toString());
    }
}
