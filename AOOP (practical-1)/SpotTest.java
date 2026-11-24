package waypoint.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpotTest {

    @Test
    void testSpotCreation() {

        SpotId id = new SpotId("S001");

        Spot spot = new Spot(id);

        assertEquals(id, spot.getId());
        assertTrue(spot.isFree());
        assertNull(spot.getOccupant());
    }

    @Test
    void testAssignVehicle() {

        Spot spot = new Spot(new SpotId("S002"));

        VehicleId vehicle = new VehicleId("V001");

        assertTrue(spot.tryAssign(vehicle));

        assertFalse(spot.isFree());
        assertEquals(vehicle, spot.getOccupant());
    }

    @Test
    void testCannotAssignWhenOccupied() {

        Spot spot = new Spot(new SpotId("S003"));

        VehicleId vehicle1 = new VehicleId("V001");
        VehicleId vehicle2 = new VehicleId("V002");

        assertTrue(spot.tryAssign(vehicle1));

        assertFalse(spot.tryAssign(vehicle2));

        assertEquals(vehicle1, spot.getOccupant());
    }

    @Test
    void testReleaseVehicle() {

        Spot spot = new Spot(new SpotId("S004"));

        VehicleId vehicle = new VehicleId("V001");

        spot.tryAssign(vehicle);
        spot.release(vehicle);

        assertTrue(spot.isFree());
        assertNull(spot.getOccupant());
    }

    @Test
    void testWrongVehicleCannotRelease() {

        Spot spot = new Spot(new SpotId("S005"));

        VehicleId vehicle1 = new VehicleId("V001");
        VehicleId vehicle2 = new VehicleId("V002");

        spot.tryAssign(vehicle1);
        spot.release(vehicle2);

        assertEquals(vehicle1, spot.getOccupant());
        assertFalse(spot.isFree());
    }

    @Test
    void testSpotEquality() {

        Spot spot1 = new Spot(new SpotId("S006"));
        Spot spot2 = new Spot(new SpotId("S006"));

        assertEquals(spot1, spot2);
        assertEquals(spot1.hashCode(), spot2.hashCode());
    }

    @Test
    void testDifferentSpotEquality() {

        Spot spot1 = new Spot(new SpotId("S007"));
        Spot spot2 = new Spot(new SpotId("S008"));

        assertNotEquals(spot1, spot2);
    }

    @Test
    void testAssignNullVehicle() {

        Spot spot = new Spot(new SpotId("S009"));

        assertThrows(NullPointerException.class,
                () -> spot.tryAssign(null));
    }
}