package lld.parkinglot.util;

import java.util.UUID;

public final class UuidTicketIdGenerator implements TicketIdGenerator {
    @Override
    public String nextId() {
        return UUID.randomUUID().toString();
    }
}