package lld.ratelimiter.orchestrator;

import java.util.HashMap;
import java.util.Map;

import lld.ratelimiter.domain.ClientRateLimitState;
import lld.ratelimiter.policy.RateLimitPolicy;

public class RateLimiterService {

    private final Map<String, ClientRateLimitState> clientStates = new HashMap<>();
    private final RateLimitPolicy rateLimitPolicy;
    private final int bucketCapacity;

    public RateLimiterService(RateLimitPolicy rateLimitPolicy, int bucketCapacity) {
        this.rateLimitPolicy = rateLimitPolicy;
        this.bucketCapacity = bucketCapacity;
    }

    public synchronized boolean allowRequest(String clientId, long currentTimeMillis) {
        if (clientId == null || clientId.isBlank()) {
            throw new IllegalArgumentException("clientId is required");
        }

        ClientRateLimitState state = clientStates.get(clientId);

        if (state == null) {
            state = new ClientRateLimitState(bucketCapacity, currentTimeMillis);
            clientStates.put(clientId, state);
        }

        return rateLimitPolicy.allow(state, currentTimeMillis);
    }
}