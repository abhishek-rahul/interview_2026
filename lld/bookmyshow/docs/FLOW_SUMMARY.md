# BookMyShow – Flow Summary

## What the system does

It’s a small **movie ticket booking** system: users search shows, see seats, lock seats, confirm a booking, or cancel it.

---

## Main pieces

| Layer | What it does |
|-------|----------------|
| **Domain** | Core data: Movie, Show, Seat, SeatState, Booking, LockToken |
| **BmsCatalog** | Central store for movies, shows, lock tokens, and bookings |
| **BookMyShowService** | Main API: list shows, view seats, lock, confirm, cancel |
| **Policies** | Rules for locking seats and when locks expire |
| **Util** | ID generation for lock tokens and bookings |

---

## End‑to‑end flow

### 1. Setup (demo)

- **Movies** are added (e.g. Interstellar, 3 Idiots).
- **Shows** are added per city/theatre/screen/time and linked to a movie.
- Each **Show** has a list of **Seats** (e.g. A1–A5). For each seat, a **SeatState** is created (initially AVAILABLE).
- **BookMyShowService** is created with the catalog and two policies: seat allocation and seat lock.

### 2. List shows

- User gives: **city**, **movieId**, **date**.
- Service asks the catalog: “Which shows match city + movie + date?”
- Returns a list of show lines (show id, movie title, theatre, screen, time).

### 3. View seats

- User gives: **showId**, **current time**.
- Service finds the show and **cleans expired locks** (locks past their expiry are removed; those seats become AVAILABLE again).
- For each seat in the show, it reads **SeatState** (status, lock owner, lock expiry, booking id) and returns a line per seat.

### 4. Lock seats

- User gives: **showId**, **userId**, **list of seat ids** (e.g. A1, A2), **current time**.
- Service:
  - Cleans expired locks for that show.
  - **Validates** that all requested seat ids exist in the show.
  - Asks lock policy for **lock expiry** (e.g. now + 2 minutes).
  - **All-or-nothing lock**: either every requested seat is AVAILABLE and gets locked, or the whole request fails (e.g. if A2 is already locked by someone else).
- On success: each of those seats gets status **LOCKED**, lock owner = userId, lock expiry set. A **LockToken** is created (id, showId, userId, seatIds, expiry) and stored in the catalog. The **token id** is returned to the user.

### 5. Confirm booking

- User gives: **lock token id**, **userId**, **current time**.
- Service checks: token exists, token belongs to this user, show exists, and (after cleaning expired locks) **all token seats are still LOCKED by this user and not expired**.
- Then:
  - Each of those seats: status → **BOOKED**, bookingId set, lock fields cleared.
  - A **Booking** is created (id, showId, userId, seatIds, CONFIRMED) and stored.
  - Lock token is **removed** (one-time use).
- Returns the **booking id**.

### 6. Cancel booking

- User gives: **bookingId**, **userId**, **current time**.
- Service checks: booking exists and belongs to this user. Booking is marked **CANCELLED** (double cancel is rejected).
- For each seat in the booking: its **SeatState** is reset to **AVAILABLE** and lock/booking fields are cleared.
- Returns success.

---

## Seat lifecycle (simple)

```
AVAILABLE  →  (user locks)  →  LOCKED  →  (user confirms)  →  BOOKED
     ↑                            ↑                              ↑
     |                            |                              |
  (initial /   (expired lock      |                    (cancel → back to
   after       cleaned up)       |                     AVAILABLE)
   cancel)
```

- **AVAILABLE**: free to lock.
- **LOCKED**: temporarily held by one user until expiry or confirm; another user cannot lock the same seat.
- **BOOKED**: confirmed; only cancel can free the seat.

---

## Policies (in simple terms)

- **SeatAllocationPolicy** (e.g. `SimpleSeatAllocationPolicy`):
  - Checks seat ids are valid for the show.
  - Locks only if all requested seats are AVAILABLE (all-or-nothing).
  - At confirm time, checks all seats are still LOCKED by that user and not expired.

- **SeatLockPolicy** (e.g. `SimpleSeatLockPolicy`):
  - Decides lock expiry (e.g. now + 2 minutes).
  - **Cleanup**: for a show, any LOCKED seat whose expiry ≤ now is set back to AVAILABLE and lock fields cleared.
  - Used before “view seats”, “lock”, and “confirm” so users always see and use up-to-date lock state.

---

## Data flow (one-line view)

**Catalog** holds: Movies, Shows (each show has Seats + SeatState per seat), LockTokens, Bookings.
**Service** uses catalog + policies + id generator to implement: **list shows → view seats → lock seats (get token) → confirm booking (use token, get booking id) → optional cancel**.

That’s the full flow of the BookMyShow folder in simple terms.
