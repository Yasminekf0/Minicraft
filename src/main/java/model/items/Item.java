package model.items;

public abstract class Item {
    protected final String section;
    protected int count;
    public Item(String section) {
        this.section = section;
        this.count = 0;
    }

    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }

    public String getSection() {
        return section;
    }
}
