interface InventoryState {
    boolean handleRequest();
}

class InStock implements InventoryState {
    @Override
    public boolean handleRequest() {
        return true;
    }
}

class OutOfStock implements InventoryState {
    @Override
    public boolean handleRequest() {
        return false;
    }
}

class InventoryContext {
    private MenuItem menuItem;
    private InventoryState currentState;

    public InventoryContext(MenuItem menuItem) {
        this.menuItem = menuItem;
        this.currentState = (menuItem.getInventory() > 0) ? new InStock() : new OutOfStock();
    }

    public boolean isAvailable() {
        return currentState.handleRequest();
    }

    public void reduceInventory() {
        int inventory = menuItem.getInventory();
        inventory--;
        menuItem.setInventory(inventory);
        if (inventory <= 0) {
            currentState = new OutOfStock();
        }
    }
}