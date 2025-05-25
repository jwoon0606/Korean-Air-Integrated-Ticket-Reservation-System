package command.undo_command;

import command.command.Command;

// undo 기능을 지원하는 명령 인터페이스
// Command 인터페이스를 확장하여 undo 메서드를 추가합니다.
public interface UndoableCommand extends Command{

    public void undo();
}
