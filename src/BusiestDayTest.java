import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;

// This code was made with AI.
public class BusiestDayTest {

    public static void main(String[] args) {
        testSingleDay();
        testDifferentWeekdays();
        testSameWeekdayAverage();
        testTwoWayTie();
        testThreeWayTie();
        testLaterDayBecomesLeader();
        testZeroTrafficDay();
        testEmptyEvents();
        testMultipleEventsSameDate();
        testLastDateIsCounted();
    }

    public static void testSingleDay() {
        ArrayList<EventRecord> events = new ArrayList<>();

        // Monday
        events.add(new EventRecord(
                "Enter",
                LocalDateTime.of(2026, 8, 24, 9, 0),
                1
        ));

        events.add(new EventRecord(
                "Exit",
                LocalDateTime.of(2026, 8, 24, 10, 0),
                0
        ));

        runTest(
                "Single day",
                events,
                new DayOfWeek[]{DayOfWeek.MONDAY},
                2.0
        );
    }

    public static void testDifferentWeekdays() {
        ArrayList<EventRecord> events = new ArrayList<>();

        // Monday traffic = 2
        events.add(new EventRecord(
                "Enter",
                LocalDateTime.of(2026, 8, 24, 9, 0),
                1
        ));

        events.add(new EventRecord(
                "Exit",
                LocalDateTime.of(2026, 8, 24, 10, 0),
                0
        ));

        // Tuesday traffic = 4
        events.add(new EventRecord(
                "Enter",
                LocalDateTime.of(2026, 8, 25, 9, 0),
                1
        ));

        events.add(new EventRecord(
                "Enter",
                LocalDateTime.of(2026, 8, 25, 9, 30),
                2
        ));

        events.add(new EventRecord(
                "Exit",
                LocalDateTime.of(2026, 8, 25, 10, 0),
                1
        ));

        events.add(new EventRecord(
                "Exit",
                LocalDateTime.of(2026, 8, 25, 10, 30),
                0
        ));

        runTest(
                "Different weekdays",
                events,
                new DayOfWeek[]{DayOfWeek.TUESDAY},
                4.0
        );
    }

    public static void testSameWeekdayAverage() {
        ArrayList<EventRecord> events = new ArrayList<>();

        // Monday #1 traffic = 2
        events.add(new EventRecord(
                "Enter",
                LocalDateTime.of(2026, 8, 24, 9, 0),
                1
        ));

        events.add(new EventRecord(
                "Exit",
                LocalDateTime.of(2026, 8, 24, 10, 0),
                0
        ));

        // Monday #2 traffic = 6
        events.add(new EventRecord(
                "Enter",
                LocalDateTime.of(2026, 8, 31, 9, 0),
                1
        ));

        events.add(new EventRecord(
                "Enter",
                LocalDateTime.of(2026, 8, 31, 9, 10),
                2
        ));

        events.add(new EventRecord(
                "Enter",
                LocalDateTime.of(2026, 8, 31, 9, 20),
                3
        ));

        events.add(new EventRecord(
                "Exit",
                LocalDateTime.of(2026, 8, 31, 9, 30),
                2
        ));

        events.add(new EventRecord(
                "Exit",
                LocalDateTime.of(2026, 8, 31, 9, 40),
                1
        ));

        events.add(new EventRecord(
                "Exit",
                LocalDateTime.of(2026, 8, 31, 9, 50),
                0
        ));

        /*
         * Monday average:
         * (2 + 6) / 2 = 4
         */

        runTest(
                "Same weekday average",
                events,
                new DayOfWeek[]{DayOfWeek.MONDAY},
                4.0
        );
    }

    public static void testTwoWayTie() {
        ArrayList<EventRecord> events = new ArrayList<>();

        // Monday traffic = 2
        events.add(new EventRecord(
                "Enter",
                LocalDateTime.of(2026, 8, 24, 9, 0),
                1
        ));

        events.add(new EventRecord(
                "Exit",
                LocalDateTime.of(2026, 8, 24, 10, 0),
                0
        ));

        // Tuesday traffic = 2
        events.add(new EventRecord(
                "Enter",
                LocalDateTime.of(2026, 8, 25, 9, 0),
                1
        ));

        events.add(new EventRecord(
                "Exit",
                LocalDateTime.of(2026, 8, 25, 10, 0),
                0
        ));

        runTest(
                "Two-way tie",
                events,
                new DayOfWeek[]{
                        DayOfWeek.MONDAY,
                        DayOfWeek.TUESDAY
                },
                2.0
        );
    }

