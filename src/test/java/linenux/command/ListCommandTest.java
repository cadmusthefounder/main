package linenux.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import linenux.command.result.CommandResult;
import linenux.model.Reminder;
import linenux.model.Schedule;
import linenux.model.Task;

//@@author A0140702X
/**
 * JUnit test for list command.
 */
public class ListCommandTest {
    private Schedule schedule;
    private ListCommand listCommand;

    @Before
    public void setupListCommand() {
        this.schedule = new Schedule();
        this.listCommand = new ListCommand(this.schedule);
    }

    /**
     * Test list all.
     */
    @Test
    public void respondTo_noArgument_trueReturned() {
        assertTrue(this.listCommand.respondTo("list"));
    }

    /**
     * Test search function in list.
     */
    @Test
    public void respondTo_withArgument_trueReturned() {
        assertTrue(this.listCommand.respondTo("list bla"));
    }

    /**
     * Test that list command is case insenstive.
     */
    @Test
    public void respondTo_upperCase_trueReturned() {
        assertTrue(this.listCommand.respondTo("LiSt"));
    }

    /**
     * Test that list command response to invalid datetime.
     */
    @Test
    public void execute_invalidTime_commandResultReturned() {
        this.schedule.addTask(new Task("todo"));
        CommandResult result = this.listCommand.execute("list st/not time");
        String expectedResult = "Cannot parse \"not time\".";

        assertEquals(expectedResult, result.getFeedback());
    }

    /**
     * Test that list command response to end time before start time.
     */
    @Test
    public void execute_endTimeBeforeStartTime_commandResultReturned() {
        this.schedule.addTask(new Task("todo"));
        CommandResult result = this.listCommand.execute("list st/2016-01-01 5.00PM et/2015-01-01 5.00PM");
        String expectedResult = "End time cannot come before start time.";

        assertEquals(expectedResult, result.getFeedback());
    }

    /**
     * Test that list command does not respond to other commands.
     */
    @Test
    public void respondTo_otherCommand_falseReturned() {
        assertFalse(this.listCommand.respondTo("whaddup"));
    }

    /**
     * Test that list without params should display all undone tasks and
     * reminders
     */
    @Test
    public void execute_noArgument_allTasksReturned() {
        this.schedule.addTask(new Task("First Task"));
        this.schedule.addTask(new Task("Second Task"));
        this.schedule.addTask(new Task("Deadline", null, LocalDateTime.of(2016, 1, 1, 17, 0)));
        this.schedule.addTask(new Task("Event", LocalDateTime.of(2016, 1, 1, 17, 0), LocalDateTime.of(2016, 1, 1, 18, 0)));

        Task taskWithReminder = new Task("Task with Reminder");
        taskWithReminder = taskWithReminder.addReminder(new Reminder("Reminder", LocalDateTime.of(2016, 2, 1, 17, 0)));
        this.schedule.addTask(taskWithReminder);

        CommandResult result = this.listCommand.execute("list");

        String expectedFeedback = "Reminders:\n"
                + "1. Reminder (On 2016-02-01 5.00PM)";
        assertEquals(expectedFeedback, result.getFeedback());
    }

    /**
     * Test that list command displays multiple tasks correctly.
     */
    @Test
    public void execute_withKeywords_tasksMatchingTheKeywordsReturned() {
        Task task1 = new Task("hello");
        Task task2 = new Task("world");
        Task task3 = new Task("hello world");
        this.schedule.addTask(task1);
        this.schedule.addTask(task2);
        this.schedule.addTask(task3);

        this.listCommand.execute("list world");
        assertTrue(
                this.schedule.getFilteredTasks().contains(task2) && this.schedule.getFilteredTasks().contains(task3));
    }

    @Test
    public void execute_noMatch_commandResultReturn() {
        this.schedule.addTask(new Task("hi!"));

        CommandResult result = this.listCommand.execute("list hello");

        String expectedFeedback = "There are no tasks and reminders found based on your given inputs!";
        assertEquals(expectedFeedback, result.getFeedback());
    }

