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
        Scanner sc = new Scanner(f);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split("\\|");
            String type = parts[0].trim();
            String doneFlag = parts[1].trim();
            if (type.equals("T")) {
                Todo t = new Todo(parts[2].trim());
                if (doneFlag.equals("1")) {
                    t.markAsDone();
                }
                tasks.add(t);
            } else if (type.equals("D")) {
                Deadline d = new Deadline(parts[2].trim(), parts[3].trim());
                if (doneFlag.equals("1")) {
                    d.markAsDone();
                }
                tasks.add(d);
            } else if (type.equals("E")) {
                Event e = new Event(parts[2].trim(), parts[3].trim(), parts[4].trim());
                if (doneFlag.equals("1")) {
                    e.markAsDone();
                }
                tasks.add(e);
            }
        }
        return tasks;
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
            if (t instanceof Todo) {
                fw.write("T|"
                        + (t.getStatusIcon().equals("X") ? "1" : "0")
                        + "|"
                        + t.getDescription()
                        + System.lineSeparator());
            } else if (t instanceof Deadline) {
                Deadline d = (Deadline) t;
                fw.write("D|"
                        + (d.getStatusIcon().equals("X") ? "1" : "0")
                        + "|"
                        + d.getDescription()
                        + "|"
                        + d.getByDate()
                        + System.lineSeparator());
            } else if (t instanceof Event) {
                Event e = (Event) t;
                fw.write("E|"
                        + (e.getStatusIcon().equals("X") ? "1" : "0")
                        + "|"
                        + e.getDescription()
                        + "|"
                        + e.getFrom()
                        + "|"
                        + e.getTo()
                        + System.lineSeparator());
            }
        }
        fw.close();
    }
}
