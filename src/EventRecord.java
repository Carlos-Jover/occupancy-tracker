import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventRecord {
    private String eventType;
    private LocalDateTime eventDateTime;
    private int occupancyAfter;

    public EventRecord(String eventType, LocalDateTime eventDateTime, int occupancyAfter) {
        this.eventType = eventType;
        this.eventDateTime = eventDateTime;
        this.occupancyAfter = occupancyAfter;
    }

    public String getEventType() {
        return eventType;
    }

    public LocalDateTime getEventDateTime() { return eventDateTime; }

    public int getOccupancyAfter() {
        return occupancyAfter;
    }

    public String getFormattedRecord() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");
        String formattedTime = eventDateTime.format(format);
        return String.format("%-15s | %s | Occupancy: %d", eventType, formattedTime, occupancyAfter);
    }

    public static EventRecord parse(String line) {
        String[] eventRecordLine = line.split("\\|");

        String eventType = eventRecordLine[0].trim();
        String eventDateTimeText = eventRecordLine[1].trim();
        String[] occupancyText = eventRecordLine[2].trim().split(" ");

        int occupancy = Integer.parseInt(occupancyText[1].trim());

        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");
        LocalDateTime eventDateTime = LocalDateTime.parse(eventDateTimeText, format);

        EventRecord event = new EventRecord(eventType, eventDateTime, occupancy);
        return event;
    }
}
