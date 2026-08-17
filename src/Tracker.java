import java.io.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class Tracker {
    private int occupancyCounter;
    private int highOccupancy;
    private ArrayList<EventRecord> eventHistory;
    private int peakOccupancy;
    private LocalDateTime peakOccupancyDateTime;

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

    public void timeBetweenEvents(OperatingHours operatingHours) {
        long totalWeightedOccupancy = 0;
        long totalSeconds = 0;

        LocalDateTime event = eventHistory.getFirst().getEventDateTime();

        LocalDate eventDate = event.toLocalDate();

        LocalTime openingTime = operatingHours.getOpeningTime();
        LocalTime closingTime = operatingHours.getClosingTime();

        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");

        LocalDateTime openingDateTime = LocalDateTime.of(eventDate, openingTime);
        LocalDateTime closingDateTime = LocalDateTime.of(eventDate, closingTime);

        System.out.println();
        System.out.println("Opening Date: " + eventDate);
        System.out.println("Opening Time: " + openingTime);
        System.out.println("Opening Hour: " + openingDateTime.format(format));
        System.out.println("Closing Date: " + eventDate);
        System.out.println("Closing Time: " + closingTime);
        System.out.println("Closing Hour: " + closingDateTime.format(format));
        System.out.println();

        for (int i = 0; i < eventHistory.size() - 1; i++) {
            LocalDateTime firstTime = eventHistory.get(i).getEventDateTime();
            LocalDateTime secondTime = eventHistory.get(i + 1).getEventDateTime();

            if (firstTime.isBefore(openingDateTime)) {
                firstTime = openingDateTime;
            }

            if (secondTime.isAfter(closingDateTime)) {
                secondTime = closingDateTime;
            }

            if (!firstTime.isBefore(secondTime)) {
                continue;
            }

            Duration d = Duration.between(firstTime, secondTime);

            long seconds = d.toSeconds();
            totalSeconds += seconds;

            int occupancyAfter = eventHistory.get(i).getOccupancyAfter();

            long weightedOccupancy = occupancyAfter * seconds;
            totalWeightedOccupancy += weightedOccupancy;

            System.out.println("Occupancy "
                    + eventHistory.get(i).getOccupancyAfter()
                    + " lasted "
                    + seconds
                    + " seconds. " + occupancyAfter + " people * " + seconds + " = " + weightedOccupancy);

        }


        LocalDateTime lastEventTime = eventHistory.getLast().getEventDateTime();
        LocalDateTime currentTime = LocalDateTime.now();

        if (lastEventTime.isBefore(openingDateTime)) {
            lastEventTime = openingDateTime;
        }

        if (currentTime.isAfter(closingDateTime)) {
            currentTime = closingDateTime;
        }

        if (lastEventTime.isBefore(currentTime)) {
            Duration d = Duration.between(lastEventTime, currentTime);

            long seconds = d.toSeconds();
            totalSeconds += seconds;

            int occupancyAfter = eventHistory.getLast().getOccupancyAfter();

            long weightedOccupancy = occupancyAfter * seconds;
            totalWeightedOccupancy += weightedOccupancy;
        }

        if (totalSeconds == 0) {
            System.out.println("Not enough elapsed time to calculate average occupancy.");
        } else {
            double average = (double) totalWeightedOccupancy / totalSeconds;

            System.out.println("Total weighted occupancy: " + totalWeightedOccupancy + " person-seconds");
            System.out.println("Total time: " + totalSeconds + " seconds");
            System.out.printf("Average occupancy: %.2f", average);
        }
    }

    public double getAverageOccupancy(OperatingHours operatingHours) {
        long totalWeightedOccupancy = 0;
        long totalSeconds = 0;

        LocalDateTime event = eventHistory.getFirst().getEventDateTime();

        LocalDate eventDate = event.toLocalDate();

        LocalTime openingTime = operatingHours.getOpeningTime();
        LocalTime closingTime = operatingHours.getClosingTime();

        LocalDateTime openingDateTime = LocalDateTime.of(eventDate, openingTime);
        LocalDateTime closingDateTime = LocalDateTime.of(eventDate, closingTime);

        for (int i = 0; i < eventHistory.size() - 1; i++) {
            LocalDateTime firstTime = eventHistory.get(i).getEventDateTime();
            LocalDateTime secondTime = eventHistory.get(i + 1).getEventDateTime();

            if (firstTime.isBefore(openingDateTime)) {
                firstTime = openingDateTime;
            }

            if (secondTime.isAfter(closingDateTime)) {
                secondTime = closingDateTime;
            }

            if (!firstTime.isBefore(secondTime)) {
                continue;
            }

            Duration d = Duration.between(firstTime, secondTime);

            long seconds = d.toSeconds();
            totalSeconds += seconds;

            int occupancyAfter = eventHistory.get(i).getOccupancyAfter();

            long weightedOccupancy = occupancyAfter * seconds;
            totalWeightedOccupancy += weightedOccupancy;
        }


        LocalDateTime lastEventTime = eventHistory.getLast().getEventDateTime();
        LocalDateTime currentTime = LocalDateTime.now();

        if (lastEventTime.isBefore(openingDateTime)) {
            lastEventTime = openingDateTime;
        }

        if (currentTime.isAfter(closingDateTime)) {
            currentTime = closingDateTime;
        }

        if (lastEventTime.isBefore(currentTime)) {
            Duration d = Duration.between(lastEventTime, currentTime);

            long seconds = d.toSeconds();
            totalSeconds += seconds;

            int occupancyAfter = eventHistory.getLast().getOccupancyAfter();

            long weightedOccupancy = occupancyAfter * seconds;
            totalWeightedOccupancy += weightedOccupancy;
        }

        if (totalSeconds == 0) {
            return -1;
        } else {
            return (double) totalWeightedOccupancy / totalSeconds;
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
}
