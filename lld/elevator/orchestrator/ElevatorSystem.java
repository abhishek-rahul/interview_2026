package lld.elevator.orchestrator;

import java.util.ArrayList;
import java.util.List;

import lld.elevator.domain.Elevator;
import lld.elevator.domain.enums.Direction;
import lld.elevator.policy.AssignmentPolicy;
import lld.elevator.policy.impl.NearestElevatorPolicy;

public class ElevatorSystem {
    private final int minFloor;
    private final int maxFloor;
    private final List<Elevator> elevators;
    private final AssignmentPolicy assignmentPolicy;

    public ElevatorSystem(int minFloor, int maxFloor, int elevatorCount) {
        this.minFloor = minFloor;
        this.maxFloor = maxFloor;
        this.elevators = new ArrayList<>();
        this.assignmentPolicy = new NearestElevatorPolicy();

        for (int i = 1; i <= elevatorCount; i++) {
            elevators.add(new Elevator(i, minFloor));
        }
    }

    public void requestPickup(int floor, Direction direction) {
        validateFloor(floor);

        if (direction == Direction.IDLE) {
            throw new IllegalArgumentException("Pickup direction cannot be IDLE");
        }

        Elevator selectedElevator = assignmentPolicy.selectElevator(elevators, floor, direction);

        if (selectedElevator == null) {
            throw new IllegalStateException("No elevator available");
        }

        selectedElevator.addStop(floor);

        System.out.println(
                "Pickup requested at floor " + floor +
                " direction " + direction +
                " assigned to elevator " + selectedElevator.getId()
        );
    }

    public void requestDestination(int elevatorId, int destinationFloor) {
        validateFloor(destinationFloor);

        Elevator elevator = findElevator(elevatorId);
        elevator.addStop(destinationFloor);

        System.out.println(
                "Destination floor " + destinationFloor +
                " added to elevator " + elevatorId
        );
    }

    public void step() {
        System.out.println("\n--- System Step ---");

        for (Elevator elevator : elevators) {
            elevator.moveOneStep();
        }

        showStatus();
    }

    public void showStatus() {
        for (Elevator elevator : elevators) {
            elevator.showStatus();
        }
    }

    private Elevator findElevator(int elevatorId) {
        for (Elevator elevator : elevators) {
            if (elevator.getId() == elevatorId) {
                return elevator;
            }
        }

        throw new IllegalArgumentException("Invalid elevator id: " + elevatorId);
    }

    private void validateFloor(int floor) {
        if (floor < minFloor || floor > maxFloor) {
            throw new IllegalArgumentException(
                    "Invalid floor: " + floor +
                    ". Valid range is " + minFloor + " to " + maxFloor
            );
        }
    }
}
