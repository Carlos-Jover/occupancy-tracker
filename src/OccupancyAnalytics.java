import java.time.*;
import java.util.ArrayList;
import java.util.EnumMap;

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

    public BusiestDayResult getBusiestDayOfWeek() {
        DayOfWeek dayOfWeek;
        int entries = 0;
        int exits = 0;
        int totalTraffic = 0;

        EnumMap<DayOfWeek, Integer> amountDaysOfWeekMap = new EnumMap<>(DayOfWeek.class);
        EnumMap<DayOfWeek, Integer> totalTrafficMap = new EnumMap<>(DayOfWeek.class);


        for (int i = 0; i < events.size(); i++) {
            LocalDateTime eventDate = events.get(i).getEventDateTime();

            LocalDateTime nextEventDate;

            if (!events.get(i).equals(events.getLast())) {
                nextEventDate = events.get(i+1).getEventDateTime();
            } else {
                nextEventDate = eventDate;
            }


            LocalDate date = eventDate.toLocalDate();
            LocalDate nextDate = nextEventDate.toLocalDate();

            dayOfWeek = date.getDayOfWeek();

            if (events.get(i).getEventType().equals("Enter")) {
                entries++;
            } else if (events.get(i).getEventType().equals("Exit")) {
                exits++;
            }

            if (!date.equals(nextDate) || events.get(i).equals(events.getLast())) {
                int currentAmount = amountDaysOfWeekMap.getOrDefault(dayOfWeek, 0);
                currentAmount++;
                amountDaysOfWeekMap.put(dayOfWeek, currentAmount);

                totalTraffic = entries + exits;
                int currentTraffic = totalTrafficMap.getOrDefault(dayOfWeek, 0);
                currentTraffic += totalTraffic;
                totalTrafficMap.put(dayOfWeek, currentTraffic);

                entries = 0;
                exits = 0;
            }
        }

        double average = 0;
        ArrayList<DayOfWeek> busiestDay = new ArrayList<>();
        for (DayOfWeek currentDay : DayOfWeek.values()) {
            int amountOfDays = amountDaysOfWeekMap.getOrDefault(currentDay, 0);
            int traffic = totalTrafficMap.getOrDefault(currentDay, 0);

            if (amountOfDays != 0) {
                double checkAverage = (double) traffic / amountOfDays;

                if (checkAverage > average) {
                    average = checkAverage;
                    busiestDay.clear();
                    busiestDay.add(currentDay);

                } else if (checkAverage == average) {
                    busiestDay.add(currentDay);
                }
            }
        }

       return new BusiestDayResult(busiestDay, average);
    }

    public double getPercentOfHighOccupancy(int occupancy, int highOccupancy) {
        double percent = ((double) occupancy / highOccupancy) * 100;
        if (percent > 100) {
            percent = 100;
        }
        return percent;
    }
}
