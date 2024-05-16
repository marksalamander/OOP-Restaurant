import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

interface Command {
    void execute();
}

class DisplayMenu implements Command{
    private Waitress waitress;

    public DisplayMenu(Waitress waitress) {
        this.waitress = waitress;
    }

    @Override
    public void execute() {
        waitress.printMenu();
    }
}

class SubmitOrder implements Command{
    private Waitress waitress;

    public SubmitOrder(Waitress waitress) {
        this.waitress = waitress;
    }

    @Override
    public void execute() {
        waitress.printOrder();
    }
}

class DisplayTab implements Command {
    private Waitress waitress;

    public DisplayTab(Waitress waitress) {
        this.waitress = waitress;
    }

    @Override
    public void execute() {
        waitress.printTab();
        waitress.payTab();
    }
}

class ChooseTable implements Command {
    private OpenTables table;

    public ChooseTable(OpenTables table) {
        this.table = table;
    }

    @Override
    public void execute() {
        table.chooseTable();
    }
}

class Menu {
    MenuItem[] menuItems = {new MenuItem("1", "Steak", 10.0, 2),
                            new MenuItem("2", "Burger", 6.0, 5),
                            new MenuItem("3", "Salad", 5.0, 7),
                            new MenuItem("4", "Soup", 4.0, 3)
                        };
    
    public Iterator createIterator() {
        return new MenuIterator(menuItems);
    }
}

class Waitress {
    private Scanner scanner = new Scanner(System.in);
    private Menu menu;
    private Order order;
    private Tab tab;
    private OpenTables openTables;
    private Table chosenTable;
    private Table[] tables;
    private int available;

    public Waitress(Menu menu, OpenTables openTables) {
        this.menu = menu;
        this.openTables = openTables;
        tables = openTables.getTables();
        available = tables.length;
    }

    public void setTable() {
        chosenTable = openTables.getChosenTable();
        order = chosenTable.getOrder();
        tab = chosenTable.getTab();
        available-=1;
    }

    public void resetTable() {
        tab.clear();
        order.clear();
        tab = null;
        order = null;
        chosenTable.getContext().setState(new ReadyState());
        available+=1;
    }

    public void printMenu() {
        Iterator menuIterator = menu.createIterator();
        System.out.println("\t---- Menu ----");
        printMenu(menuIterator);
    }

    public void printMenu(Iterator iterator) {
        while(iterator.hasNext()) {
            MenuItem menuItem = (MenuItem) iterator.next();
            InventoryContext context = new InventoryContext(menuItem);

            System.out.print(iterator.getIndex() + ": " +  menuItem.getName() + ", Price: $" + menuItem.getPrice());

            if(context.isAvailable()) {
                System.out.println();
            }
            else {
                System.out.print(" - Out of stock\n");
            }
        }

        if(available >= tables.length) {
            System.out.println("\nPlease choose a table before ordering.");
        }
        else {
            order.writeOrder(menu);
            tab.setItems(order.getItems());
        }
    }
    
    public void printOrder() {
        if(order == null) {
            System.out.println("Please select a table first");
        }
        else {
            order.printOrder();
        }
    }

    public void printTab() {
        if(order == null) {
            System.out.println("Please select a table first");
        }
        else {
            tab.printTab();
        }
    }

    public void payTab() {
        while(true) {
            System.out.print("Pay tab? (y/n): ");
            String userInput = scanner.nextLine();
            if(userInput.equals("y")) {
                System.out.println("\nTab paid, thank you.");
                resetTable();
                break;
            }
            else if(userInput.equals("n")) {
                break;
            }
            else {
                System.out.println("\nInvalid input\n");
            }
        }
    }
}

class Order {
    private Scanner scanner = new Scanner(System.in);
    private List<MenuItem> items = new ArrayList<>();

    public List<MenuItem> getItems() {
        return items;
    }

    public void clear() {
        items.clear();
    }

    public void writeOrder(Menu menu) {
        String userInput = "";
        while(!userInput.equals("q")) {
            Iterator menuIterator = menu.createIterator();
            System.out.print("\nEnter food number ('q' to finish): ");
            userInput = scanner.nextLine();
            if(!InputValidation.isValid(userInput)) {
                System.out.println("Inavlid input");
            }
            else {
                writeItem(userInput, menuIterator);
            }
        }
    }

    public void writeItem(String input, Iterator iterator) {
        while(iterator.hasNext()) {
            MenuItem menuItem = (MenuItem) iterator.next();

            if(menuItem.getItemNum().equals(input)) {
                InventoryContext context = new InventoryContext(menuItem);
                if(context.isAvailable()) {
                    items.add(menuItem);
                    context.reduceInventory();
                }
                else {
                    System.out.println("Out of stock: Please choose another item");
                }
            }
        }
    }

    public void printOrder() {
        System.out.println("\t---- Order ----");

        if(items.isEmpty()) {
            System.out.println("No items in order");
        }
        else {
            for (MenuItem item : items) {
                System.out.println(item.getName());
            }
        }
    }
}

class Tab {
    private List<MenuItem> items = new ArrayList<MenuItem>();

    public void setItems(List<MenuItem> items) {
        this.items = items;
    }

    public void clear() {
        items.clear();
    }

    public void printTab() {
        double total = 0;
        System.out.println("\t---- Tab ----");
        if(items.isEmpty()) {
            System.out.println("No tab");
        }
        else {
            for(MenuItem item : items) {
                System.out.println(item.getItemNum() + " - " + item.getName());
                total += item.getPrice();
            }
            System.out.println("Total = $" + total);
        }
    }
}

class RemoteControl {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void executeCommand() {
        command.execute();
    }
}
