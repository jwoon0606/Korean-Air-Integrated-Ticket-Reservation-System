package command.invoker;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import command.undo_command.UndoableCommand;

/**
 * 이 클래스는 마치 똑똑한 리모컨 같아요!
 * 여러 가지 명령(버튼)들을 가지고 있다가, 우리가 누르면 해당 명령을 실행시켜 줘요.
 * 이렇게 하면 로봇(Receiver)에게 직접 명령하지 않아도 돼서, 나중에 새로운 버튼을 추가하거나 바꾸기가 쉬워요.
 */
public class CommandRegistry {
    // 여기에는 우리가 선택할 수 있는 모든 행동(버튼)들이 들어있어요.
    // 각 행동은 숫자(예: 1번, 2번)로 찾을 수 있답니다.
    private Map<Integer, UndoableCommand> commands;
    // 여기에는 우리가 방금 했던 행동들을 순서대로 쌓아둬요.
    // 마치 접시를 쌓는 것처럼, 가장 마지막에 한 행동을 가장 먼저 되돌릴 수 있어요. (실행 취소 기능!)
    private Stack<UndoableCommand> historyStack = new Stack<>();
    
    /**
     * 새로운 똑똑한 리모컨을 만드는 거예요.
     * 마치 새 TV 리모컨을 받아서, 이제 막 버튼들을 설정할 준비가 된 것과 같아요.
     */
    public CommandRegistry() {
        this.commands = new HashMap<>();
    }
    
    /**
     * 우리 리모컨에 새로운 행동(버튼)을 추가하는 거예요.
     * 
     * @param menuId 이 행동에 붙일 번호예요 (예: 1번 버튼).
     * @param command 실제로 할 행동이에요 (예: "TV 켜기" 행동).
     */
    public void setCommand(int menuId, UndoableCommand command) {
        commands.put(menuId, command);
    }
    
    /**
     * 우리가 선택한 행동을 실행하는 거예요.
     * 마치 리모컨 버튼을 누르는 것과 같아요.
     * 
     * @param menuChoice 우리가 선택한 행동의 번호예요.
     * @param isLoggedIn 우리가 지금 로그인 되어 있는지 아닌지 알려줘요.
     * @return 행동이 잘 실행되었으면 true, 아니면 false를 돌려줘요.
     */
    public boolean executeCommand(int menuChoice, boolean isLoggedIn) {
        // 우리가 선택한 번호에 해당하는 행동이 있는지 확인해요.
        if (!hasCommand(menuChoice)) {
            System.out.println("그런 선택지는 없어요. 메뉴에서 번호를 골라주세요.");
            return false; // 행동이 실행되지 않았어요.
        }
        
        // 우리 행동 목록에서 선택한 행동을 가져와요.
        UndoableCommand selectedCommand = commands.get(menuChoice);
        // 이 행동을 지금 할 수 있는지 확인해요 (예: 어떤 행동은 로그인해야만 할 수 있어요).
        if (selectedCommand.canExecute(isLoggedIn)) {
            selectedCommand.execute(); // 행동을 실행해요!
            // 행동을 되돌릴 수 있다면, 우리가 했던 행동 기록에 저장해요.
            historyStack.push(selectedCommand); 
            return true; // 행동이 잘 실행되었어요.
        }
        // 만약 지금 이 행동을 할 수 없다면 (예: 로그인 안 했는데 로그아웃 하려고 할 때)
        System.out.println("지금은 그 행동을 할 수 없어요.");
        return false; // 행동이 실행되지 않았어요.
    }

    /**
     * 우리가 마지막으로 했던 행동을 되돌리는 거예요.
     * 마치 "실행 취소" 버튼 같아요.
     */
    public void undoCommand(){
        // 되돌릴 행동이 우리 기록에 있는지 확인해요.
        if (historyStack.isEmpty()) {
            System.out.println("되돌릴 행동이 없어요.");
            return; // 할 일이 없네요.
        }

        // 우리 기록에서 마지막으로 했던 행동을 가져와요.
        UndoableCommand commandToUndo = historyStack.pop();
        // 그 행동에게 "너 자신을 되돌려!"라고 말해요.
        commandToUndo.undo();
        System.out.println("마지막 행동을 되돌렸어요.");
    }
    
    /**
     * 어떤 번호에 해당하는 행동이 우리한테 있는지 확인하는 거예요.
     * 
     * @param menuId 우리가 확인하고 싶은 번호예요.
     * @return 이 번호에 해당하는 행동이 있으면 true, 없으면 false를 돌려줘요.
     */
    private boolean hasCommand(int menuId) {
        return commands.containsKey(menuId);
    }
    
    /**
     * 우리가 할 수 있는 행동 메뉴를 보여주는 거예요.
     * 이 메뉴는 우리가 로그인했는지 안 했는지에 따라 다르게 보여요.
     * 
     * @param isLoggedIn 우리가 로그인했는지 알려줘요.
     */
    public void displayMenu(boolean isLoggedIn) {
        System.out.println("\\n무엇을 할까요? 골라보세요:"); // "메뉴"는 너무 짧으니까요.
        
        // 로그인 상태에 따라 보여줄 메뉴 번호 목록을 가져와요.
        Integer[] menuNumbersToShow = getMenuNumbersBasedOnLogin(isLoggedIn);
        
        // 보여줘야 할 각 메뉴 번호를 하나씩 살펴봐요.
        for (Integer menuNumber : menuNumbersToShow) {
            // "종료" 메뉴(0번)는 특별하니까, 맨 마지막에 보여줄 거예요.
            if (menuNumber != 0) { 
                UndoableCommand menuOption = commands.get(menuNumber); // Command 대신 UndoableCommand로 바꿨어요.
                // 이 번호에 해당하는 행동이 있다면, 그 행동의 설명을 보여줘요.
                if (menuOption != null) {
                    System.out.println(menuOption.getMenuText());
                }
            }
        }
        
        // "종료" 메뉴는 항상 마지막에 보여줘요.
        UndoableCommand exitOption = commands.get(0); // Command 대신 UndoableCommand로 바꿨어요.
        if (exitOption != null) {
            System.out.println(exitOption.getMenuText());
        }
    }
    
    /**
     * 우리가 선택할 수 있는 메뉴 번호 목록을 알려줘요.
     * 이 목록은 우리가 로그인했는지 안 했는지에 따라 달라요.
     * 
     * @param isUserLoggedIn 우리가 로그인했으면 true, 아니면 false예요.
     * @return 메뉴 번호들이 들어있는 목록을 돌려줘요.
     */
    private Integer[] getMenuNumbersBasedOnLogin(boolean isUserLoggedIn) {
        if (isUserLoggedIn) {
            // 여기 있는 번호들은 로그인한 사람들이 볼 수 있는 메뉴예요.
            // 예를 들어: 0번은 종료, 5번은 로그아웃, 6번은 내 정보 보기 같은 거예요.
            return new Integer[]{0, 5, 6, 7, 8}; 
        } else {
            // 여기 있는 번호들은 로그인하지 않은 사람들이 볼 수 있는 메뉴예요.
            // 예를 들어: 0번은 종료, 1번은 로그인, 2번은 회원가입 같은 거예요.
            return new Integer[]{0, 1, 2, 3, 4, 7, 8}; 
        }
    }
}