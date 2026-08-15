import java.time.LocalTime;

public class OperatingHours {
    private LocalTime openingTime;
    private LocalTime closingTime;

    public OperatingHours() {
        this.openingTime = LocalTime.of(0, 0);
        this.closingTime = LocalTime.of(23, 59);
    }

    public LocalTime getOpeningTime() {
        return openingTime;
    }

    public LocalTime getClosingTime() {
        return closingTime;
    }

    public void setOperatingHours (LocalTime openingTime, LocalTime closingTime) {
        if (openingTime.isAfter(closingTime) || openingTime.equals(closingTime)) {
            throw new IllegalArgumentException("Opening time must come before closing time.");
        } else {
            this.openingTime = openingTime;
            this.closingTime = closingTime;
        }
    }
}
