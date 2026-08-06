import java.time.LocalTime;
import java.util.ArrayList;

public class Tracker {
    private int occupancyCounter;
    private int highOccupancy;
    private ArrayList<EventRecord> eventHistory;

    public Tracker(int highOccupancy) {
        this.occupancyCounter = 0;
        if (highOccupancy > 0) {
            this.highOccupancy = highOccupancy;
        } else {
            throw new IllegalArgumentException("Occupancy can not be 0 or less.");
        }
        eventHistory = new ArrayList<>();
    }

    public int getOccupancyCounter() {
        return occupancyCounter;
    }

    public int getHighOccupancy() {
        return highOccupancy;
    }

    public ArrayList<EventRecord> getEventHistory() {
        return eventHistory;
    }

    public void setHighOccupancy(int highOccupancy) {
        if (highOccupancy > 0) {
            this.highOccupancy = highOccupancy;
        } else {
            throw new IllegalArgumentException("Occupancy can not be 0 or less.");
        }
    }

    public void enter() {
        occupancyCounter++;
        recordEvent("Enter");

    }

    public void exit() {
        if (occupancyCounter > 0) {
            occupancyCounter--;
            recordEvent("Exit");
        }
    }

    public void resetCounter() {
        occupancyCounter = 0;
        recordEvent("Counter reset");
    }

    public double getOccupancyPercentage() {
        return ((double) occupancyCounter / highOccupancy) * 100;
    }

    private void recordEvent(String eventType) {
        EventRecord eventRecord = new EventRecord(eventType, LocalTime.now(), occupancyCounter);
        eventHistory.add(eventRecord);
    }
}
