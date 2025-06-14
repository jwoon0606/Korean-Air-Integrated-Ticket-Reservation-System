package command.concrete_command;
import command.undo_command.UndoableCommand;

/**
 * 프로그램 종료를 위한 명령 구현체
 */
public class ExitCommand implements UndoableCommand {
    private boolean[] programRunningState;
    
    public ExitCommand(boolean[] programRunningState) {
        this.programRunningState = programRunningState;
    }
    
    @Override
    public void execute() {
        System.out.println();
        programRunningState[0] = false;
    }
    
    @Override
    public boolean canExecute(boolean isLoggedIn) {
        return true;
    }
    
    @Override
    public String getMenuText() {
        return "0. Exit";
    }

    @Override
    public void undo() {
        System.out.println("does not support undo.");
    }
}