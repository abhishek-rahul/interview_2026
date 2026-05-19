package lld.ratelimiter.policy;

import lld.ratelimiter.domain.ClientRateLimitState;

public interface RateLimitPolicy {
    public boolean allow(ClientRateLimitState state, long currentTimeMillis);
}