    public static void testThreeWayTie() {
        ArrayList<EventRecord> events = new ArrayList<>();

        // Monday = 2
        events.add(new EventRecord(
                "Enter",
                LocalDateTime.of(2026, 8, 24, 9, 0),
                1
        ));
        events.add(new EventRecord(
                "Exit",
                LocalDateTime.of(2026, 8, 24, 10, 0),
                0
        ));

        // Tuesday = 2
        events.add(new EventRecord(
                "Enter",
                LocalDateTime.of(2026, 8, 25, 9, 0),
                1
        ));
        events.add(new EventRecord(
                "Exit",
                LocalDateTime.of(2026, 8, 25, 10, 0),
                0
        ));

        // Wednesday = 2
        events.add(new EventRecord(
                "Enter",
                LocalDateTime.of(2026, 8, 26, 9, 0),
                1
        ));
        events.add(new EventRecord(
                "Exit",
                LocalDateTime.of(2026, 8, 26, 10, 0),
                0
        ));

        runTest(
                "Three-way tie",
                events,
                new DayOfWeek[]{
                        DayOfWeek.MONDAY,
                        DayOfWeek.TUESDAY,
                        DayOfWeek.WEDNESDAY
                },
                2.0
        );
    }

    public static void testLaterDayBecomesLeader() {
        ArrayList<EventRecord> events = new ArrayList<>();

        // Monday = 2
        events.add(new EventRecord(
                "Enter",
                LocalDateTime.of(2026, 8, 24, 9, 0),
                1
        ));
        events.add(new EventRecord(
                "Exit",
                LocalDateTime.of(2026, 8, 24, 10, 0),
                0
        ));

        // Tuesday = 2
        events.add(new EventRecord(
                "Enter",
                LocalDateTime.of(2026, 8, 25, 9, 0),
                1
        ));
        events.add(new EventRecord(
                "Exit",
                LocalDateTime.of(2026, 8, 25, 10, 0),
                0
        ));

        // Wednesday = 5
        events.add(new EventRecord(
                "Enter",
                LocalDateTime.of(2026, 8, 26, 9, 0),
                1
        ));
        events.add(new EventRecord(
                "Enter",
                LocalDateTime.of(2026, 8, 26, 9, 10),
                2
        ));
        events.add(new EventRecord(
                "Exit",
                LocalDateTime.of(2026, 8, 26, 9, 20),
                1
        ));
        events.add(new EventRecord(
                "Enter",
                LocalDateTime.of(2026, 8, 26, 9, 30),
                2
        ));
        events.add(new EventRecord(
                "Exit",
                LocalDateTime.of(2026, 8, 26, 9, 40),
                1
        ));

        runTest(
                "Later weekday becomes leader",
                events,
                new DayOfWeek[]{DayOfWeek.WEDNESDAY},
                5.0
        );
    }

    public static void testZeroTrafficDay() {
        ArrayList<EventRecord> events = new ArrayList<>();

        // Monday has events, but no Enter/Exit traffic
        events.add(new EventRecord(
                "SYSTEM START",
                LocalDateTime.of(2026, 8, 24, 9, 0),
                0
        ));

        events.add(new EventRecord(
                "SYSTEM STOP",
                LocalDateTime.of(2026, 8, 24, 17, 0),
                0
        ));

        runTest(
                "Zero traffic day",
                events,
                new DayOfWeek[]{DayOfWeek.MONDAY},
                0.0
        );
    }

    public static void testEmptyEvents() {
        ArrayList<EventRecord> events = new ArrayList<>();

        runTest(
                "Empty event list",
                events,
                new DayOfWeek[]{},
                0.0
        );
    }

