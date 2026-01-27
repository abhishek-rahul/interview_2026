package lld.parkinglot.util;

public final class FakeClock implements Clock {
    private long now;

    public FakeClock(long startMillis) {
        this.now = startMillis;
    }

    @Override
    public long nowMillis() {
        return now;
    }

    public void advanceMillis(long delta) {
        if (delta < 0) throw new IllegalArgumentException("delta must be >= 0");
        now += delta;
    }
}
