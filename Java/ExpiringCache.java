
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class ExpiringCache<K, V> {
    private final Map<K, CacheItem<V>> cache;
    private final ScheduledExecutorService cleaner;
    private final long expirationTimeMillis;

    public ExpiringCache(long expirationTimeMillis, int maxSize) {
        this.cache = new ConcurrentHashMap<>(maxSize);
        this.expirationTimeMillis = expirationTimeMillis;
        this.cleaner = Executors.newSingleThreadScheduledExecutor();
        startCleaner();
    }

    private void startCleaner() {
        cleaner.scheduleAtFixedRate(() -> {
            long now = System.currentTimeMillis();
            for (K key : cache.keySet()) {
                CacheItem<V> item = cache.get(key);
                if (item != null && now - item.creationTime > expirationTimeMillis) {
                    cache.remove(key);
                }
            }
        }, expirationTimeMillis, expirationTimeMillis, TimeUnit.MILLISECONDS);
    }

    public void put(K key, V value) {
        cache.put(key, new CacheItem<>(value));
    }

    public V get(K key) {
        CacheItem<V> item = cache.get(key);
        return (item == null || isExpired(item)) ? null : item.value;
    }

    private boolean isExpired(CacheItem<V> item) {
        return System.currentTimeMillis() - item.creationTime > expirationTimeMillis;
    }

    public void remove(K key) {
        cache.remove(key);
    }

    public void clear() {
        cache.clear();
    }

    public void shutdown() {
        cleaner.shutdown();
    }

    private static class CacheItem<V> {
        private final V value;
        private final long creationTime;

        CacheItem(V value) {
            this.value = value;
            this.creationTime = System.currentTimeMillis();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExpiringCache<String, String> cache = new ExpiringCache<>(5000, 100); // 5 seconds expiration time

        cache.put("key1", "value1");
        System.out.println("Added key1: " + cache.get("key1"));

        Thread.sleep(6000); // Wait for 6 seconds (expiration time is 5 seconds)
        System.out.println("After 6 seconds, key1: " + cache.get("key1")); // Should be null (expired)

        cache.shutdown(); // Gracefully shut down the cleaner thread
    }
}
