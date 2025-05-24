package command.undo_command;

import command.command.Command;

// undo를 위한 틀을 마련한다. 
public interface UndoableCommand extends Command {
    public void undo();
}
