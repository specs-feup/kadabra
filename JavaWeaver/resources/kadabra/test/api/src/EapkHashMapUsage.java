import java.util.HashMap;
import java.util.Map;

public class EapkHashMapUsage {
    HashMap<String, String> hashMapField = new HashMap<String, String>();

    public Map<String, Integer> GenerateHashMap()
    {
        HashMap<String, Integer> hashMapVar = new HashMap<String, Integer>();
        hashMapVar.put("foo", 1);
        return hashMapVar;
    }
}