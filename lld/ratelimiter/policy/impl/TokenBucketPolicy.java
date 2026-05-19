package lld.ratelimiter.policy.impl;

import lld.ratelimiter.policy.RateLimitPolicy;
import lld.ratelimiter.domain.*;

public class TokenBucketPolicy implements RateLimitPolicy {

    private final int capacity;
    private final double refillTokensPerSecond;

    public TokenBucketPolicy(int capacity, double refillTokensPerSecond) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }

        if (refillTokensPerSecond <= 0) {
            throw new IllegalArgumentException("Refill rate must be positive");
        }

        this.capacity = capacity;
        this.refillTokensPerSecond = refillTokensPerSecond;
    }

    @Override
    public boolean allow(ClientRateLimitState state, long currentTimeMillis) {
        refillBucketIfNeeded(state, currentTimeMillis);

        if (!state.hasAtLeastOneToken()) {
            return false;
        }

        state.consumeOneToken();
        return true;
    }

    private void refillBucketIfNeeded(ClientRateLimitState state, long currentTimeMillis) {
        long elapsedMillis = currentTimeMillis - state.getLastRefillTimeMillis();

        if (elapsedMillis <= 0) {
            return;
        }

        double elapsedSeconds = elapsedMillis / 1000.0;
        double tokensToAdd = elapsedSeconds * refillTokensPerSecond;

        state.refill(tokensToAdd, capacity, currentTimeMillis);
    }
}
