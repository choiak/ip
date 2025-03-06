package inuChan;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import inuChan.tasks.Deadline;
import inuChan.tasks.Event;
import inuChan.tasks.Task;
import inuChan.tasks.ToDo;

public class Storage {
    private static final String MARKED = "X";

    public TaskList load() throws IOException {
        Files.createDirectories(Paths.get("./data"));
        File file = new File("./data/InuChan.txt");
        file.createNewFile();

        Scanner fileScanner = new Scanner(file);
        TaskList taskList = new TaskList();

        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            Task task = getTaskFromLine(line);
            taskList.addTask(task);
        }
        return taskList;
    }


    /**
     * Read the input line and return a Task object.
     *
     * @param line The input line.
     * @return Task object.
     * @throws RuntimeException If the input line is not in the correct format.
     */
    private Task getTaskFromLine(String line) throws RuntimeException {
        // Split the line into tokens separated by SPLITTER "]\|["
        String[] tokens = line.split("]\\|\\[");

        Task task = switch (tokens[0]) {
            case "T" -> new ToDo(tokens[2]);
            case "D" -> new Deadline(tokens[2], tokens[3]);
            case "E" -> new Event(tokens[2], tokens[3], tokens[4]);
            default -> throw new RuntimeException();
        };

        if (tokens[1].equals(MARKED)) {
            task.markTask(true);
        }
        return task;
    }

    /**
     * Save the task list into the file.
     *
     * @param taskList Task list to be saved.
     * @throws IOException If any error during the writing of file.
     */
    public void writeData(TaskList taskList) throws IOException{
        FileWriter fileWriter = new FileWriter("./data/InuChan.txt");
        taskList.writeData(fileWriter);
        fileWriter.close();
    }
}
