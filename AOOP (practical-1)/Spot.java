package waypoint.model;

import java.util.Objects;

/**
 * ENTITY.
 * Identity = id.
 * Invariant: a spot cannot be assigned while occupied.
 */
public final class Spot {

    private final SpotId id;
    private VehicleId occupant;  // The ONLY mutable field

    // Constructor
    public Spot(SpotId id) {
        this.id = Objects.requireNonNull(id, "Spot ID cannot be null");
        this.occupant = null;
    }

    // Assign vehicle only if spot is free
    public boolean tryAssign(VehicleId v) {

        Objects.requireNonNull(v, "Vehicle ID cannot be null");

        if (occupant != null) {
            return false;
        }

        occupant = v;
        return true;
    }

    // Release vehicle
    public void release(VehicleId v) {

        Objects.requireNonNull(v, "Vehicle ID cannot be null");

        if (v.equals(occupant)) {
            occupant = null;
        }
    }

    // Check whether spot is free
    public boolean isFree() {
        return occupant == null;
    }

    // Get spot ID
    public SpotId getId() {
        return id;
    }

    // Get current occupant
    public VehicleId getOccupant() {
        return occupant;
    }

    // Entity equality based ONLY on ID
    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Spot)) {
            return false;
        }

        Spot other = (Spot) obj;

        return id.equals(other.id);
    }

    // Hash code based ONLY on ID
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
