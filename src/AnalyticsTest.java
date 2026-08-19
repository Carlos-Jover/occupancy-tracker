import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

// Test cases generated with assistance from ChatGPT.
public class AnalyticsTest {

    public static void main(String[] args) {

        testBasicAverage();
        testAllEventsBeforeOpening();
        testAllEventsAfterClosing();
        testEventCrossesOpening();
        testEventCrossesClosing();
        testMultipleOccupancyChanges();
        testEmptyEvents();
        testSingleEventDuringHours();
        testSingleEventBeforeOpening();
        testSingleEventAfterClosing();
        testEventFarAfterClosing();
    }

    public static void testBasicAverage() {
        ArrayList<EventRecord> events = new ArrayList<>();

        events.add(new EventRecord(
                "Enter",
                LocalDateTime.of(2026, 8, 19, 9, 30),
                1
        ));

        events.add(new EventRecord(
                "Exit",
                LocalDateTime.of(2026, 8, 19, 10, 30),
                0
        ));

        OperatingHours operatingHours = new OperatingHours();
        operatingHours.setOperatingHours(
                LocalTime.of(9, 0),
                LocalTime.of(11, 0)
        );

        runTest(
                "Basic weighted average",
                events,
                operatingHours,
                0.50
        );
    }

    public static void testAllEventsBeforeOpening() {
        ArrayList<EventRecord> events = new ArrayList<>();

        events.add(new EventRecord(
                "Enter",
                LocalDateTime.of(2026, 8, 19, 7, 30),
                1
        ));

        events.add(new EventRecord(
                "Enter",
                LocalDateTime.of(2026, 8, 19, 8, 30),
                2
        ));

        OperatingHours operatingHours = new OperatingHours();
        operatingHours.setOperatingHours(
                LocalTime.of(9, 0),
                LocalTime.of(11, 0)
        );

        runTest(
                "All events before opening",
                events,
                operatingHours,
                2.00
        );
    }

    public static void testAllEventsAfterClosing() {
        ArrayList<EventRecord> events = new ArrayList<>();

        events.add(new EventRecord(
                "Enter",
                LocalDateTime.of(2026, 8, 19, 11, 30),
                1
        ));

        events.add(new EventRecord(
                "Enter",
                LocalDateTime.of(2026, 8, 19, 12, 0),
                2
        ));

        OperatingHours operatingHours = new OperatingHours();
        operatingHours.setOperatingHours(
                LocalTime.of(9, 0),
                LocalTime.of(11, 0)
        );

        runTest(
                "All events after closing",
                events,
                operatingHours,
                0.00
        );
    }

    public static void testEventCrossesOpening() {
        ArrayList<EventRecord> events = new ArrayList<>();

        events.add(new EventRecord(
                "Enter",
                LocalDateTime.of(2026, 8, 19, 8, 30),
                1
        ));

        events.add(new EventRecord(
                "Enter",
                LocalDateTime.of(2026, 8, 19, 9, 30),
                2
        ));

        events.add(new EventRecord(
                "Exit",
                LocalDateTime.of(2026, 8, 19, 10, 30),
                1
        ));

        OperatingHours operatingHours = new OperatingHours();
        operatingHours.setOperatingHours(
                LocalTime.of(9, 0),
                LocalTime.of(11, 0)
        );

        /*
         * 9:00 - 9:30  -> occupancy 1
         * 9:30 - 10:30 -> occupancy 2
         * 10:30 - 11:00 -> occupancy 1
         *
         * Weighted:
         * 1 * 30 = 30
         * 2 * 60 = 120
         * 1 * 30 = 30
         *
         * Total = 180
         * Total time = 120
         * Average = 1.50
         */

        runTest(
                "Interval crossing opening",
                events,
                operatingHours,
                1.50
        );
    }

    public static void testEventCrossesClosing() {
        ArrayList<EventRecord> events = new ArrayList<>();

        events.add(new EventRecord(
                "Enter",
                LocalDateTime.of(2026, 8, 19, 9, 30),
                1
        ));

        events.add(new EventRecord(
                "Enter",
                LocalDateTime.of(2026, 8, 19, 10, 30),
                2
        ));

        events.add(new EventRecord(
                "Exit",
                LocalDateTime.of(2026, 8, 19, 11, 30),
                1
        ));

        OperatingHours operatingHours = new OperatingHours();
        operatingHours.setOperatingHours(
                LocalTime.of(9, 0),
                LocalTime.of(11, 0)
        );

        /*
         * 9:00 - 9:30  -> occupancy 0
         * 9:30 - 10:30 -> occupancy 1
         * 10:30 - 11:00 -> occupancy 2
         *
         * Weighted:
         * 0 * 30 = 0
         * 1 * 60 = 60
         * 2 * 30 = 60
         *
         * Total = 120
         * Total time = 120
         * Average = 1.00
         */

        runTest(
                "Interval crossing closing",
                events,
                operatingHours,
                1.00
        );
    }

