package linenux.command;

import linenux.command.result.CommandResult;
import linenux.model.Schedule;

/**
 * Undo the previous command that mutated the state of the schedule.
 */
public class UndoCommand extends AbstractCommand {
    private static final String TRIGGER_WORD = "undo";
    private static final String DESCRIPTION = "Undo the previous command.";
    private static final String COMMAND_FORMAT = "undo";

    private Schedule schedule;

    public UndoCommand(Schedule schedule) {
        this.schedule = schedule;
        this.TRIGGER_WORDS.add(TRIGGER_WORD);
    }

    @Override
    public boolean respondTo(String userInput) {
        return userInput.matches(getPattern());
    }

    @Override
    public CommandResult execute(String userInput) {
        assert userInput.matches(getPattern());
        assert this.schedule != null;

        if (this.schedule.popState()) {
            return makeUndoSuccessfulMessage();
        } else {
            return makeUndoUnsuccessfulMessage();
        }
    }

    @Override
    public String getTriggerWord() {
        return TRIGGER_WORD;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public String getCommandFormat() {
        return COMMAND_FORMAT;
    }

    private CommandResult makeUndoSuccessfulMessage() {
        return () -> "Successfully undo last command.";
    }

    private CommandResult makeUndoUnsuccessfulMessage() {
        return () -> "No more commands to undo!";
    }

}
