package waypoint.model;

/**
 * VALUE OBJECT.
 * Validates and normalises registration numbers.
 */
public record Registration(String value) {

    // Compact constructor
    public Registration {
        if (value == null) {
            throw new NullPointerException("Registration cannot be null");
        }

        // Remove extra spaces
        value = value.trim();

        // Convert to uppercase
        value = value.toUpperCase();

        // Validate registration format
        // Example: MH12AB1234
        if (!value.matches("^[A-Z]{2}\\d{2}[A-Z]{1,2}\\d{4}$")) {
            throw new IllegalArgumentException(
                    "Invalid registration number: " + value
            );
        }
    }
}
