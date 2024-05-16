import java.util.Scanner;

interface TableState {
    boolean handleRequest();
}

class ReadyState implements TableState {
    @Override
    public boolean handleRequest() {
        return true;
    }
}

class OccupiedState implements TableState {
    @Override
    public boolean handleRequest() {
        return false;
    }
}

class TableContext {
    private TableState currentState;

    public TableContext(TableState state) {
        this.currentState = state;
    }

    public void setState(TableState state) {
        this.currentState = state;
    }

    public void getState() {
        if(isAvailable()) {
            System.out.print("Available");
        }
        else {
            System.out.print("Occupied");
        }
    }

    public boolean isAvailable() {
        return currentState.handleRequest();
    }    
}

class OpenTables {
    private Table[] tables = {new Table(new TableContext(new ReadyState()), new Order(), new Tab(), "1"),
                    new Table(new TableContext(new ReadyState()), new Order(), new Tab(), "2"),
                    new Table(new TableContext(new ReadyState()), new Order(), new Tab(), "3"),
                    new Table(new TableContext(new ReadyState()), new Order(), new Tab(), "4")
                };
    private Table chosenTable;
    private Scanner scanner = new Scanner(System.in);

    public Table[] getTables() {
        return tables;
    }

    public Table getChosenTable() {
        return chosenTable;
    }

    public void displayTables() {
        for(Table table : tables) {
            System.out.print(table.getTableNumber() + " - ");
            table.getContext().getState();
            System.out.println();
        }
    }

    public void chooseTable() {
        displayTables();

        while(true) {
            System.out.print("Choose a table ('q' to finish): ");
            String userInput = scanner.nextLine();

            if(InputValidation.isValid(userInput)){
                for(Table table : tables) {
                    if(userInput.equals(table.getTableNumber())) {
                        table.getContext().setState(new OccupiedState());
                        chosenTable = table;
                        break;
                    }
                }
                break;
            }
            else {
                System.out.println("\nInvalid input\n");
            }
        }
    }
}

class Table {
    private TableContext context;
    private Order order;
    private Tab tab;
    private String tableNum;

    public Table(TableContext context, Order order, Tab tab, String tableNum) {
        this.context = context;
        this.order = order;
        this.tab = tab;
        this.tableNum = tableNum;
    }

    public TableContext getContext() {
        return context;
    }

    public Order getOrder() {
        return order;
    }

    public Tab getTab() {
        return tab;
    }

    public String getTableNumber() {
        return tableNum;
    }
}