import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class Main {

    public static void HashMapExample() {
        // 1. Create a HashMap
        Map<String, Integer> map = new HashMap<>();
        // 2. Add key-value pairs to the HashMap
        map.put("One", 1);
        map.put("Two", 2);
        map.put("Three", 3);
        // 3. Retrieve a value using a key
        int value = map.get("Two");
        System.out.println("Value for key 'Two': " + value);
        // 4. Check if a key exists
        boolean exists = map.containsKey("Three");
        System.out.println("Key 'Three' exists: " + exists);
        // 5. Remove a key-value pair
        map.remove("One");
        // 6. Iterate over the HashMap
        System.out.println("Iterating over HashMap:");
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        // 7. Contains value
        boolean containsValue = map.containsValue(3);
        System.out.println("Value '3' exists in HashMap: " + containsValue);
    }

    public static void LinkedHashMapExample() {
        // 1. Create a LinkedHashMap
        Map<String, Integer> map = new LinkedHashMap<>();
        // 2. Add key-value pairs to the LinkedHashMap
        map.put("One", 1);
        map.put("Two", 2);
        map.put("Three", 3);
        // 3. Retrieve a value using a key
        int value = map.get("Two");
        System.out.println("Value for key 'Two': " + value);
        // 4. Check if a key exists
        boolean exists = map.containsKey("Three");
        System.out.println("Key 'Three' exists: " + exists);
        // 5. Remove a key-value pair
        map.remove("One");
        // 6. Iterate over the HashMap
        System.out.println("Iterating over HashMap:");
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        // 7. Contains value
        boolean containsValue = map.containsValue(3);
        System.out.println("Value '3' exists in HashMap: " + containsValue);

        // Give an example of remove Eldest entry in LinkedHashMap
        LinkedHashMap<String, Integer> linkedMap = new LinkedHashMap<String, Integer>(16, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, Integer> eldest) {
                return size() > 2; // Remove eldest entry when size exceeds 2
            }
        };
        linkedMap.put("One", 1);
        linkedMap.put("Two", 2);    
        linkedMap.put("Three", 3); // This will cause "One" to be removed
        System.out.println("LinkedHashMap after adding three entries (max size 2):");
        for (Map.Entry<String, Integer> entry : linkedMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public static void TreeMapExample() {
        // 1. Create a TreeMap
        SortedMap<String, Integer> map = new TreeMap<>();
        // 2. Add key-value pairs to the TreeMap
        map.put("One", 1);
        map.put("Two", 2);
        map.put("Three", 3);
        map.put("Four", 4);
        map.put("Five", 5);    
        // 3. Retrieve a value using a key
        int value = map.get("Two");
        System.out.println("Value for key 'Two': " + value);
        // 4. Check if a key exists
        boolean exists = map.containsKey("Three");
        System.out.println("Key 'Three' exists: " + exists);
        // 5. Remove a key-value pair
        map.remove("One");
        // 6. Iterate over the TreeMap (will be in sorted order)
        System.out.println("Iterating over TreeMap:");
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        // 7. Contains value
        boolean containsValue = map.containsValue(3);
        System.out.println("Value '3' exists in TreeMap: " + containsValue);

        // firstKey and lastKey
        String firstKey = map.firstKey();
        String lastKey = map.lastKey();
        System.out.println("First key in TreeMap: " + firstKey);
        System.out.println("Last key in TreeMap: " + lastKey);

        // subMap example headMap example tailMap example
        SortedMap<String, Integer> subMap = map.subMap("Four", "Two");
        System.out.println("SubMap from 'Two' to 'Four':");
        for (Map.Entry<String, Integer> entry : subMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        SortedMap<String, Integer> headMap = map.headMap("Three");
        System.out.println("HeadMap up to 'Three':");
        for (Map.Entry<String, Integer> entry : headMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        SortedMap<String, Integer> tailMap = map.tailMap("Two");
        System.out.println("TailMap from 'Two':");
        for (Map.Entry<String, Integer> entry : tailMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public static void HashTableExample() {
        // 1. Create a HashTable
        Map<String, Integer> map = new java.util.Hashtable<>();
        // 2. Add key-value pairs to the HashTable
        map.put("One", 1);
        map.put("Two", 2);
        map.put("Three", 3);
        // 3. Retrieve a value using a key
        int value = map.get("Two");
        System.out.println("Value for key 'Two': " + value);
        // 4. Check if a key exists
        boolean exists = map.containsKey("Three");
        System.out.println("Key 'Three' exists: " + exists);
        // 5. Remove a key-value pair
        map.remove("One");
        // 6. Iterate over the HashTable
        System.out.println("Iterating over HashTable:");
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        // 7. Contains value
        boolean containsValue = map.containsValue(3);
        System.out.println("Value '3' exists in HashTable: " + containsValue);
    }
    
    public static void main(String[] args) {
        //HashMapExample();
        //LinkedHashMapExample();
        //TreeMapExample();
        HashTableExample();
    }
}
