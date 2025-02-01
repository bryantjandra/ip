package robert.storage;

import robert.task.Deadline;
import robert.task.Event;
import robert.task.Task;
import robert.task.Todo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

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

    public void save(ArrayList<Task> tasks) throws IOException {
        FileWriter fw = new FileWriter(filePath);
        for (Task t : tasks) {
            if (t instanceof Todo) {
                fw.write("T|" + (t.getStatusIcon().equals("X") ? "1" : "0") + "|" + t.getDescription() + System.lineSeparator());
            } else if (t instanceof Deadline d) {
                fw.write("D|" + (d.getStatusIcon().equals("X") ? "1" : "0") + "|" + d.getDescription() + "|" + d.getByDate() + System.lineSeparator());
            } else if (t instanceof Event e) {
                fw.write("E|" + (e.getStatusIcon().equals("X") ? "1" : "0") + "|" + e.getDescription() + "|" + e.getFrom() + "|" + e.getTo() + System.lineSeparator());
            }
        }
        fw.close();
    }
}
