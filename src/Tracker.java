import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Tracker {
    private int occupancyCounter;
    private int highOccupancy;
    private ArrayList<EventRecord> eventHistory;
    private int peakOccupancy;
    private LocalDateTime peakOccupancyDateTime;
    private ArrayList<EventRecord> eventHistoryTotalData;

    public Tracker(int highOccupancy) {
        this.occupancyCounter = 0;
        if (highOccupancy > 0) {
            this.highOccupancy = highOccupancy;
        } else {
            throw new IllegalArgumentException("Occupancy can not be 0 or less.");
        }
        eventHistory = new ArrayList<>();
        eventHistoryTotalData = new ArrayList<>();
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

    public int getPeakOccupancy() {
        return peakOccupancy;
    }

    public LocalDateTime getPeakOccupancyDateTime() {
        return peakOccupancyDateTime;
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
        updatePeakOccupancy();

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

        try (PrintWriter printWriter = new PrintWriter(new FileWriter("Event_History.txt", true))) {
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
            updatePeakOccupancy();
        }
    }

    public boolean restoreOccupancyOnStartUp() {
        try (Scanner reader = new Scanner(new File("Event_History.txt"))) {
            String line = "";
            while (reader.hasNextLine()) {
                String currentLine = reader.nextLine();

                if (!currentLine.isEmpty()) {
                    line = currentLine;
                }
            }

            if (line.isEmpty()) {
                return false;
            }

            String[] splitLine = line.split("\\|");
            String eventType = splitLine[0].trim();


            if (eventType.equals("SYSTEM STOP")) {
                occupancyCounter = 0;
                return false;
            } else {
                String occupancyLine = splitLine[2].trim();
                String[] occupancyLineSplit = occupancyLine.split(" ");
                String occupancyText = occupancyLineSplit[1].trim();

                occupancyCounter = Integer.parseInt(occupancyText);
                recordEvent("SYSTEM RESTORED");
                updatePeakOccupancy();
                return true;
            }
        } catch (FileNotFoundException exp) {
            return false;
        }
    }

    private void updatePeakOccupancy() {
        if (occupancyCounter > peakOccupancy) {
            peakOccupancy = occupancyCounter;
            peakOccupancyDateTime = LocalDateTime.now();
        }
    }

    public void runTestSimulation(int numberOfAttempts, int minTime, int maxTime) {
        try {
            Random random = new Random();
            int n = 0;

            while (n != numberOfAttempts){
                int time = random.nextInt(minTime, maxTime);
                Thread.sleep(time);

                int randomInt = random.nextInt(2);
                if (randomInt == 0) {
                    enter();
                } else {
                    exit();
                }

                n++;
            }
        } catch (InterruptedException exp ){
            System.out.println(exp.getMessage());
        }
    }

    public void loadEventHistoryData() {
        try (Scanner reader = new Scanner(new File("Event_History.txt"))) {
            eventHistoryTotalData.clear();

            while (reader.hasNextLine()) {
                String currentLine = reader.nextLine();
                if (!currentLine.isEmpty()) {
                    eventHistoryTotalData.add(EventRecord.parse(currentLine));
                }
            }

        } catch (FileNotFoundException exp) {
            System.out.println(exp.getMessage());
        }
    }

    public ArrayList<EventRecord> getEventsForDate(LocalDate date) {
        ArrayList<EventRecord> matchingEvents = new ArrayList<>();

        for (EventRecord eventRecord : eventHistoryTotalData) {
            if (eventRecord.getEventDateTime().toLocalDate().equals(date)) {
                matchingEvents.add(eventRecord);
            }
        }

        return matchingEvents;
    }
}
