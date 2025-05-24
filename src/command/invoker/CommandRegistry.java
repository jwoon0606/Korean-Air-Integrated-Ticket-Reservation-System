package command.invoker;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack; // 추가

import command.command.Command;
import command.undo_command.UndoableCommand; // 추가

/**
 * Command Pattern의 Invoker 역할을 수행하는 클래스
 * 명령 객체들을 관리하고 실행하는 책임을 가짐
 * Receiver와의 의존성을 제거하여 확장성과 유연성을 높임
 */
public class CommandRegistry {
    private Map<Integer, Command> commands;
    private Stack<Command> historyStack = new Stack<>(); // 추가: 실행된 커맨드 기록
    
    /**
     * CommandRegistry 기본 생성자 - Invoker 초기화
     */
    public CommandRegistry() {
        this.commands = new HashMap<>();
    }
    
    /**
     * 명령 객체를 레지스트리에 등록
     * 
     * @param menuId 메뉴 ID
     * @param command 등록할 명령 객체
     */
    public void setCommand(int menuId, Command command) {
        commands.put(menuId, command);
    }
    
    /**
     * Invoker의 핵심 메서드: 메뉴 선택에 따라 적절한 명령을 실행
     * 
     * @param menuChoice 사용자가 선택한 메뉴 번호
     * @param isLoggedIn 현재 로그인 상태
     * @return 명령 실행 성공 여부
     */
    public boolean executeCommand(int menuChoice, boolean isLoggedIn) {
        if (!hasCommand(menuChoice)) {
            System.out.println("Invalid menu selection. Please try again.");
            return false;
        }
        
        Command selectedCommand = commands.get(menuChoice);
        if (selectedCommand.canExecute(isLoggedIn)) {
            selectedCommand.execute();
            historyStack.push(selectedCommand); // 추가: 실행된 커맨드를 히스토리에 추가
            return true;
        }
        return false;
    }

    public void undoCommand(){
        if (historyStack.isEmpty()) {
            System.out.println("Nothing to undo.");
            return;
        }

        Command lastCommand = historyStack.pop();
        if (lastCommand instanceof UndoableCommand) {
            ((UndoableCommand) lastCommand).undo();
            System.out.println("Command undone.");
        } else {
            System.out.println("This command cannot be undone.");
            // Optionally, push the command back if it's not undoable but you want to keep the historyStack intact for other purposes
            // historyStack.push(lastCommand); 
        }
    }
    
    /**
     * 명령 객체가 등록되어 있는지 확인
     * 
     * @param menuId 확인할 메뉴 ID
     * @return 등록 여부
     */
    private boolean hasCommand(int menuId) {
        return commands.containsKey(menuId);
    }
    
    /**
     * 로그인 상태에 따른 메뉴 표시
     * 
     * @param isLoggedIn 로그인 상태
     */
    public void displayMenu(boolean isLoggedIn) {
        System.out.println("\nmenu");
        
        // 로그인 상태에 따라 다른 메뉴 표시
        Integer[] validMenuIds = getValidMenuIds(isLoggedIn);
        
        for (Integer menuId : validMenuIds) {
            if (menuId != 0) { // 종료 메뉴는 마지막에 표시
                Command command = commands.get(menuId);
                if (command != null) {
                    System.out.println(command.getMenuText());
                }
            }
        }
        
        // 항상 표시되는 종료 메뉴
        Command exitCommand = commands.get(0);
        if (exitCommand != null) {
            System.out.println(exitCommand.getMenuText());
        }
    }
    
    /**
     * 로그인 상태에 따른 유효한 메뉴 ID 목록 반환
     * 
     * @param isLoggedIn 로그인 상태
     * @return 유효한 메뉴 ID 배열
     */
    private Integer[] getValidMenuIds(boolean isLoggedIn) {
        if (isLoggedIn) {
            return new Integer[]{0, 5, 6, 7, 8}; // 로그인한 상태의 메뉴
        } else {
            return new Integer[]{0, 1, 2, 3, 4, 7, 8}; // 로그인하지 않은 상태의 메뉴
        }
    }
}