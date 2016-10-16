package linenux.model;

import static junit.framework.TestCase.assertEquals;
import static linenux.helpers.Assert.assertNoChange;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test for schedule.
 */
public class ScheduleTest {
    private Schedule schedule;

    @Before
    public void setupSchedule() {
        this.schedule = new Schedule();
    }

    @Test
    public void testAddTask() {
        int beforeSize = this.schedule.getTaskList().size();
        this.schedule.addTask(new Task("bla"));
        int afterSize = this.schedule.getTaskList().size();

        assertEquals(beforeSize + 1, afterSize);
    }

    @Test
    public void testSearch() {
        String[] keywords = {"hello", "WoRlD"};
        Task match1 = new Task("Say Hello");
        Task match2 = new Task("Around the world");
        Task mismatch = new Task("meh");

        this.schedule.addTask(match1);
        this.schedule.addTask(mismatch);
        this.schedule.addTask(match2);

        ArrayList<Task> tasks = this.schedule.search(keywords);

        assertEquals(2, tasks.size());
    }

    @Test
    public void testDelete() {
        Task task = new Task("bla");
        this.schedule.addTask(task);
        int beforeSize = this.schedule.getTaskList().size();
        this.schedule.deleteTask(task);
        int afterSize = this.schedule.getTaskList().size();

        assertEquals(beforeSize - 1, afterSize);
        assertTrue(this.schedule.getTaskList().indexOf(task) == -1);
    }

    @Test
    public void testMaxStates() {
        for (int i = 0; i < Schedule.MAX_STATES; i++) {
            this.schedule.addTask(new Task("task" + Integer.toString(i)));
        }
        assertEquals(Schedule.MAX_STATES, this.schedule.getStates().size());
        assertNoChange(() -> this.schedule.getStates().size(), () -> { this.schedule.addTask(new Task("Hi")); return 0; });
    }
}
