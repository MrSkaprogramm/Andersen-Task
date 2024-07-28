package by.andersen.tr.utils;

public abstract class IDUtil {
    private static long nextId = 0;
    @NullableWarning
    private long ID;

    protected IDUtil() {
        this.ID = nextId++;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }
}
