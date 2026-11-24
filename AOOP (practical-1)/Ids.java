
package waypoint.model;

import java.util.Objects;

/**
 * Distinct ID types so they can never be swapped at a call site.
 */

record TicketId(String value) {

    public TicketId {
        Objects.requireNonNull(value, "Ticket ID cannot be null");

        if (value.isBlank()) {
            throw new IllegalArgumentException("Ticket ID cannot be blank");
        }
    }
}

record SpotId(String value) {

    public SpotId {
        Objects.requireNonNull(value, "Spot ID cannot be null");

        if (value.isBlank()) {
            throw new IllegalArgumentException("Spot ID cannot be blank");
        }
    }
}
record VehicleId(String value) {
    public VehicleId {
        java.util.Objects.requireNonNull(value);

        if (value.isBlank()) {
            throw new IllegalArgumentException("Vehicle ID cannot be blank");
        }
    }
}