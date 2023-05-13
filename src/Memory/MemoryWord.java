package Memory;

public class MemoryWord {
    private String key;
    private Object val;
    public MemoryWord(String key, Object val) {
        this.key = key;
        this.val = val;
    }

    public String toString(){
        return key + ": " + val;
    }

    public String getKey() {
        return key;
    }

    public Object getVal() {
        return val;
    }

}
