package command.undo_command;

// ExecuteResult 임포트 제거

/**
 * Undo 기능을 지원하는 명령 인터페이스
 */
public interface UndoableCommand {
    void execute(); // 반환 타입을 void로 변경
    void undo();
    boolean canExecute(boolean isLoggedIn);
    String getMenuText();
}
