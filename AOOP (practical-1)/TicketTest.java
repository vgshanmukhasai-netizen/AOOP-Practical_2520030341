package waypoint.model;

import org.junit.jupiter.api.Test;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class TicketTest {

    @Test
    void testTicketCreation() {

        TicketId ticketId = new TicketId("T001");
        SpotId spotId = new SpotId("S001");

        Instant entry = Instant.parse("2026-11-24T10:00:00Z");

        Ticket ticket = Ticket.open(ticketId, spotId, entry);

        assertNotNull(ticket);
        assertTrue(ticket.isOpen());
        assertEquals(ticketId, ticket.getId());
        assertEquals(spotId, ticket.getSpotId());
        assertEquals(entry, ticket.getEntry());
    }

    @Test
    void testTicketInitiallyOpen() {

        Ticket ticket = Ticket.open(
                new TicketId("T002"),
                new SpotId("S002"),
                Instant.now()
        );

        assertTrue(ticket.isOpen());
        assertNull(ticket.getExit());
        assertNull(ticket.getFare());
    }

    @Test
    void testCloseTicket() {

        Instant entry = Instant.parse("2026-11-24T10:00:00Z");
        Instant exit = Instant.parse("2026-11-24T12:00:00Z");

        Ticket ticket = Ticket.open(
                new TicketId("T003"),
                new SpotId("S003"),
                entry
        );

        FarePolicy policy = (start, end, spot) -> Money.rupees(100);

        Money fare = ticket.close(exit, policy);

        assertEquals(Money.rupees(100), fare);
        assertFalse(ticket.isOpen());
        assertEquals(exit, ticket.getExit());
        assertEquals(fare, ticket.getFare());
    }

    @Test
    void testCannotCloseTwice() {

        Instant entry = Instant.parse("2026-11-24T10:00:00Z");
        Instant exit = Instant.parse("2026-11-24T12:00:00Z");

        Ticket ticket = Ticket.open(
                new TicketId("T004"),
                new SpotId("S004"),
                entry
        );

        FarePolicy policy = (start, end, spot) -> Money.rupees(50);

        ticket.close(exit, policy);

        assertThrows(TicketAlreadyClosed.class,
                () -> ticket.close(exit, policy));
    }

    @Test
    void testExitBeforeEntry() {

        Instant entry = Instant.parse("2026-11-24T12:00:00Z");
        Instant exit = Instant.parse("2026-11-24T10:00:00Z");

        Ticket ticket = Ticket.open(
                new TicketId("T005"),
                new SpotId("S005"),
                entry
        );

        FarePolicy policy = (start, end, spot) -> Money.rupees(100);

        assertThrows(IllegalArgumentException.class,
                () -> ticket.close(exit, policy));

        assertTrue(ticket.isOpen());
    }

    @Test
    void testNegativeFareRejected() {

        Instant entry = Instant.parse("2026-11-24T10:00:00Z");
        Instant exit = Instant.parse("2026-11-24T12:00:00Z");

        Ticket ticket = Ticket.open(
                new TicketId("T006"),
                new SpotId("S006"),
                entry
        );

        FarePolicy policy = (start, end, spot) ->
                Money.ofMinor(-100, java.util.Currency.getInstance("INR"));

        assertThrows(IllegalArgumentException.class,
                () -> ticket.close(exit, policy));

        assertTrue(ticket.isOpen());
    }
}