    public static void testMultipleOccupancyChanges() {
        ArrayList<EventRecord> events = new ArrayList<>();

        events.add(new EventRecord(
                "Enter",
                LocalDateTime.of(2026, 8, 19, 9, 15),
                1
        ));

        events.add(new EventRecord(
                "Enter",
                LocalDateTime.of(2026, 8, 19, 9, 45),
                2
        ));

        events.add(new EventRecord(
                "Enter",
                LocalDateTime.of(2026, 8, 19, 10, 15),
                3
        ));

        events.add(new EventRecord(
                "Exit",
                LocalDateTime.of(2026, 8, 19, 10, 45),
                2
        ));

        OperatingHours operatingHours = new OperatingHours();
        operatingHours.setOperatingHours(
                LocalTime.of(9, 0),
                LocalTime.of(11, 0)
        );

        /*
         * 9:00 - 9:15   -> 0 people = 15 min
         * 9:15 - 9:45   -> 1 person = 30 min
         * 9:45 - 10:15  -> 2 people = 30 min
         * 10:15 - 10:45 -> 3 people = 30 min
         * 10:45 - 11:00 -> 2 people = 15 min
         *
         * Weighted:
         * 0 * 15 = 0
         * 1 * 30 = 30
         * 2 * 30 = 60
         * 3 * 30 = 90
         * 2 * 15 = 30
         *
         * Total weighted = 210
         * Total time = 120
         * Average = 1.75
         */

        runTest(
                "Multiple occupancy changes",
                events,
                operatingHours,
                1.75
        );
    }

    public static void testEmptyEvents() {
        ArrayList<EventRecord> events = new ArrayList<>();

        OperatingHours operatingHours = new OperatingHours();
        operatingHours.setOperatingHours(
                LocalTime.of(9, 0),
                LocalTime.of(11, 0)
        );

        runTest(
                "Empty event list",
                events,
                operatingHours,
                -1.00
        );
    }

    public static void testSingleEventDuringHours() {
        ArrayList<EventRecord> events = new ArrayList<>();

        events.add(new EventRecord(
                "Enter",
                LocalDateTime.of(2026, 8, 19, 10, 0),
                1
        ));

        OperatingHours operatingHours = new OperatingHours();
        operatingHours.setOperatingHours(
                LocalTime.of(9, 0),
                LocalTime.of(11, 0)
        );

        /*
         * 9:00 - 10:00 -> occupancy 0
         * 10:00 - 11:00 -> occupancy 1
         *
         * Average = 0.50
         */

        runTest(
                "Single event during operating hours",
                events,
                operatingHours,
                0.50
        );
    }

    public static void testSingleEventBeforeOpening() {
        ArrayList<EventRecord> events = new ArrayList<>();

        events.add(new EventRecord(
                "Enter",
                LocalDateTime.of(2026, 8, 19, 8, 0),
                1
        ));

        OperatingHours operatingHours = new OperatingHours();
        operatingHours.setOperatingHours(
                LocalTime.of(9, 0),
                LocalTime.of(11, 0)
        );

        /*
         * Last known occupancy before opening is 1.
         *
         * 9:00 - 11:00 -> occupancy 1
         *
         * Expected average = 1.00
         */

        runTest(
                "Single event before opening",
                events,
                operatingHours,
                1.00
        );
    }

    public static void testSingleEventAfterClosing() {
        ArrayList<EventRecord> events = new ArrayList<>();

        events.add(new EventRecord(
                "Enter",
                LocalDateTime.of(2026, 8, 19, 12, 0),
                1
        ));

        OperatingHours operatingHours = new OperatingHours();
        operatingHours.setOperatingHours(
                LocalTime.of(9, 0),
                LocalTime.of(11, 0)
        );

        /*
         * No event occurred during operating hours.
         * Under the current rule, occupancy is 0 all day.
         *
         * Expected average = 0.00
         */

        runTest(
                "Single event after closing",
                events,
                operatingHours,
                0.00
        );
    }

    public static void runTest(
            String testName,
            ArrayList<EventRecord> events,
            OperatingHours operatingHours,
            double expected
    ) {
        OccupancyAnalytics analytics =
                new OccupancyAnalytics(events);

        double actual =
                analytics.getAverageOccupancy(operatingHours);

        System.out.println();
        System.out.println("--------------------------------");
        System.out.println(testName);
        System.out.printf("Expected: %.2f%n", expected);
        System.out.printf("Actual:   %.2f%n", actual);

        if (Math.abs(expected - actual) < 0.0001) {
            System.out.println("RESULT: PASS");
        } else {
            System.out.println("RESULT: FAIL");
        }
    }

    public static void testEventFarAfterClosing() {
        ArrayList<EventRecord> events = new ArrayList<>();

        events.add(new EventRecord(
                "Enter",
                LocalDateTime.of(2026, 8, 19, 16, 0),
                1
        ));

        OperatingHours operatingHours = new OperatingHours();
        operatingHours.setOperatingHours(
                LocalTime.of(9, 0),
                LocalTime.of(11, 0)
        );

        /*
         * Business hours: 9:00 AM - 11:00 AM
         * First event does not occur until 4:00 PM.
         *
         * Under the current Version 1 rule:
         * 9:00 - 11:00 -> occupancy 0
         *
         * Expected average = 0.00
         */

        runTest(
                "First event far after closing",
                events,
                operatingHours,
                0.00
        );
    }
}