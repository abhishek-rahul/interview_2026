# Parking Lot - Sequence Diagrams

## park() Method Sequence Diagram

```mermaid
sequenceDiagram
    participant Client
    participant Service as ParkingLotService
    participant Policy as SpotAllocationPolicy
    participant Lot as ParkingLot
    participant Floor as ParkingFloor
    participant Spot as ParkingSpot
    participant IdGen as TicketIdGenerator
    participant Clock
    participant Ticket
    participant ActiveTickets

    Client->>Service: park(vehicle)
    
    alt vehicle is null
        Service-->>Client: throw IllegalArgumentException
    else vehicle is valid
        Service->>Policy: findSpot(parkingLot, vehicle)
        
        Policy->>Lot: getFloors()
        Lot-->>Policy: List<ParkingFloor>
        
        loop for each floor
            Policy->>Floor: getSpots()
            Floor-->>Policy: List<ParkingSpot>
            
            loop for each spot
                Policy->>Spot: isFree()
                Spot-->>Policy: boolean
                Policy->>Spot: getSpotType()
                Spot-->>Policy: VehicleType
            end
        end
        
        alt spot found
            Policy-->>Service: ParkingSpot
            
            Service->>Spot: occupy(vehicle)
            Note over Spot: Validate vehicle, occupied status, type match
            Note over Spot: Set parkedVehicle = vehicle<br/>Set occupied = true
            Spot-->>Service: void
            
            Service->>IdGen: nextId()
            IdGen-->>Service: ticketId
            
            Service->>Clock: nowMillis()
            Clock-->>Service: entryTime
            
            Service->>Spot: getSpotId()
            Spot-->>Service: spotId
            
            Service->>Ticket: new Ticket(...)
            Note over Ticket: Initialize with ticketId, vehicleNumber,<br/>vehicleType, spotId, entryTime<br/>Set status = ACTIVE
            Ticket-->>Service: Ticket instance
            
            Service->>ActiveTickets: put(ticketId, ticket)
            ActiveTickets-->>Service: void
            
            Service-->>Client: Ticket
        else no spot available
            Policy-->>Service: null
            Service-->>Client: throw NoSpotAvailableException
        end
    end
```

## unpark() Method Sequence Diagram

```mermaid
sequenceDiagram
    participant Client
    participant Service as ParkingLotService
    participant ActiveTickets
    participant ClosedTickets
    participant Clock
    participant FeePolicy as FeeCalculationPolicy
    participant Ticket
    participant Lot as ParkingLot
    participant Spot as ParkingSpot

    Client->>Service: unpark(ticketId)
    
    alt ticketId is invalid
        Service-->>Client: throw IllegalArgumentException
    else ticketId is valid
        Service->>ActiveTickets: get(ticketId)
        ActiveTickets-->>Service: Ticket or null
        
        alt ticket not found in activeTickets
            Service->>ClosedTickets: containsKey(ticketId)
            ClosedTickets-->>Service: boolean
            
            alt ticket in closedTickets
                Service-->>Client: throw TicketAlreadyClosedException
            else ticket not found anywhere
                Service-->>Client: throw InvalidTicketException
            end
        else ticket found
            Service->>Clock: nowMillis()
            Clock-->>Service: exitTime
            
            Service->>FeePolicy: calculateFee(ticket, exitTime)
            FeePolicy->>Ticket: getEntryTimeMillis()
            Ticket-->>FeePolicy: entryTimeMillis
            Note over FeePolicy: Calculate duration<br/>Calculate hours (ceil)<br/>Get rate from rateCard<br/>fee = hours * rate
            FeePolicy-->>Service: fee
            
            Service->>Ticket: getSpotId()
            Ticket-->>Service: spotId
            
            Service->>Lot: getSpotById(spotId)
            Lot-->>Service: ParkingSpot
            
            Service->>Spot: vacate()
            Note over Spot: Validate occupied == true<br/>Set parkedVehicle = null<br/>Set occupied = false
            Spot-->>Service: void
            
            Service->>Ticket: close(exitTime, fee)
            Note over Ticket: Validate status, exitTime, fee<br/>Set exitTimeMillis, feePaid<br/>Set status = CLOSED
            Ticket-->>Service: void
            
            Service->>ActiveTickets: remove(ticketId)
            ActiveTickets-->>Service: void
            
            Service->>ClosedTickets: put(ticketId, ticket)
            ClosedTickets-->>Service: void
            
            Service-->>Client: fee (double)
        end
    end
```

## Key Interactions Summary

### park() Flow:
1. **Validation**: Validate vehicle input
2. **Spot Allocation**: Use allocation policy to find available spot
3. **Spot Occupation**: Occupy the found spot with vehicle
4. **Ticket Creation**: Generate ticket ID, get entry time, create ticket
5. **Registration**: Store ticket in active tickets registry
6. **Return**: Return ticket to client

### unpark() Flow:
1. **Validation**: Validate ticket ID input
2. **Ticket Lookup**: Find ticket in active tickets registry
3. **Fee Calculation**: Calculate parking fee based on duration
4. **Spot Vacating**: Free the parking spot
5. **Ticket Closure**: Close the ticket with exit time and fee
6. **Registry Update**: Move ticket from active to closed registry
7. **Return**: Return calculated fee to client
