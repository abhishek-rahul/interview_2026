package lld.bookmyshow.domain;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

public class LockToken {
    private final String id;
    private final String showId;
    private final String userId;
    private final List<String> seatIds;
    private final Instant expiry;

    public LockToken(String id, String showId, String userId, List<String> seatIds, Instant expiry) {
        this.id = Objects.requireNonNull(id);
        this.showId = Objects.requireNonNull(showId);
        this.userId = Objects.requireNonNull(userId);
        this.seatIds = Objects.requireNonNull(seatIds);
        this.expiry = Objects.requireNonNull(expiry);
    }

    public String getId() { return id; }
    public String getShowId() { return showId; }
    public String getUserId() { return userId; }
    public List<String> getSeatIds() { return seatIds; }
    public Instant getExpiry() { return expiry; }

    // NO logic yet: isExpired(now)
}