package command;

/**
 * 프로그램 종료를 위한 명령 구현체
 */
public class ExitCommand implements Command {
    private boolean[] runningRef;
    
    public ExitCommand(boolean[] runningRef) {
        this.runningRef = runningRef;
    }
    
    @Override
    public void execute() {
        System.out.println("프로그램을 종료합니다.");
        runningRef[0] = false; // 참조를 통해 running 값을 변경
    }
    
    @Override
    public boolean canExecute(boolean isLoggedIn) {
        // 항상 실행 가능
        return true;
    }
    
    @Override
    public String getMenuText() {
        return "0. End Program";
    }
}