package lld.elevator.domain;

import java.util.TreeSet;

import lld.elevator.domain.enums.Direction;

public class Elevator {
    private final int id;
    private int currentFloor;
    private Direction direction;
    private final TreeSet<Integer> pendingStops;

    public Elevator(int id, int startFloor) {
        this.id = id;
        this.currentFloor = startFloor;
        this.direction = Direction.IDLE;
        this.pendingStops = new TreeSet<>();
    }

    public int getId() {
        return id;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean isIdle() {
        return direction == Direction.IDLE && pendingStops.isEmpty();
    }

    public int pendingCount() {
        return pendingStops.size();
    }

    public void addStop(int floor) {
        pendingStops.add(floor);
        updateDirection();
    }

    public void moveOneStep() {
        if (pendingStops.isEmpty()) {
            direction = Direction.IDLE;
            return;
        }

        Integer nextStop = getNextStop();

        if (nextStop == null) {
            direction = Direction.IDLE;
            return;
        }

        if (currentFloor < nextStop) {
            direction = Direction.UP;
            currentFloor++;
        } else if (currentFloor > nextStop) {
            direction = Direction.DOWN;
            currentFloor--;
        }

        completeStopIfNeeded();
        updateDirection();
    }

    private Integer getNextStop() {
        if (pendingStops.isEmpty()) {
            return null;
        }

        if (direction == Direction.UP) {
            Integer higher = pendingStops.ceiling(currentFloor);
            if (higher != null) {
                return higher;
            }
            return pendingStops.first();
        }

        if (direction == Direction.DOWN) {
            Integer lower = pendingStops.floor(currentFloor);
            if (lower != null) {
                return lower;
            }
            return pendingStops.last();
        }

        return nearestStop();
    }

    private Integer nearestStop() {
        Integer lower = pendingStops.floor(currentFloor);
        Integer higher = pendingStops.ceiling(currentFloor);

        if (lower == null) {
            return higher;
        }

        if (higher == null) {
            return lower;
        }

        int lowerDistance = Math.abs(currentFloor - lower);
        int higherDistance = Math.abs(currentFloor - higher);

        if (lowerDistance <= higherDistance) {
            return lower;
        }

        return higher;
    }

    private void completeStopIfNeeded() {
        if (pendingStops.contains(currentFloor)) {
            System.out.println("Elevator " + id + " opened door at floor " + currentFloor);
            pendingStops.remove(currentFloor);
        }
    }

    private void updateDirection() {
        if (pendingStops.isEmpty()) {
            direction = Direction.IDLE;
            return;
        }

        Integer nextStop = nearestStop();

        if (nextStop == null) {
            direction = Direction.IDLE;
        } else if (nextStop > currentFloor) {
            direction = Direction.UP;
        } else if (nextStop < currentFloor) {
            direction = Direction.DOWN;
        } else {
            direction = Direction.IDLE;
        }
    }

    public void showStatus() {
        System.out.println(
                "Elevator " + id +
                " | floor=" + currentFloor +
                " | direction=" + direction +
                " | pendingStops=" + pendingStops
        );
    }
}
