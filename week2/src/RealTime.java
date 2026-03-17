import java.util.*;

class Event {
    String url;
    String userId;
    String source;

    Event(String url, String userId, String source) {
        this.url = url;
        this.userId = userId;
        this.source = source;
    }
}

public class RealTime{

    // pageUrl -> visit count
    static HashMap<String, Integer> pageViews = new HashMap<>();

    // pageUrl -> unique visitors
    static HashMap<String, HashSet<String>> uniqueVisitors = new HashMap<>();

    // traffic source -> count
    static HashMap<String, Integer> trafficSources = new HashMap<>();

    // process event
    static void processEvent(Event e) {

        // count page views
        pageViews.put(e.url, pageViews.getOrDefault(e.url, 0) + 1);

        // track unique users
        uniqueVisitors.putIfAbsent(e.url, new HashSet<>());
        uniqueVisitors.get(e.url).add(e.userId);

        // count traffic source
        trafficSources.put(e.source, trafficSources.getOrDefault(e.source, 0) + 1);
    }

    // get top pages
    static void getDashboard() {

        System.out.println("Top Pages:");

        List<Map.Entry<String, Integer>> list =
                new ArrayList<>(pageViews.entrySet());

        list.sort((a, b) -> b.getValue() - a.getValue());

        int rank = 1;

        for (Map.Entry<String, Integer> entry : list) {

            String page = entry.getKey();
            int views = entry.getValue();
            int unique = uniqueVisitors.get(page).size();

            System.out.println(rank + ". " + page +
                    " - " + views + " views (" + unique + " unique)");

            rank++;
            if (rank > 10)
                break;
        }

        System.out.println("\nTraffic Sources:");

        int total = 0;
        for (int count : trafficSources.values())
            total += count;

        for (String source : trafficSources.keySet()) {

            int count = trafficSources.get(source);
            double percent = (count * 100.0) / total;

            System.out.printf("%s: %.0f%%\n", source, percent);
        }
    }

    public static void main(String[] args) {

        processEvent(new Event("/article/breaking-news", "user_123", "google"));
        processEvent(new Event("/article/breaking-news", "user_456", "facebook"));
        processEvent(new Event("/sports/championship", "user_789", "google"));
        processEvent(new Event("/sports/championship", "user_111", "direct"));
        processEvent(new Event("/article/breaking-news", "user_999", "google"));

        getDashboard();
    }
}