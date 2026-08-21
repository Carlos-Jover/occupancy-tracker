import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class OccupancyAnalytics {
    private ArrayList<EventRecord> events;

    public OccupancyAnalytics(ArrayList<EventRecord> events) {
        this.events = events;
    }

    public ArrayList<EventRecord> getEvents() {
        return events;
    }

    public double getAverageOccupancy(OperatingHours operatingHours) {
        if (events.isEmpty()) {
            return -1;
        }

        long totalWeightedOccupancy = 0;
        long totalSeconds = 0;

        LocalDateTime firstEventTime = events.getFirst().getEventDateTime();

        LocalDate selectedDate = firstEventTime.toLocalDate();

        LocalTime openingTime = operatingHours.getOpeningTime();
        LocalTime closingTime = operatingHours.getClosingTime();

        LocalDateTime openingDateTime = LocalDateTime.of(selectedDate, openingTime);
        LocalDateTime closingDateTime = LocalDateTime.of(selectedDate, closingTime);

        if (firstEventTime.isAfter(openingDateTime) && firstEventTime.isBefore(closingDateTime)) {
            Duration firstEventDuration = Duration.between(openingDateTime, firstEventTime);
            long secondsOfFirstEvent = firstEventDuration.toSeconds();
            totalSeconds += secondsOfFirstEvent;

        } else if (firstEventTime.isAfter(closingDateTime) || firstEventTime.equals(closingDateTime)) {
            Duration duration = Duration.between(openingDateTime, closingDateTime);
            totalSeconds += duration.toSeconds();
        }

        for (int i = 0; i < events.size() - 1; i++) {
            LocalDateTime intervalStart = events.get(i).getEventDateTime();
            LocalDateTime intervalEnd = events.get(i + 1).getEventDateTime();

            if (intervalStart.isBefore(openingDateTime)) {
                intervalStart = openingDateTime;
            }

            if (intervalEnd.isAfter(closingDateTime)) {
                intervalEnd = closingDateTime;
            }

            if (!intervalStart.isBefore(intervalEnd)) {
                continue;
            }

            Duration intervalDuration = Duration.between(intervalStart, intervalEnd);

            long intervalSeconds = intervalDuration.toSeconds();
            totalSeconds += intervalSeconds;

            int occupancyAfter = events.get(i).getOccupancyAfter();

            long weightedOccupancy = occupancyAfter * intervalSeconds;
            totalWeightedOccupancy += weightedOccupancy;
        }

        LocalDateTime lastEventTime = events.getLast().getEventDateTime();

        if (lastEventTime.isBefore(openingDateTime)) {
            Duration duration = Duration.between(openingDateTime, closingDateTime);

            long seconds = duration.toSeconds();

            totalSeconds += seconds;

            int occupancyAfter = events.getLast().getOccupancyAfter();

            long weightedOccupancy = occupancyAfter * seconds;
            totalWeightedOccupancy += weightedOccupancy;

        } else if (lastEventTime.isBefore(closingDateTime)) {
            Duration duration = Duration.between(lastEventTime, closingDateTime);

            long seconds = duration.toSeconds();
            totalSeconds += seconds;

            int occupancyAfter = events.getLast().getOccupancyAfter();
            totalWeightedOccupancy += occupancyAfter * seconds;
        }

        if (totalSeconds == 0) {
            return -1;
        } else {
            return (double) totalWeightedOccupancy / totalSeconds;
        }
    }

    public int getPeakOccupancy() {
        if (events.isEmpty()) {
            return -1;
        }

        int peakOccupancy = 0;

        for (EventRecord event : events) {
            if (event.getOccupancyAfter() > peakOccupancy) {
                peakOccupancy = event.getOccupancyAfter();
            }
        }

        return peakOccupancy;
    }
}
