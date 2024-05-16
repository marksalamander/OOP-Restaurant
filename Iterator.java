public interface Iterator {
    boolean hasNext();
    Object next();
    int getIndex();
}

class MenuIterator implements Iterator {
    private MenuItem[] items;
    private int index;

    public MenuIterator(MenuItem[] items) {
        this.items = items;
        this.index = 0;
    }

    public int getIndex() {
        return index;
    }

    public MenuItem next() {
        MenuItem menuItem = items[index];
        index++;
        return menuItem;
    }

    public boolean hasNext() {
        if (index >= items.length || items[index] == null) {
            return false;
        }
        else {
            return true;
        }
    }
}

class MenuItem {
    private String itemNum;
    private String name;
    private double price;
    private int inventory;
    
    public MenuItem(String itemNum, String name, double price, int inventory) {
         this.itemNum = itemNum;
         this.name = name;
         this.price = price;
         this.inventory = inventory;
    }

    public String getItemNum() {
         return itemNum;
    }

    public String getName() {
         return name;
    }

    public double getPrice() {
         return price;
    }

    public void setInventory(int inventory) {
         this.inventory = inventory;
    }

    public int getInventory() {
         return inventory;
    }
}
