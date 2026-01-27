package lld.parkinglot.util;
public class SystemClock implements Clock {
    @Override
    public long nowMillis() {
        return System.currentTimeMillis();
    }
}