    /**
     * Test that list command displays multiple reminders correctly.
     */
    @Test
    public void execute_keywords_remindersMatchingTheKeywordsReturned() {
        Task hello = new Task("hello");
        hello = hello.addReminder(new Reminder("world domination", LocalDateTime.of(2016, 2, 1, 17, 0)));
        hello = hello.addReminder(new Reminder("is my occupation", LocalDateTime.of(2016, 1, 1, 17, 0)));
        hello = hello.addReminder(new Reminder("hello world", LocalDateTime.of(2016, 3, 1, 17, 0)));
        this.schedule.addTask(hello);

        CommandResult result = this.listCommand.execute("list world");

        ArrayList<Task> filteredTasks = this.schedule.getFilteredTasks();
        assertTrue(!filteredTasks.contains(hello));

        String expectedFeedback = "Reminders:\n1. world domination (On 2016-02-01 5.00PM)\n2. hello world (On 2016-03-01 5.00PM)";
        assertEquals(expectedFeedback, result.getFeedback());
    }

    /**
     * Test that list command displays multiple reminders and tasks correctly.
     */
    @Test
    public void execute_keywords_tasksAndRemindersMatchingTheKeywordsReturned() {
        Task hello = new Task("hello");
        hello = hello.addReminder(new Reminder("world domination", LocalDateTime.of(2016, 2, 1, 17, 0)));
        hello = hello.addReminder(new Reminder("is my occupation", LocalDateTime.of(2016, 1, 1, 17, 0)));
        hello = hello.addReminder(new Reminder("hello world", LocalDateTime.of(2016, 3, 1, 17, 0)));
        hello = hello.addReminder(new Reminder("hello darkness", LocalDateTime.of(2016, 4, 1, 17, 0)));
        this.schedule.addTask(hello);

        Task helloWorld = new Task("Hello World");
        helloWorld = helloWorld.addReminder(new Reminder("hello hello", LocalDateTime.of(2016, 1, 1, 17, 0)));
        this.schedule.addTask(helloWorld);

        CommandResult result = this.listCommand.execute("list hello");

        String expectedFeedback = "Reminders:\n" + "1. hello hello (On 2016-01-01 5.00PM)\n"
                + "2. hello world (On 2016-03-01 5.00PM)\n" + "3. hello darkness (On 2016-04-01 5.00PM)";
        assertEquals(expectedFeedback, result.getFeedback());
    }

    /**
     * Test that list command filters by start time
     */
    @Test
    public void execute_startTime_tasksAndRemindersReturned() {
        Task todo = new Task("todo");
        todo = todo.addReminder(new Reminder("todo before", LocalDateTime.of(2015, 1, 1, 17, 0)));
        todo = todo.addReminder(new Reminder("todo after", LocalDateTime.of(2017, 1, 1, 17, 0)));
        todo = todo.addReminder(new Reminder("todo on", LocalDateTime.of(2016, 1, 1, 17, 0)));

        Task eventBefore = new Task("event before", LocalDateTime.of(2015, 1, 1, 17, 0),
                LocalDateTime.of(2015, 1, 1, 19, 0));
        Task eventOn = new Task("event on", LocalDateTime.of(2015, 1, 1, 17, 0), LocalDateTime.of(2016, 1, 1, 17, 0));
        Task eventAfter = new Task("event after", LocalDateTime.of(2015, 1, 1, 17, 0),
                LocalDateTime.of(2017, 1, 1, 17, 0));

        Task deadlineBefore = new Task("deadline before", LocalDateTime.of(2015, 1, 1, 17, 0));
        Task deadlineOn = new Task("deadline On", LocalDateTime.of(2016, 1, 1, 17, 0));
        Task deadlineAfter = new Task("deadline before", LocalDateTime.of(2017, 1, 1, 17, 0));

        this.schedule.addTask(todo);
        this.schedule.addTask(eventBefore);
        this.schedule.addTask(eventOn);
        this.schedule.addTask(eventAfter);
        this.schedule.addTask(deadlineBefore);
        this.schedule.addTask(deadlineOn);
        this.schedule.addTask(deadlineAfter);

        CommandResult result = this.listCommand.execute("list st/2016-01-01 5.00PM");

        ArrayList<Task> filteredTasks = this.schedule.getFilteredTasks();

        assertTrue(filteredTasks.contains(todo));
        assertTrue(!filteredTasks.contains(eventBefore));
        assertTrue(filteredTasks.contains(eventOn));
        assertTrue(filteredTasks.contains(eventAfter));
        assertTrue(!filteredTasks.contains(deadlineBefore));
        assertTrue(filteredTasks.contains(deadlineOn));
        assertTrue(filteredTasks.contains(deadlineAfter));

        String expectedFeedback = "Reminders:\n" + "1. todo on (On 2016-01-01 5.00PM)\n"
                + "2. todo after (On 2017-01-01 5.00PM)";
        assertEquals(expectedFeedback, result.getFeedback());
    }

