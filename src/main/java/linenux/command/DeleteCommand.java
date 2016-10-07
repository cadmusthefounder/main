package linenux.command;

import linenux.command.result.CommandResult;
import linenux.model.Schedule;
import linenux.model.Task;
import linenux.util.TasksListUtil;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Deletes a task from the schedule.
 */
public class DeleteCommand implements Command {
    private static final String DELETE_PATTERN = "(?i)^delete (?<keywords>.*)$";
    private static final String NUMBER_PATTERN = "^\\d+$";
    private static final String CANCEL_PATTERN = "^cancel$";

    private Schedule schedule;
    private boolean requiresUserResponse;
    private ArrayList<Task> foundTasks;

    public DeleteCommand(Schedule schedule) {
        this.schedule = schedule;
        this.requiresUserResponse = false;
    }

    @Override
    public boolean respondTo(String userInput) {
        return userInput.matches(DELETE_PATTERN);
    }

    @Override
    public CommandResult execute(String userInput) {
        Matcher matcher = Pattern.compile(DELETE_PATTERN).matcher(userInput);

        if (matcher.matches()) {
            assert (this.schedule != null);
            
            String keywords = matcher.group("keywords");
            String[] keywordsArr = keywords.split("\\s+");
            ArrayList<Task> tasks = this.schedule.search(keywordsArr);

            if (tasks.size() == 0) {
                return makeNotFoundResult(keywords);
            } else if (tasks.size() == 1) {
                this.schedule.deleteTask(tasks.get(0));
                return makeDeletedTask(tasks.get(0));
            } else {
                this.requiresUserResponse = true;
                this.foundTasks = tasks;
                return makePromptResult(tasks);
            }
        }

        return null;
    }

    @Override
    public boolean awaitingUserResponse() {
        return requiresUserResponse;
    }

    @Override
    public CommandResult userResponse(String userInput) {
        if (userInput.matches(NUMBER_PATTERN)) {
            int index = Integer.parseInt(userInput);

            if (1 <= index && index <= this.foundTasks.size()) {
                Task task = this.foundTasks.get(index - 1);
                this.schedule.deleteTask(task);

                this.requiresUserResponse = false;
                this.foundTasks = null;

                return makeDeletedTask(task);
            } else {
                return makeInvalidIndexResult();
            }
        } else if (userInput.matches(CANCEL_PATTERN)) {
            return makeCancelledResult();
        } else {
            return makeInvalidUserResponse(userInput);
        }
    }

    private CommandResult makeNotFoundResult(String keywords) {
        return () -> "Cannot find \"" + keywords + "\".";
    }

    private CommandResult makeDeletedTask(Task task) {
        return () -> "Deleted \"" + task.getTaskName() + "\".";
    }

    private CommandResult makePromptResult(ArrayList<Task> tasks) {
        return () -> {
            StringBuilder builder = new StringBuilder();
            builder.append("Which one? (1-");
            builder.append(tasks.size());
            builder.append(")\n");

            builder.append(TasksListUtil.display(tasks));

            return builder.toString();
        };
    }

    private CommandResult makeCancelledResult() {
        return () -> "OK! Not deleting anything.";
    }

    private CommandResult makeInvalidUserResponse(String userInput) {
        return () -> {
            StringBuilder builder = new StringBuilder();
            builder.append("I don't understand \"" + userInput + "\".\n");
            builder.append("Enter a number to indicate which task to delete.\n");
            builder.append(TasksListUtil.display(this.foundTasks));
            return builder.toString();
        };
    }

    private CommandResult makeInvalidIndexResult() {
        return () -> {
            StringBuilder builder = new StringBuilder();
            builder.append("That's not a valid index. Enter a number between 1 and ");
            builder.append(this.foundTasks.size());
            builder.append(":\n");
            builder.append(TasksListUtil.display(this.foundTasks));
            return builder.toString();
        };
    }
}