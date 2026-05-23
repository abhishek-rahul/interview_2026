package lld.elevator.policy.impl;

import java.util.List;

import lld.elevator.domain.Elevator;
import lld.elevator.domain.enums.Direction;
import lld.elevator.policy.AssignmentPolicy;

public class NearestElevatorPolicy implements AssignmentPolicy {

    @Override
    public Elevator selectElevator(List<Elevator> elevators, int pickupFloor, Direction requestedDirection) {
        Elevator bestElevator = null;
        int bestScore = Integer.MAX_VALUE;

        for (Elevator elevator : elevators) {
            int score = calculateScore(elevator, pickupFloor, requestedDirection);

            if (score < bestScore) {
                bestScore = score;
                bestElevator = elevator;
            }
        }

        return bestElevator;
    }

    private int calculateScore(Elevator elevator, int pickupFloor, Direction requestedDirection) {
        int distance = Math.abs(elevator.getCurrentFloor() - pickupFloor);

        if (elevator.isIdle()) {
            return distance;
        }

        boolean sameDirection = elevator.getDirection() == requestedDirection;

        boolean canServeOnWay =
                requestedDirection == Direction.UP &&
                elevator.getDirection() == Direction.UP &&
                elevator.getCurrentFloor() <= pickupFloor;

        boolean canServeOnWayDown =
                requestedDirection == Direction.DOWN &&
                elevator.getDirection() == Direction.DOWN &&
                elevator.getCurrentFloor() >= pickupFloor;

        if (sameDirection && (canServeOnWay || canServeOnWayDown)) {
            return distance;
        }

        return distance + 100 + elevator.pendingCount();
    }
}
