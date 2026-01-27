package lld.parkinglot.demo;
import lld.parkinglot.domain.ParkingLot;
import lld.parkinglot.domain.Ticket;
import lld.parkinglot.domain.Vehicle;
import lld.parkinglot.domain.enums.VehicleType;
import lld.parkinglot.exceptions.InvalidTicketException;
import lld.parkinglot.exceptions.NoSpotAvailableException;
import lld.parkinglot.exceptions.TicketAlreadyClosedException;
import lld.parkinglot.policy.FeeCalculationPolicy;
import lld.parkinglot.policy.SpotAllocationPolicy;
import lld.parkinglot.policy.impl.FirstAvailableSpotPolicy;
import lld.parkinglot.policy.impl.HourlyFeePolicy;
import lld.parkinglot.service.ParkingLotService;
import lld.parkinglot.util.FakeClock;
import lld.parkinglot.util.TicketIdGenerator;
import lld.parkinglot.util.UuidTicketIdGenerator;
import lld.parkinglot.policy.RateCard;
public final class ParkingLotDemo {

    public static void main(String[] args) {
        // Arrange
        ParkingLot lot = ParkingLotTestData.createSmallLot();

        SpotAllocationPolicy allocationPolicy = new FirstAvailableSpotPolicy();

        RateCard rateCard = new RateCard(
                10.0,  // BIKE per hour
                20.0,  // CAR per hour
                30.0   // TRUCK per hour
        );
        FeeCalculationPolicy feePolicy = new HourlyFeePolicy(rateCard);

        FakeClock clock = new FakeClock(0L); // start at time=0
        TicketIdGenerator idGen = new UuidTicketIdGenerator();

        ParkingLotService service = new ParkingLotService(lot, allocationPolicy, feePolicy, clock, idGen);

        // ---------- Test 1: Happy path park/unpark CAR ----------
        Vehicle car = new Vehicle("DL-01-1234", VehicleType.CAR);

        Ticket ticket = service.park(car);
        System.out.println("Parked. TicketId=" + ticket.getTicketId() + " Spot=" + ticket.getSpotId());

        TestUtil.assertTrue(service.getActiveTicketCount() == 1, "active ticket count should be 1");

        // advance 90 minutes => ceil(1.5h) = 2 hours, CAR rate = 20 => fee = 40
        clock.advanceMillis(90L * 60L * 1000L);

        double fee = service.unpark(ticket.getTicketId());
        System.out.println("Unparked. Fee=" + fee);

        TestUtil.assertEquals(40.0, fee, 0.0001, "fee should be 40 for 90 mins car");
        TestUtil.assertTrue(service.getActiveTicketCount() == 0, "active ticket count should be 0 after unpark");

        // ---------- Test 2: Invalid ticket ----------
        TestUtil.assertThrows(InvalidTicketException.class,
                () -> service.unpark("UNKNOWN"),
                "unpark should throw InvalidTicketException for unknown ticket");

        // ---------- Test 3: Ticket already closed ----------
        TestUtil.assertThrows(TicketAlreadyClosedException.class,
                () -> service.unpark(ticket.getTicketId()),
                "unpark should throw TicketAlreadyClosedException for closed ticket");

        // ---------- Test 4: Parking full for BIKE (only 1 bike spot) ----------
        Vehicle bike1 = new Vehicle("BIKE-1", VehicleType.BIKE);
        Ticket t1 = service.park(bike1);

        Vehicle bike2 = new Vehicle("BIKE-2", VehicleType.BIKE);
        TestUtil.assertThrows(NoSpotAvailableException.class,
                () -> service.park(bike2),
                "park should throw NoSpotAvailableException when bike spots full");

        // cleanup
        clock.advanceMillis(10L * 60L * 1000L);
        service.unpark(t1.getTicketId());

        System.out.println("ALL TESTS PASSED ✅");
    }
}