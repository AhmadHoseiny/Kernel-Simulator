package memory;

public class MemoryWord {
    private String key;
    private String val;

    public MemoryWord() {
    }

    public MemoryWord(String key, String val) {
        this.key = key;
        this.val = val;
    }

    public String toString() {
        return key + ": " + val;
    }

    public String getKey() {
        return key;
    }

    public String getVal() {
        return val;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
