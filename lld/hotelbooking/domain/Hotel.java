package lld.hotelbooking.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Hotel {
    private final String id;
    private final String name;
    private final String city;

    // roomType -> inventory
    private final Map<String, RoomInventory> inventories = new HashMap<>();

    public Hotel(String id, String name, String city) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.city = Objects.requireNonNull(city);
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getCity() { return city; }

    public void addInventory(RoomInventory inv) {
        inventories.put(inv.getRoomType(), inv);
    }

    public RoomInventory getInventory(String roomType) {
        return inventories.get(roomType); // may be null, orchestrator/policy will handle
    }

    public Map<String, RoomInventory> getInventories() {
        return inventories;
    }
}
