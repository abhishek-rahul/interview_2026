package lld.elevator.policy;

import java.util.List;
import lld.elevator.domain.Elevator;
import lld.elevator.domain.enums.Direction;

public interface AssignmentPolicy {
    Elevator selectElevator(List<Elevator> elevators, int pickupFloor, Direction requestedDirection);
}
