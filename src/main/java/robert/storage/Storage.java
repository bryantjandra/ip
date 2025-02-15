package robert.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import robert.task.Deadline;
import robert.task.Event;
import robert.task.Task;
import robert.task.Todo;

/**
 * Deals with loading tasks from a file and saving tasks to a file.
 */
public class Storage {
    private String filePath;

    /**
     * Constructs a Storage object with the specified file path.
     *
     * @param filePath The path of the file where tasks will be stored and loaded from.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the file specified by filePath.
     *
     * @return An ArrayList of Task objects loaded from the file.
     * @throws IOException If an I/O error occurs.
     */
    public ArrayList<Task> load() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();
        File f = new File(filePath);

        if (!f.exists()) {
            f.getParentFile().mkdirs();
            f.createNewFile();
            return tasks;
        }

        try (Scanner sc = new Scanner(f)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                Task t = parseTaskLine(line);
                if (t != null) {
                    tasks.add(t);
                }
            }
        }

        return tasks;
    }

    /**
     * Parses a single line from storage into a Task object.
     *
     * @param line One line from the tasks file.
     * @return The corresponding Task object (Todo/Deadline/Event), or null if line is invalid.
     */
    private Task parseTaskLine(String line) {
        String[] parts = line.split("\\|");
        if (parts.length < 3) {
            return null;
        }

        String type = parts[0].trim();
        String doneFlag = parts[1].trim();

        Task task = null;
        switch (type) {
        case "T":
            task = new Todo(parts[2].trim());
            break;
        case "D":
            if (parts.length < 4) {
                return null;
            }
            task = new Deadline(parts[2].trim(), parts[3].trim());
            break;
        case "E":
            if (parts.length < 5) {
                return null;
            }
            task = new Event(parts[2].trim(), parts[3].trim(), parts[4].trim());
            break;
        default:
            return null;
        }

        if ("1".equals(doneFlag)) {
            task.markAsDone();
        }
        return task;
    }

    /**
     * Saves the given list of tasks to the file specified by filePath.
     *
     * @param tasks An ArrayList of Task objects to be saved.
     * @throws IOException If an I/O error occurs.
     */
    public void save(ArrayList<Task> tasks) throws IOException {
        FileWriter fw = new FileWriter(filePath);
        for (Task t : tasks) {
            fw.write(convertTaskToString(t) + System.lineSeparator());
        }
        fw.close();
    }

    /**
     * Converts a Task to a storage-friendly string representation.
     *
     * @param t A Task object (Todo, Deadline, or Event).
     * @return A line to be written into the storage file.
     */
    private String convertTaskToString(Task t) {
        String doneFlag = t.getStatusIcon().equals("X") ? "1" : "0";

        if (t instanceof Todo) {
            return "T|" + doneFlag + "|" + t.getDescription();
        } else if (t instanceof Deadline) {
            Deadline d = (Deadline) t;
            return "D|" + doneFlag + "|" + d.getDescription() + "|" + d.getByDate();
        } else if (t instanceof Event) {
            Event e = (Event) t;
            return "E|" + doneFlag + "|" + e.getDescription() + "|" + e.getStartTime() + "|" + e.getEndTime();
        } else {
            return "";
        }
    }
}
