package command.invoker;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import command.undo_command.UndoableCommand;


public class CommandRegistry {
    // command들을 저장하는 Map입니다.
    private Map<Integer, UndoableCommand> commands;

    private Stack<UndoableCommand> historyStack = new Stack<>();
    

    public CommandRegistry() {
        // 명령어 등록
        this.commands = new HashMap<>();

    }

    // 여기서 concrete command를 등록하고 사용할 준비를 합니다.
   public void setCommand(int menuId, UndoableCommand command) {
        commands.put(menuId, command);
    }   
    
    // 사용자가 선택한 행동에 따라 어떤 command로 실행할 지를 결정하는 메소드입니다.
    public boolean executeCommand(int menuChoice, boolean isLoggedIn) {

        
        UndoableCommand selectedCommand = commands.get(menuChoice);

        if (selectedCommand.canExecute(isLoggedIn)) {
            selectedCommand.execute();

            historyStack.push(selectedCommand); 
            printHistoryStack();
            return true; 
        }
 
        System.out.println("You cannot perform that action right now.");
        return false; // 행동이 실행되지 않았어요.
    }

    //undo 기능을 구현하는 메소드입니다.
    public void undoCommand(){
        // 되돌릴 행동이 우리 기록에 있는지 확인해요.
        if (historyStack.isEmpty()) {
            System.out.println("There are no actions to undo.");
            printHistoryStack(); // 스택 현황 출력 추가 (비어있을 때도)
            return; // 할 일이 없네요.
        }

        // 마지막으로 수행했던 action을 pop합니다.
        UndoableCommand commandToUndo = historyStack.pop();
        // 마지막으로 수행했던 class를 pop해서 가져온 후 undo를 실행합니다.
        commandToUndo.undo();
        System.out.println("undo action complete: " + commandToUndo.getClass().getSimpleName());
        printHistoryStack(); // 스택 현황 출력 추가
    }
    
    
    // 스택의 현재 상태를 출력하는 헬퍼 메소드
    private void printHistoryStack() {
        System.out.println("\n--- History Stack (Top to Bottom) ---");
        if (historyStack.isEmpty()) {
            System.out.println("(empty)");
        } else {
            // 스택의 맨 위(가장 최근)부터 출력
            for (int i = historyStack.size() - 1; i >= 0; i--) {
                UndoableCommand cmd = historyStack.get(i);
                // 스택 인덱스 (0이 가장 위), 클래스 이름, 메뉴 텍스트 출력
                System.out.println("[" + (historyStack.size() - 1 - i) + "]: " + cmd.getClass().getSimpleName() + " - (" + cmd.getMenuText() + ")");
            }
        }
        System.out.println("------------------------------------\n");
    }

    public void displayMenu(boolean isLoggedIn) {
        System.out.println("\nWhat would you like to do? Please make a choice:");
        
 
        Integer[] menuNumbersToShow = getMenuNumbersBasedOnLogin(isLoggedIn);
        

        for (Integer menuNumber : menuNumbersToShow) {
 
            if (menuNumber != 0) { 
                UndoableCommand menuOption = commands.get(menuNumber); 
                if (menuOption != null) {
                    System.out.println(menuOption.getMenuText());
                }
            }
        }
        

        UndoableCommand exitOption = commands.get(0); 
        if (exitOption != null) {
            System.out.println(exitOption.getMenuText());
        }
    }
    
    private Integer[] getMenuNumbersBasedOnLogin(boolean isUserLoggedIn) {
        if (isUserLoggedIn) {
   
            return new Integer[]{0, 5, 6, 7, 8}; 
        } else {
       
            return new Integer[]{0, 1, 2, 3, 4, 7, 8}; 
        }
    }
}