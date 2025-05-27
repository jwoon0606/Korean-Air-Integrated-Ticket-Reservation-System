package command.command;

public interface Command {
    void execute();
    boolean canExecute(boolean isLoggedIn);
    String getMenuText();
}