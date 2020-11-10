import java.util.HashMap;

public class JeresHashMap extends HashMap<Integer, String> {

    public void add(String str){
        int index = this.size();
        this.put(index, str);
    }

}
