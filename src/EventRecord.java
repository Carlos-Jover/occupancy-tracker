import java.time.LocalTime;

public class EventRecord {
    private String eventType;
    private LocalTime eventTime;
    private int occupancyAfter;

    public EventRecord(String eventType, LocalTime eventTime, int occupancyAfter) {
        this.eventType = eventType;
        this.eventTime = eventTime;
        this.occupancyAfter = occupancyAfter;
    }

    public String getEventType() {
        return eventType;
    }

    public LocalTime getEventTime() {
        return eventTime;
    }

    public int getOccupancyAfter() {
        return occupancyAfter;
    }

    public String getFormattedRecord() {

    }
}
