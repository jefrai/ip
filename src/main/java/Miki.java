import command.Command;
import shigure.Ui;
import storage.Storage;
import task.TaskList;

import java.io.IOException;

/**
 * Main class of the Miki personal-task manager project.
 * In honour of Fuzuki Miki.
 */
public class Miki {
    /**
     * Main function of the Miki project.
     *
     * @param args command-line arguments from the initial program call
     */
    public static void main(String[] args) {
        boolean asciiOnly = false;
        boolean noAutoload = false;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--ascii-only")) {
                asciiOnly = true;
            }
            if (args[i].equals("--no-autoload")) {
                noAutoload = true;
            }
        }

        Ui ui = new Ui(asciiOnly);
        Storage storage = new Storage("./data/");
        TaskList tasks = new TaskList();
        ui.printIntro();

        if (!noAutoload) {
            try {
                storage.load("autosave.txt", tasks);
            } catch (IOException | Storage.MikiLoadException ex) {

            }
        }

        String cmdLine = "";
        while (!Parser.parseExit(cmdLine)) {
            cmdLine = ui.readLine();
            Command cmd = Parser.parse(cmdLine);
            ui.printDiv();
            cmd.run(tasks, ui, storage);
            ui.printDiv();

            try {
                storage.save("autosave.txt", tasks);
            } catch (IOException ex) {

            }
        }
    }
}
