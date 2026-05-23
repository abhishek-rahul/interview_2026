package lld.elevator.demo;


import lld.elevator.domain.enums.Direction;
import lld.elevator.orchestrator.ElevatorSystem;


public class MainDemo {
    public static void main(String[] args) {
        ElevatorSystem system = new ElevatorSystem(0, 10, 3);

        System.out.println("Initial Status:");
        system.showStatus();

        System.out.println("\nRahul requests pickup from floor 3 going UP");
        system.requestPickup(3, Direction.UP);

        system.step();
        system.step();
        system.step();

        System.out.println("\nRahul enters elevator 1 and selects floor 8");
        system.requestDestination(1, 8);

        system.step();
        system.step();
        system.step();
        system.step();
        system.step();

        System.out.println("\nFailure Case: Invalid floor");
        try {
            system.requestPickup(15, Direction.DOWN);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}