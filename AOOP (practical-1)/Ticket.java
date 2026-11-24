package waypoint.model;

import java.time.Instant;
import java.util.Objects;

public final class Ticket {

    // Three final fields
    private final TicketId id;
    private final SpotId spotId;
    private final Instant entry;

    // Exactly two non-final fields
    private Instant exit;
    private Money fare;

    // Private constructor
    private Ticket(TicketId id, SpotId spotId, Instant entry) {

        this.id = Objects.requireNonNull(id, "Ticket ID cannot be null");
        this.spotId = Objects.requireNonNull(spotId, "Spot ID cannot be null");
        this.entry = Objects.requireNonNull(entry, "Entry cannot be null");

        this.exit = null;
        this.fare = null;
    }

    // Static factory method
    public static Ticket open(TicketId id, SpotId spotId, Instant entry) {
        return new Ticket(id, spotId, entry);
    }

    // Check ticket status
    public boolean isOpen() {
        return exit == null;
    }

    // Close ticket
    public Money close(Instant at, FarePolicy policy) {

        Objects.requireNonNull(at, "Exit time cannot be null");
        Objects.requireNonNull(policy, "Fare policy cannot be null");

        if (!isOpen()) {
            throw new TicketAlreadyClosed("Ticket is already closed");
        }

        if (at.isBefore(entry)) {
            throw new IllegalArgumentException(
                    "Exit time cannot be before entry"
            );
        }

        Money calculatedFare = Objects.requireNonNull(
    policy.fareFor(entry, at, spotId),
    "Fare cannot be null"
);
        

        if (calculatedFare.isNegative()) {
            throw new IllegalArgumentException("Fare cannot be negative");
        }

        // Update both fields only after successful validation
        this.exit = at;
        this.fare = calculatedFare;

        return calculatedFare;
    }

    // Getters
    public TicketId getId() {
        return id;
    }

    public SpotId getSpotId() {
        return spotId;
    }

    public Instant getEntry() {
        return entry;
    }

    public Instant getExit() {
        return exit;
    }

    public Money getFare() {
        return fare;
    }
}
class TicketAlreadyClosed extends RuntimeException {
    public TicketAlreadyClosed(String message) {
        super(message);
    }
}