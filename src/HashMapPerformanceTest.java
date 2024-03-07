import java.util.Random;

public class HashMapPerformanceTest {

    public static void main(String[] args) {
        final int cacheCapacity = 100; // Adjust based on your cache capacity
        final int numberOfAccesses = 100000; // Total number of get/store operations
        CacheLRU<Integer, String> cache = new CacheLRU<>(cacheCapacity);
        Random rand = new Random();

        // Pre-fill the cache to its capacity with initial data
        for (int i = 0; i < cacheCapacity; i++) {
            cache.store(i, "Value " + i);
        }

        // Perform 'get' operations with a focus on simulating real-world access patterns
        long startTime = System.currentTimeMillis();
        int hits = 0;
        for (int i = 0; i < numberOfAccesses; i++) {
            // Access keys with a bias towards certain keys to simulate frequently accessed data
            int key = rand.nextInt((int) (cacheCapacity * 1.5)); // Access keys beyond the cache capacity to simulate misses
            String value = cache.lookUp(key);
            if (value != null) {
                hits++;
            } else {
                // Simulate adding a new entry if it's not present
                cache.store(key, "Value " + key);
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Total time for 'get' and conditional 'store' operations: " + (endTime - startTime) + " ns");
        System.out.println("Cache hits: " + hits);
        System.out.println("Cache misses: " + (numberOfAccesses - hits));
        System.out.println("Hit ratio: " + ((double) hits / numberOfAccesses));
        cache.printOperations();
    }
}
