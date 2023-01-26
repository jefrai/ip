import java.time.LocalDateTime;

public class Todo extends Task {
    public Todo(String objective) {
        super(objective);
    }

    public static Todo parseArgs(String[] args) throws TaskParseException {
        String objective = "";
        for (int i = 0; i < args.length; i++) {
            objective += (objective.isEmpty() ? "" : " ") + args[i];
        }
        if (objective.isEmpty()) throw new TaskParseException("This todo is missing its body text!");
        return new Todo(objective);
    }

    @Override
    public boolean beforeDate(LocalDateTime date) {
        return false;
    }

    @Override
    public boolean afterDate(LocalDateTime date) {
        return false;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