    /**
     * Test that list command filters by end time
     */
    @Test
    public void execute_endTime_tasksAndRemindersReturned() {
        Task todo = new Task("todo");
        todo = todo.addReminder(new Reminder("todo before", LocalDateTime.of(2015, 1, 1, 17, 0)));
        todo = todo.addReminder(new Reminder("todo after", LocalDateTime.of(2017, 1, 1, 17, 0)));
        todo = todo.addReminder(new Reminder("todo on", LocalDateTime.of(2016, 1, 1, 17, 0)));

        Task eventBefore = new Task("event before", LocalDateTime.of(2015, 1, 1, 17, 0),
                LocalDateTime.of(2015, 1, 1, 19, 0));
        Task eventOn = new Task("event on", LocalDateTime.of(2015, 1, 1, 17, 0), LocalDateTime.of(2016, 1, 1, 17, 0));
        Task eventEndTimeAfter = new Task("event after", LocalDateTime.of(2015, 1, 1, 19, 0),
                LocalDateTime.of(2017, 1, 1, 17, 0));
        Task eventStartTimeAfter = new Task("event after", LocalDateTime.of(2017, 1, 1, 19, 0),
                LocalDateTime.of(2018, 1, 1, 17, 0));

        Task deadlineBefore = new Task("deadline before", LocalDateTime.of(2015, 1, 1, 17, 0));
        Task deadlineOn = new Task("deadline On", LocalDateTime.of(2016, 1, 1, 17, 0));
        Task deadlineAfter = new Task("deadline after", LocalDateTime.of(2017, 1, 1, 17, 0));

        this.schedule.addTask(todo);
        this.schedule.addTask(eventBefore);
        this.schedule.addTask(eventOn);
        this.schedule.addTask(eventEndTimeAfter);
        this.schedule.addTask(eventStartTimeAfter);
        this.schedule.addTask(deadlineBefore);
        this.schedule.addTask(deadlineOn);
        this.schedule.addTask(deadlineAfter);

        CommandResult result = this.listCommand.execute("list et/2016-01-01 5.00PM");

        ArrayList<Task> filteredTasks = this.schedule.getFilteredTasks();

        assertTrue(filteredTasks.contains(todo));
        assertTrue(filteredTasks.contains(eventBefore));
        assertTrue(filteredTasks.contains(eventOn));
        assertTrue(filteredTasks.contains(eventEndTimeAfter));
        assertTrue(!filteredTasks.contains(eventStartTimeAfter));
        assertTrue(filteredTasks.contains(deadlineBefore));
        assertTrue(filteredTasks.contains(deadlineOn));
        assertTrue(!filteredTasks.contains(deadlineAfter));

        String expectedFeedback = "Reminders:\n" + "1. todo before (On 2015-01-01 5.00PM)\n"
                + "2. todo on (On 2016-01-01 5.00PM)";
        assertEquals(expectedFeedback, result.getFeedback());
    }

