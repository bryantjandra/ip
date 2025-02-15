package robert.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import robert.task.Deadline;
import robert.task.Task;
import robert.task.Todo;

/**
 * Tests the Storage class.
 */
public class StorageTest {

    @TempDir
    File tempFolder;

    @Test
    public void load_emptyFile_returnsEmptyList() throws IOException {
        File emptyFile = new File(tempFolder, "testTasks.txt");
        emptyFile.createNewFile();
        Storage storage = new Storage(emptyFile.getPath());

        ArrayList<Task> tasks = storage.load();
        assertEquals(0, tasks.size());
    }

    @Test
    public void saveAndLoad_validTasks_success() throws IOException {
        File testFile = new File(tempFolder, "testTasks.txt");
        Storage storage = new Storage(testFile.getPath());

        ArrayList<Task> tasksToSave = new ArrayList<>();
        tasksToSave.add(new Todo("Buy groceries"));
        tasksToSave.add(new Deadline("Submit assignment", "2025-01-01"));

        storage.save(tasksToSave);

        ArrayList<Task> loadedTasks = storage.load();
        assertEquals(2, loadedTasks.size());
        assertEquals("[T][ ] Buy groceries", loadedTasks.get(0).toString());
        assertEquals("[D][ ] Submit assignment (by: Jan 1 2025)", loadedTasks.get(1).toString());
    }

    @Test
    public void load_malformedLine_ignoreOrHandleGracefully() throws IOException {
        File testFile = new File(tempFolder, "testTasks.txt");
        Files.writeString(testFile.toPath(), "???|???|malformed\n");
        Storage storage = new Storage(testFile.getPath());

        ArrayList<Task> tasks = storage.load();
        assertEquals(0, tasks.size());
    }
}
