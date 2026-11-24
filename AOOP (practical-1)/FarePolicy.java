package waypoint.model;

import java.time.Instant;

@FunctionalInterface
public interface FarePolicy {

    Money fareFor(Instant entry, Instant exit, SpotId spot);

}