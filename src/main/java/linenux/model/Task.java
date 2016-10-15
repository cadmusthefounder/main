package linenux.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import linenux.util.ArrayListUtil;

/**
 * Represents a task in the schedule. Only taskName is a required field and
 * cannot be an empty string.
 */
public class Task {
    private String taskName;
    private boolean isDone;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private ArrayList<String> categories;
    private ArrayList<Reminder> reminders;

    /**
     * Constructor for uncategorized To-Dos (tasks with no deadlines or
     * predetermined time slots).
     *
     * @param taskName
     */
    public Task(String taskName) {
        this(taskName, new ArrayList<String>());
    }

    /**
     * Constructor for uncategorized Deadlines (tasks with deadlines only).
     *
     * @param taskName
     * @param endTime
     */
    public Task(String taskName, LocalDateTime endTime) {
        this(taskName, endTime, new ArrayList<String>());
    }

    /**
     * Constructor for uncategorized Events (tasks with predetermined time
     * slots).
     *
     * @param taskName
     * @param startTime
     * @param endTime
     */
    public Task(String taskName, LocalDateTime startTime, LocalDateTime endTime) {
        this(taskName, startTime, endTime, new ArrayList<String>());
    }

    /**
     * Constructor for To-Dos (tasks with no deadlines or predetermined time
     * slots).
     */
    public Task(String taskName, ArrayList<String> categories) {
        this.taskName = taskName;
        this.isDone = false;
        this.startTime = null;
        this.endTime = null;
        this.categories = categories;
        this.reminders = new ArrayList<Reminder>();
    }

    /**
     * Constructor for Deadlines (tasks with deadlines only).
     */
    public Task(String taskName, LocalDateTime endTime, ArrayList<String> categories) {
        this.taskName = taskName;
        this.isDone = false;
        this.startTime = null;
        this.endTime = endTime;
        this.categories = categories;
        this.reminders = new ArrayList<Reminder>();
    }

    /**
     * Constructor for Events (tasks with predetermined time slots).
     */
    public Task(String taskName, LocalDateTime startTime, LocalDateTime endTime, ArrayList<String> categories) {
        this.taskName = taskName;
        this.isDone = false;
        this.startTime = startTime;
        this.endTime = endTime;
        this.categories = categories;
        this.reminders = new ArrayList<Reminder>();
    }

    public Task copyTask() {
        Task copyTask = new Task(taskName, startTime, endTime, categories);
        copyTask.setIsDone(isDone);
        copyTask.setCategories(new ArrayListUtil.ChainableArrayListUtil<String>(categories)
                .map(category -> category.toString()).value());
        System.out.println(copyTask.categoriesToString());
        copyTask.setReminders(new ArrayListUtil.ChainableArrayListUtil<Reminder>(reminders)
                .map(reminder -> reminder.copyReminder()).value());
        return copyTask;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd h:mma");

        if (this.isDeadline()) {
            return taskName + " (Due " + this.endTime.format(formatter) + ")" + categoriesToString();
        } else if (this.isEvent()) {
            return taskName + " (" + this.startTime.format(formatter) + " - " + this.endTime.format(formatter) + ")"
                    + categoriesToString();
        } else {
            return taskName + categoriesToString();
        }
    }

    public boolean isTodo() {
        return startTime == null && endTime == null;
    }

    public boolean isDeadline() {
        return startTime == null && endTime != null;
    }

    public boolean isEvent() {
        return startTime != null && endTime != null;
    }

    public boolean isDone() {
        return isDone == true;
    }

    public boolean isNotDone() {
        return isDone == false;
    }

    /* Getters */

    public String getTaskName() {
        return this.taskName;
    }

    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    public LocalDateTime getEndTime() {
        return this.endTime;
    }

    public ArrayList<String> getCategories() {
        return this.categories;
    }

    public ArrayList<Reminder> getReminders() {
        return this.reminders;
    }

    /* Setters */

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public void addReminder(Reminder reminder) {
        this.reminders.add(reminder);
    }

    public void setReminders(ArrayList<Reminder> reminders) {
        this.reminders = reminders;
    }

    private String categoriesToString() {
        StringBuilder builder = new StringBuilder();
        if (this.categories.isEmpty()) {
            return "";
        }

        builder.append(" [");
        builder.append("Categories:");
        for (String s : this.categories) {
            builder.append(" \"");
            builder.append(s);
            builder.append("\"");
        }
        builder.append(" ]");

        return builder.toString();
    }
}
