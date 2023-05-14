package Memory;

public class MemoryWord {
    private String key;
    private Object val;

    public MemoryWord() {
    }

    public MemoryWord(String key, Object val) {
        this.key = key;
        this.val = val;
    }

    public String toString() {
        return key + ": " + val;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getVal() {
        return val;
    }

    public void setVal(Object val) {
        this.val = val;
    }
}
