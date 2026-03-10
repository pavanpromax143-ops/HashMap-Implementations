import java.util.HashMap;

class TokenBucket {

    int tokens;
    int maxTokens;
    long lastRefillTime;
    int refillRate; // tokens per second

    TokenBucket(int maxTokens, int refillRate) {
        this.maxTokens = maxTokens;
        this.tokens = maxTokens;
        this.refillRate = refillRate;
        this.lastRefillTime = System.currentTimeMillis();
    }

    void refill() {
        long now = System.currentTimeMillis();
        long elapsed = (now - lastRefillTime) / 1000;

        int tokensToAdd = (int) (elapsed * refillRate);

        if (tokensToAdd > 0) {
            tokens = Math.min(maxTokens, tokens + tokensToAdd);
            lastRefillTime = now;
        }
    }

    boolean allowRequest() {
        refill();

        if (tokens > 0) {
            tokens--;
            return true;
        }

        return false;
    }
}

public class solution{

    static HashMap<String, TokenBucket> clients = new HashMap<>();

    static int LIMIT = 1000;
    static int REFILL_RATE = 1000 / 3600; // tokens per second

    static void checkRateLimit(String clientId) {

        clients.putIfAbsent(clientId, new TokenBucket(LIMIT, REFILL_RATE));

        TokenBucket bucket = clients.get(clientId);

        if (bucket.allowRequest()) {
            System.out.println("Allowed (" + bucket.tokens + " requests remaining)");
        } else {
            System.out.println("Denied (0 requests remaining, retry later)");
        }
    }

    static void getRateLimitStatus(String clientId) {

        TokenBucket bucket = clients.get(clientId);

        System.out.println("{used: " + (LIMIT - bucket.tokens) +
                ", limit: " + LIMIT +
                ", reset: " + (bucket.lastRefillTime + 3600000) + "}");
    }

    public static void main(String[] args) {

        checkRateLimit("abc123");
        checkRateLimit("abc123");
        checkRateLimit("abc123");

        getRateLimitStatus("abc123");
    }
}