import java.util.ArrayList;

public class Tracker {
    private int occupancyCounter;
    private int highOccupancy;
    private ArrayList<EventRecord> eventHistory;

    public Tracker(int highOccupancy) {
        this.occupancyCounter = 0;
        this.highOccupancy = highOccupancy;
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
        this.highOccupancy = highOccupancy;
    }

    public void enter() {

    }

    public void exit() {

    }

    public void resetCounter() {

    }

    public double getCounterPercentage() {

    }
}
