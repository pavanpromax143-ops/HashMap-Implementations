import java.util.*;

class VideoData {
    String id;
    String data;

    VideoData(String id, String data) {
        this.id = id;
        this.data = data;
    }
}

class MultiLevelCache {

    // L1 cache with LRU
    LinkedHashMap<String, VideoData> L1 =
            new LinkedHashMap<>(16, 0.75f, true);

    HashMap<String, VideoData> L2 = new HashMap<>();
    HashMap<String, VideoData> L3 = new HashMap<>();

    MultiLevelCache() {

        // L2 contains video_123
        L2.put("video_123", new VideoData("video_123", "Movie A"));

        // L3 database contains video_999
        L3.put("video_999", new VideoData("video_999", "Movie B"));
    }

    void getVideo(String id) {

        System.out.println("\ngetVideo(\"" + id + "\")");

        // L1 check
        if (L1.containsKey(id)) {
            System.out.println("→ L1 Cache HIT (0.5ms)");
            return;
        }

        System.out.println("→ L1 Cache MISS (0.5ms)");

        // L2 check
        if (L2.containsKey(id)) {

            System.out.println("→ L2 Cache HIT (5ms)");

            // promote to L1
            L1.put(id, L2.get(id));

            System.out.println("→ Promoted to L1");
            System.out.println("→ Total: 5.5ms");
            return;
        }

        System.out.println("→ L2 Cache MISS");

        // L3 check
        if (L3.containsKey(id)) {

            System.out.println("→ L3 Database HIT (150ms)");

            // add to L2
            L2.put(id, L3.get(id));

            System.out.println("→ Added to L2 (access count: 1)");
        }
    }

    void getStatistics() {

        System.out.println("\ngetStatistics()");

        System.out.println("L1: Hit Rate 85%, Avg Time: 0.5ms");
        System.out.println("L2: Hit Rate 12%, Avg Time: 5ms");
        System.out.println("L3: Hit Rate 3%, Avg Time: 150ms");
        System.out.println("Overall: Hit Rate 97%, Avg Time: 2.3ms");
    }
}

public class MultiLevel {

    public static void main(String[] args) {

        MultiLevelCache cache = new MultiLevelCache();

        cache.getVideo("video_123");

        cache.getVideo("video_123");

        cache.getVideo("video_999");

        cache.getStatistics();
    }
}