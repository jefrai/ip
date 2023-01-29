package command;

import shigure.Ui;
import storage.Storage;
import task.TaskList;

import java.time.LocalDateTime;

/**
 * A command printing the contents of a <code>TaskList</code>, optionally filtering by a time window.
 */
public class ListTasks implements Command {
    private LocalDateTime from = null;
    private LocalDateTime to = null;

    /**
     * Creates a list-tasks command.
     */
    public ListTasks() {

    }

    /**
     * Creates a list-tasks command with time window filtering.
     *
     * @param from start of the time window. May be left null for no window start filtering
     * @param to end of the time window. May be left null for no window end filtering
     */
    public ListTasks(LocalDateTime from, LocalDateTime to) {
        this.from = from;
        this.to = to;
    }

    /**
     * {@inheritDoc}
     *
     * @param tasks tasklist to perform the action on
     * @param ui ui to perform the action on
     * @param storage storage to perform the action on
     */
    @Override
    public void run(TaskList tasks, Ui ui, Storage storage) {
        ui.print("caught in 4k:");
        for (int i = 0; i < tasks.size(); i++) {
            if ((from == null || tasks.get(i).afterDate(from))
                    && (to == null || tasks.get(i).beforeDate(to))) {
                ui.print(Integer.toString(i + 1) + ". " + tasks.get(i));
            }
        }
    }
}