    /**
     * Test that list command filters by start and end time
     */
    @Test
    public void execute_startTimeAndEndTime_tasksAndRemindersReturned() {
        Task todo = new Task("todo");
        todo = todo.addReminder(new Reminder("todo before", LocalDateTime.of(2015, 1, 1, 17, 0)));
        todo = todo.addReminder(new Reminder("todo after", LocalDateTime.of(2017, 1, 1, 17, 0)));
        todo = todo.addReminder(new Reminder("todo during", LocalDateTime.of(2016, 6, 1, 17, 0)));

        Task eventEndTimeBefore = new Task("event end time before", LocalDateTime.of(2015, 1, 1, 17, 0),
                LocalDateTime.of(2015, 1, 1, 19, 0));
        Task eventDuring1 = new Task("event during1", LocalDateTime.of(2015, 1, 1, 17, 0),
                LocalDateTime.of(2016, 2, 1, 17, 0));
        Task eventDuring2 = new Task("event during2", LocalDateTime.of(2016, 1, 2, 19, 0),
                LocalDateTime.of(2016, 1, 3, 17, 0));
        Task eventDuring3 = new Task("event during3", LocalDateTime.of(2016, 1, 2, 19, 0),
                LocalDateTime.of(2017, 1, 3, 17, 0));
        Task eventDuring4 = new Task("event during4", LocalDateTime.of(2014, 1, 1, 17, 0),
                LocalDateTime.of(2017, 1, 1, 17, 0));
        Task eventStartTimeAfter = new Task("event start time after", LocalDateTime.of(2017, 1, 1, 19, 0),
                LocalDateTime.of(2018, 1, 1, 17, 0));

        Task deadlineBefore = new Task("deadline before", LocalDateTime.of(2015, 1, 1, 17, 0));
        Task deadlineDuring = new Task("deadline during", LocalDateTime.of(2016, 6, 1, 17, 0));
        Task deadlineAfter = new Task("deadline after", LocalDateTime.of(2017, 1, 1, 17, 0));

        this.schedule.addTask(todo);
        this.schedule.addTask(eventEndTimeBefore);
        this.schedule.addTask(eventDuring1);
        this.schedule.addTask(eventDuring2);
        this.schedule.addTask(eventDuring3);
        this.schedule.addTask(eventDuring4);
        this.schedule.addTask(eventStartTimeAfter);
        this.schedule.addTask(deadlineBefore);
        this.schedule.addTask(deadlineDuring);
        this.schedule.addTask(deadlineAfter);

        CommandResult result = this.listCommand.execute("list st/2016-01-01 5.00PM et/2016-12-31 5.00PM");

        ArrayList<Task> filteredTasks = this.schedule.getFilteredTasks();

        assertTrue(filteredTasks.contains(todo));
        assertTrue(!filteredTasks.contains(eventEndTimeBefore));
        assertTrue(filteredTasks.contains(eventDuring1));
        assertTrue(filteredTasks.contains(eventDuring2));
        assertTrue(filteredTasks.contains(eventDuring3));
        assertTrue(filteredTasks.contains(eventDuring4));
        assertTrue(!filteredTasks.contains(eventStartTimeAfter));
        assertTrue(!filteredTasks.contains(deadlineBefore));
        assertTrue(filteredTasks.contains(deadlineDuring));
        assertTrue(!filteredTasks.contains(deadlineAfter));

        String expectedFeedback = "Reminders:\n" + "1. todo during (On 2016-06-01 5.00PM)";
        assertEquals(expectedFeedback, result.getFeedback());
    }

    /**
     * Test that list command filters task by tags
     */
    @Test
    public void execute_tags_tasksReturned() {
        ArrayList<String> tags1 = new ArrayList<>();
        ArrayList<String> tags2 = new ArrayList<>();
        ArrayList<String> tags3 = new ArrayList<>();

        tags1.add("hello");
        tags2.add("hello");
        tags2.add("world");
        tags3.add("wat");

        Task todo1 = new Task("todo 1", tags1);
        Task todo2 = new Task("todo 2", tags2);
        Task todo3 = new Task("todo 3", tags3);

        this.schedule.addTask(todo1);
        this.schedule.addTask(todo2);
        this.schedule.addTask(todo3);

        this.listCommand.execute("list #/hello");

        ArrayList<Task> filteredTasks = this.schedule.getFilteredTasks();

        assertTrue(filteredTasks.contains(todo1));
        assertTrue(filteredTasks.contains(todo2));
        assertTrue(!filteredTasks.contains(todo3));
    }

