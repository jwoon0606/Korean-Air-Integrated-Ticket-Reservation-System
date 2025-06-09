package command.invoker;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import command.undo_command.UndoableCommand;


public class CommandRegistry {
    private Map<Integer, UndoableCommand> commands;

    private Stack<UndoableCommand> historyStack = new Stack<>();
    

    public CommandRegistry() {
        this.commands = new HashMap<>();

    }

   public void setCommand(int menuId, UndoableCommand command) {
        commands.put(menuId, command);
    }   
    
    public boolean executeCommand(int menuChoice, boolean isLoggedIn) {

        
        UndoableCommand selectedCommand = commands.get(menuChoice);

        if (selectedCommand.canExecute(isLoggedIn)) {
            selectedCommand.execute();

            historyStack.push(selectedCommand); 
            printHistoryStack();
            return true; 
        }
 
        System.out.println("You cannot perform that action right now.");
        return false; 
    }

    public void undoCommand(){
        if (historyStack.isEmpty()) {
            System.out.println("There are no actions to undo.");
            printHistoryStack(); 
            return; 
        }

        UndoableCommand commandToUndo = historyStack.pop();
        commandToUndo.undo();
        System.out.println("undo action complete: " + commandToUndo.getClass().getSimpleName());
        printHistoryStack(); 
    }
    
    
    private void printHistoryStack() {
        System.out.println("\n--- History Stack (Top to Bottom) ---");
        if (historyStack.isEmpty()) {
            System.out.println("(empty)");
        } else {
            for (int i = historyStack.size() - 1; i >= 0; i--) {
                UndoableCommand cmd = historyStack.get(i);
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
   
            return new Integer[]{0, 5, 6, 7, 8, 9, 15};
        } else {
       
            return new Integer[]{0, 1, 2, 3, 4, 7, 8, 15};
        }
    }
}