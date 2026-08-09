import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
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

    public void systemStart() {
        recordEvent("SYSTEM START");
    }

    public void systemStop() {
        recordEvent("SYSTEM STOP");
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
        double percent = ((double) occupancyCounter / highOccupancy) * 100;
        if (percent > 100) {
            percent = 100;
        }
        return percent;
    }

    private void recordEvent(String eventType) {
        EventRecord eventRecord = new EventRecord(eventType, LocalDateTime.now(), occupancyCounter);
        eventHistory.add(eventRecord);

        try (PrintWriter printWriter = new PrintWriter(new FileWriter("Event_History.txt", true))){
            printWriter.println(eventRecord.getFormattedRecord());

        } catch (IOException exp) {
            System.out.println("Something went wrong saving to file");
            System.out.println(exp.getMessage());
        }
    }

    public void manualOccupancyCorrection(int newOccupancy) {
        if (newOccupancy >= 0) {
            occupancyCounter = newOccupancy;
            recordEvent("Correction");
        }
    }
}