    public static void testMultipleEventsSameDate() {
        ArrayList<EventRecord> events = new ArrayList<>();

        // Monday traffic = 6
        events.add(new EventRecord(
                "SYSTEM START",
                LocalDateTime.of(2026, 8, 24, 8, 0),
                0
        ));

        events.add(new EventRecord(
                "Enter",
                LocalDateTime.of(2026, 8, 24, 9, 0),
                1
        ));

        events.add(new EventRecord(
                "Enter",
                LocalDateTime.of(2026, 8, 24, 9, 30),
                2
        ));

        events.add(new EventRecord(
                "Exit",
                LocalDateTime.of(2026, 8, 24, 10, 0),
                1
        ));

        events.add(new EventRecord(
                "Enter",
                LocalDateTime.of(2026, 8, 24, 11, 0),
                2
        ));

        events.add(new EventRecord(
                "Exit",
                LocalDateTime.of(2026, 8, 24, 12, 0),
                1
        ));

        events.add(new EventRecord(
                "Exit",
                LocalDateTime.of(2026, 8, 24, 13, 0),
                0
        ));

        events.add(new EventRecord(
                "SYSTEM STOP",
                LocalDateTime.of(2026, 8, 24, 17, 0),
                0
        ));

        runTest(
                "Multiple events on same date",
                events,
                new DayOfWeek[]{DayOfWeek.MONDAY},
                6.0
        );
    }

    public static void testLastDateIsCounted() {
        ArrayList<EventRecord> events = new ArrayList<>();

        // Monday traffic = 2
        events.add(new EventRecord(
                "Enter",
                LocalDateTime.of(2026, 8, 24, 9, 0),
                1
        ));

        events.add(new EventRecord(
                "Exit",
                LocalDateTime.of(2026, 8, 24, 10, 0),
                0
        ));

        // Tuesday is the final date and traffic = 5
        events.add(new EventRecord(
                "Enter",
                LocalDateTime.of(2026, 8, 25, 9, 0),
                1
        ));

        events.add(new EventRecord(
                "Enter",
                LocalDateTime.of(2026, 8, 25, 9, 10),
                2
        ));

        events.add(new EventRecord(
                "Exit",
                LocalDateTime.of(2026, 8, 25, 9, 20),
                1
        ));

        events.add(new EventRecord(
                "Enter",
                LocalDateTime.of(2026, 8, 25, 9, 30),
                2
        ));

        events.add(new EventRecord(
                "Exit",
                LocalDateTime.of(2026, 8, 25, 9, 40),
                1
        ));

        runTest(
                "Last date is counted",
                events,
                new DayOfWeek[]{DayOfWeek.TUESDAY},
                5.0
        );
    }

    public static void runTest(
            String testName,
            ArrayList<EventRecord> events,
            DayOfWeek[] expectedDays,
            double expectedAverage
    ) {
        OccupancyAnalytics analytics =
                new OccupancyAnalytics(events);

        BusiestDayResult result =
                analytics.getBusiestDayOfWeek();

        ArrayList<DayOfWeek> actualDays =
                result.getDayOfWeek();

        double actualAverage =
                result.getAverageTraffic();

        boolean daysMatch =
                actualDays.size() == expectedDays.length;

        if (daysMatch) {
            for (DayOfWeek expectedDay : expectedDays) {
                if (!actualDays.contains(expectedDay)) {
                    daysMatch = false;
                    break;
                }
            }
        }

        boolean averageMatches =
                Math.abs(expectedAverage - actualAverage) < 0.0001;

        System.out.println();
        System.out.println("--------------------------------");
        System.out.println(testName);

        System.out.print("Expected days: ");
        printDays(expectedDays);

        System.out.println("Actual days:   " + actualDays);

        System.out.printf(
                "Expected average: %.2f%n",
                expectedAverage
        );

        System.out.printf(
                "Actual average:   %.2f%n",
                actualAverage
        );

        if (daysMatch && averageMatches) {
            System.out.println("RESULT: PASS");
        } else {
            System.out.println("RESULT: FAIL");
        }
    }

    public static void printDays(DayOfWeek[] days) {
        System.out.print("[");

        for (int i = 0; i < days.length; i++) {
            System.out.print(days[i]);

            if (i < days.length - 1) {
                System.out.print(", ");
            }
        }

        System.out.println("]");
    }
}