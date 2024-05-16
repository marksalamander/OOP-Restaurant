import java.util.Scanner;

class Commands {
    private static Menu menu = new Menu();
    private static OpenTables openTables = new OpenTables();
    private static Waitress waitress = new Waitress(menu, openTables);

    public static Waitress getWaitress() {
        return waitress;
    }

    static RemoteControl control = new RemoteControl();

    static Command displayMenu = new DisplayMenu(waitress);
    static Command submitOrder = new SubmitOrder(waitress);
    static Command diplayTab = new DisplayTab(waitress);
    static Command chooseTable = new ChooseTable(openTables);
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
    
        while(true){
            System.out.println("============ Restaurant ============");
            System.out.println("1. Menu ");
            System.out.println("2. Submit Order ");
            System.out.println("3. Tab ");
            System.out.println("4. Choose table ");
            System.out.print("Input ('q' to leave): ");
            String userInput = scanner.nextLine();
            if(userInput.equals("q")) {
                break;
            }
            System.out.println();

            executeChoice(userInput);
            System.out.println();
        }
        scanner.close();
    }

    private static void executeChoice(String input) {
        switch(input){
            case "1":
                Commands.control.setCommand(Commands.displayMenu);
                Commands.control.executeCommand();
                break;

            case "2":
                Commands.control.setCommand(Commands.submitOrder);
                Commands.control.executeCommand();
                break;

            case "3":
                Commands.control.setCommand(Commands.diplayTab);
                Commands.control.executeCommand();
                break;

            case "4":
                Commands.control.setCommand(Commands.chooseTable);
                Commands.control.executeCommand();
                Commands.getWaitress().setTable();
                break;

            case "q":
                break;

            default:
                System.out.println("Invalid Input");
        }
    }
}