    /**
     * Test that list command filters by tags does not show reminders
     */
    @Test
    public void execute_tags_remindersNotReturned() {
        ArrayList<String> tags1 = new ArrayList<>();
        ArrayList<String> tags2 = new ArrayList<>();
        ArrayList<String> tags3 = new ArrayList<>();

        tags1.add("hello");
        tags2.add("hello");
        tags2.add("world");
        tags3.add("wat");

        Task todo1 = new Task("todo 1", tags1);
        Task todo2 = new Task("todo 2", tags2);
        Task todo3 = new Task("todo 3", tags3);

        todo1.addReminder(new Reminder("reminder", LocalDateTime.of(2016, 1, 1, 17, 0)));

        this.schedule.addTask(todo1);
        this.schedule.addTask(todo2);
        this.schedule.addTask(todo3);

        CommandResult result = this.listCommand.execute("list #/hello");

        ArrayList<Task> filteredTasks = this.schedule.getFilteredTasks();

        assertTrue(filteredTasks.contains(todo1));
        assertTrue(filteredTasks.contains(todo2));
        assertTrue(!filteredTasks.contains(todo3));

        assertEquals("", result.getFeedback());
    }

    /**
     * Test that list command filters by tags is case-insensitive
     */
    @Test
    public void execute_tags_caseInsensitive() {
        ArrayList<String> tags1 = new ArrayList<>();
        ArrayList<String> tags2 = new ArrayList<>();
        ArrayList<String> tags3 = new ArrayList<>();

        tags1.add("hello");
        tags2.add("hello");
        tags2.add("world");
        tags3.add("wat");

        Task todo1 = new Task("todo 1", tags1);
        Task todo2 = new Task("todo 2", tags2);
        Task todo3 = new Task("todo 3", tags3);

        this.schedule.addTask(todo1);
        this.schedule.addTask(todo2);
        this.schedule.addTask(todo3);

        this.listCommand.execute("list #/hElLo");

        ArrayList<Task> filteredTasks = this.schedule.getFilteredTasks();

        assertTrue(filteredTasks.contains(todo1));
        assertTrue(filteredTasks.contains(todo2));
        assertTrue(!filteredTasks.contains(todo3));
    }

    /**
     * Test that default list command does not show done task.
     */
    @Test
    public void execute_noDoneFlag_doneTasksAreNotShown() {
        Task todo1 = new Task("todo 1");
        Task todo2 = new Task("todo 2");

        todo1 = todo1.markAsDone();

        this.schedule.addTask(todo1);
        this.schedule.addTask(todo2);

        this.listCommand.execute("list");

        ArrayList<Task> filteredTasks = this.schedule.getFilteredTasks();

        assertTrue(!filteredTasks.contains(todo1));
        assertTrue(filteredTasks.contains(todo2));
    }

    /**
     * Test that list command field d/yes (view done only)
     */
    @Test
    public void execute_doneFlagYes_doneTasksReturned() {
        Task todo1 = new Task("todo 1");
        Task todo2 = new Task("todo 2");

        todo1 = todo1.markAsDone();

        this.schedule.addTask(todo1);
        this.schedule.addTask(todo2);

        this.listCommand.execute("list d/yes");

        ArrayList<Task> filteredTasks = this.schedule.getFilteredTasks();

        assertTrue(filteredTasks.contains(todo1));
        assertTrue(!filteredTasks.contains(todo2));
    }

    /**
     * Test that list command field d/all (view all including done)
     */
    @Test
    public void execute_doneFlagAll_allTasksReturned() {
        Task todo1 = new Task("todo 1");
        Task todo2 = new Task("todo 2");

        todo1 = todo1.markAsDone();

        this.schedule.addTask(todo1);
        this.schedule.addTask(todo2);

        this.listCommand.execute("list d/all");

        ArrayList<Task> filteredTasks = this.schedule.getFilteredTasks();

        assertTrue(filteredTasks.contains(todo1));
        assertTrue(filteredTasks.contains(todo2));
    }

    /**
     * Test that list command field d/ when invalid
     */
    @Test
    public void execute_doneFlagInvalid_commandResultReturn() {
        Task todo = new Task("todo");

        this.schedule.addTask(todo);

        CommandResult result = this.listCommand.execute("list d/invalid");

        String expectedFeedback = "Unable to parse \"invalid\".\n" + "Did you mean:\n"
                + "d/all - View all done and uncompleted tasks.\n" + "d/yes - Show only tasks that are marked done.";
        assertEquals(expectedFeedback, result.getFeedback());
    }
}
