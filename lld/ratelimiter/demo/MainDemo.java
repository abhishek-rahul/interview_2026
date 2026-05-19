package lld.ratelimiter.demo;

import lld.ratelimiter.orchestrator.RateLimiterService;
import lld.ratelimiter.policy.RateLimitPolicy;
import lld.ratelimiter.policy.impl.TokenBucketPolicy;

public class MainDemo {

    public static void main(String[] args) {

        int capacity = 3;
        double refillTokensPerSecond = 1.0;

        RateLimitPolicy policy = new TokenBucketPolicy(capacity, refillTokensPerSecond);
        RateLimiterService rateLimiterService = new RateLimiterService(policy, capacity);

        String clientId = "client-1";

        long startTime = System.currentTimeMillis();

        System.out.println("=== Client sends 4 instant requests ===");

        System.out.println("Request 1: " + rateLimiterService.allowRequest(clientId, startTime));
        System.out.println("Request 2: " + rateLimiterService.allowRequest(clientId, startTime));
        System.out.println("Request 3: " + rateLimiterService.allowRequest(clientId, startTime));
        System.out.println("Request 4: " + rateLimiterService.allowRequest(clientId, startTime));

        System.out.println();
        System.out.println("=== After 2 seconds, tokens refill ===");

        long afterTwoSeconds = startTime + 2000;

        System.out.println("Request 5: " + rateLimiterService.allowRequest(clientId, afterTwoSeconds));
        System.out.println("Request 6: " + rateLimiterService.allowRequest(clientId, afterTwoSeconds));
        System.out.println("Request 7: " + rateLimiterService.allowRequest(clientId, afterTwoSeconds));

        System.out.println();
        System.out.println("=== Different client has separate bucket ===");

        String anotherClient = "client-2";

        System.out.println("Client-2 Request 1: " + rateLimiterService.allowRequest(anotherClient, startTime));
        System.out.println("Client-2 Request 2: " + rateLimiterService.allowRequest(anotherClient, startTime));
    }
}
