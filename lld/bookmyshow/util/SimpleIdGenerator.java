package lld.bookmyshow.util;

import java.util.concurrent.atomic.AtomicLong;

public class SimpleIdGenerator implements IdGenerator {
    private final AtomicLong seq = new AtomicLong(0);

    @Override
    public String newId() {
        return "ID-" + seq.incrementAndGet();
    }
}