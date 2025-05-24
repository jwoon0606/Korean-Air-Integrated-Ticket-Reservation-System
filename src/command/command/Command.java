package command.command;

/**
 * 명령 패턴의 기본 인터페이스
 * 모든 메뉴 명령은 이 인터페이스를 구현합니다.
 */
public interface Command {
    /**
     * 명령을 실행합니다.
     */
    void execute();
    
    /**
     * 명령이 현재 상태에서 실행 가능한지 확인합니다.
     * 
     * @param isLoggedIn 현재 로그인 상태
     * @return 실행 가능 여부
     */
    boolean canExecute(boolean isLoggedIn);
    
    /**
     * 명령에 대한 메뉴 텍스트를 반환합니다.
     * 
     * @return 메뉴 텍스트
     */
    String getMenuText();
}