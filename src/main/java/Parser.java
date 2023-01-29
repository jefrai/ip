import command.*;
import exception.MikiArgsException;
import exception.TaskParseException;
import storage.Storage;
import task.Deadline;
import task.Event;
import task.Task;
import task.Todo;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Collections;

public class Parser {
    public static List parseList(String[] args) throws MikiArgsException {
        String from = "";
        String to = "";
        boolean token_from = false;
        boolean token_to = false;
        if (Collections.frequency(Arrays.asList(args), "/from") > 1) throw new MikiArgsException("too many filter-froms...");
        if (Collections.frequency(Arrays.asList(args), "/to") > 1) throw new MikiArgsException("too many filter-tos...");
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("/from")) {
                token_from = true;
                token_to = false;
                continue;
            }
            if (args[i].equals("/to")) {
                token_from = false;
                token_to = true;
                continue;
            }
            if (token_from) {
                from += (from.isEmpty() ? "" : " ") + args[i];
            } else if (token_to) {
                to += (to.isEmpty() ? "" : " ") + args[i];
            }
        }
        LocalDateTime fromDate = null;
        LocalDateTime toDate = null;
        if (!from.isEmpty()) {
            try {
                fromDate = LocalDateTime.parse(from, Task.DATE_IN_FMT);
            } catch (DateTimeParseException ex) {
                throw new MikiArgsException(from + " needs to be formatted as " + Task.DATE_IN_FMT_STR + "!");
            }
        }
        if (!to.isEmpty()) {
            try {
                toDate = LocalDateTime.parse(to, Task.DATE_IN_FMT);
            } catch (DateTimeParseException ex) {
                throw new MikiArgsException(to + " needs to be formatted as " + Task.DATE_IN_FMT_STR + "!");
            }
        }
        return new List(fromDate, toDate);
    }

    public static int parseTaskIndex(String[] args) throws MikiArgsException {
        int idx;
        if (args.length == 0) {
            throw new MikiArgsException("you didn't specify which one?!");
        }
        try {
            idx = Integer.parseInt(args[0]) - 1;
        } catch (NumberFormatException ex) {
            throw new MikiArgsException("\"" + args[0] + "\" isn't a real integer! There's no task #" + args[0] + "!");
        }
        return idx;
    }

    public static String recombine(String[] args) {
        String arg = "";
        for (int i = 0; i < args.length; i++) {
            arg += (i > 0 ? " " : "") + args[i];
        }
        return arg;
    }

    public static boolean parseExit(String cmdLine) {
        return cmdLine.split(" ")[0].toLowerCase().equals("bye");
    }

    public static Command parse(String cmdLine) {
        String cmd = cmdLine.split(" ")[0].toLowerCase();
        String[] args = {};
        if (cmdLine.contains(" ")) {
            args = cmdLine.substring(cmd.length() + 1).split(" ", -1);
        }
        try {
            switch (cmd) {
                case "bye":
                    return new Exit();
                case "list":
                    return parseList(args);
                case "mark":
                    return new Mark(parseTaskIndex(args));
                case "unmark":
                    return new Unmark(parseTaskIndex(args));
                case "todo":
                    return new AddTask(Todo.parseArgs(args));
                case "deadline":
                    return new AddTask(Deadline.parseArgs(args));
                case "event":
                    return new AddTask(Event.parseArgs(args));
                case "delete":
                    return new Delete(parseTaskIndex(args));
                case "save":
                    return new Save(recombine(args));
                case "load":
                    return new Load(recombine(args));
                case "find":
                    return new Find(".*" + recombine(args) + ".*");
                default:
                    throw new MikiArgsException("\"" + cmd + "\" isn't a real word!");
            }
        } catch (TaskParseException | MikiArgsException ex) {
            return new ExceptionPrint(ex);
        }
    }
}