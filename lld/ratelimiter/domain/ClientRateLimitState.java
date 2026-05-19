package lld.ratelimiter.domain;

public class ClientRateLimitState {

    private double availableTokens;
    private long lastRefillTimeMillis;

    public ClientRateLimitState(int capacity, long currentTimeMillis) {
        this.availableTokens = capacity;
        this.lastRefillTimeMillis = currentTimeMillis;
    }

    public double getAvailableTokens() {
        return availableTokens;
    }

    public long getLastRefillTimeMillis() {
        return lastRefillTimeMillis;
    }

    public void refill(double tokensToAdd, int capacity, long currentTimeMillis) {
        if (tokensToAdd <= 0) {
            return;
        }

        this.availableTokens = Math.min(capacity, this.availableTokens + tokensToAdd);
        this.lastRefillTimeMillis = currentTimeMillis;
    }

    public boolean hasAtLeastOneToken() {
        return availableTokens >= 1;
    }

    public void consumeOneToken() {
        if (!hasAtLeastOneToken()) {
            throw new IllegalStateException("Cannot consume token because bucket is empty");
        }

        this.availableTokens -= 1;
    }
}

