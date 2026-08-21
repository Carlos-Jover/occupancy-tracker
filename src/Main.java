import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner keyboardInput = new Scanner(System.in);
        Tracker tracker = new Tracker(100);

        OperatingHours operatingHours = new OperatingHours();

        boolean systemRestored = tracker.restoreOccupancyOnStartUp();
        if (!systemRestored) {
            tracker.systemStart();
        }

        System.out.println("This is the occupancy tracker. To use it, enter the number of the command you would like to do. This is the list of available commands: ");
        System.out.println("1. Enter");
        System.out.println("2. Exit");
        System.out.println("3. Reset counter");
        System.out.println("4. Manual occupancy correction");
        System.out.println("5. Display current occupancy");
        System.out.println("6. Set high occupancy (default is 100)");
        System.out.println("7. View event history");
        System.out.println("8. Set operating hours (default set to 12:00 am - 11:59 pm)");
        System.out.println("9. View operating hours");
        System.out.println("10. Analytics");
        System.out.println("11. Run test simulation");
        System.out.println("12. Help");
        System.out.println("13. quit");
        System.out.println();
        System.out.println("Enter the command you would like to complete: ");

        int input = getValidInteger(keyboardInput, 1);

        while (input != 13) {
            if (input == 1) {
                tracker.enter();

            } else if (input == 2) {
                tracker.exit();

            } else if (input == 3) {
                tracker.resetCounter();
                System.out.println("Occupancy counter reset.");

            } else if (input == 4) {
                System.out.println("Current occupancy: " + tracker.getOccupancyCounter());
                System.out.println("Insert new occupancy: ");

                int newOccupancy = getValidInteger(keyboardInput, 0);

                tracker.manualOccupancyCorrection(newOccupancy);
                System.out.println("Occupancy corrected to: " + tracker.getOccupancyCounter());

            } else if (input == 5) {
                System.out.println("Current occupancy: " + tracker.getOccupancyCounter());

                double occupancyPercentage = tracker.getOccupancyPercentage();
                System.out.printf("Occupancy level: %.0f%%\n", occupancyPercentage);

                displayOccupancyPercentageBar(occupancyPercentage);
                displayLevelOfOccupancy(occupancyPercentage);

                System.out.println();

                if (tracker.getPeakOccupancy() == 0) {
                    System.out.println("Peak occupancy: " + tracker.getPeakOccupancy() + " | No peak recorded yet.");
                } else {
                    DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");
                    String formattedPeakTime = tracker.getPeakOccupancyDateTime().format(format);
                    System.out.println("Peak occupancy: " + tracker.getPeakOccupancy() + " | " + formattedPeakTime);
                }

            } else if (input == 6) {
                System.out.println("Insert high occupancy: ");

                int newHighOccupancy = getValidInteger(keyboardInput, 1);
                tracker.setHighOccupancy(newHighOccupancy);

            } else if (input == 7) {
                displayEventHistory(tracker);

            } else if (input == 8) {
                keyboardInput.nextLine();

                setOperatingHours(keyboardInput, operatingHours);

                displayOperatingTimes(operatingHours);

            } else if (input == 9) {
                displayOperatingTimes(operatingHours);

                if (operatingHours.isTimeWithinOperatingHours(LocalTime.now())) {
                    System.out.println("Business is open.");
                } else {
                    System.out.println("Business is closed.");
                }

            } else if (input == 10) {
                analyticsSubMenu();

                int choice = getValidInteger(keyboardInput, 1);

                while (choice != 4) {
                    if (choice == 1) {
                        System.out.println("Enter date to analyze: ");
                        historicalAnalyticsByDate(tracker, keyboardInput, operatingHours);

                    } else if (choice == 2) {
                        System.out.println("Enter a date to analyze: ");
                        peakOccupancyByDate(tracker, keyboardInput);

                    } else if (choice == 3) {
                        System.out.println("Enter a date to analyze");
                        dailyTrafficByDate(tracker, keyboardInput);

                    } else {
                        System.out.println("Choose from the available options. Try again: ");
                    }

                    System.out.println();
                    analyticsSubMenu();

                    choice = getValidInteger(keyboardInput, 1);
                }

                System.out.println("Exiting analysis.");

            } else if (input == 11) {
                tracker.runTestSimulation(7, 5000, 10001);

            } else if (input == 12) {
                help();

            } else {
                System.out.println("Input must be one of the available command numbers. Try again.");
            }

            System.out.println();
            System.out.println("Insert next command: ");

            input = getValidInteger(keyboardInput, 1);
        }

        tracker.systemStop();
        keyboardInput.close();
    }

    public static void help() {
        System.out.println("These are the available commands:");
        System.out.println("1. Enter");
        System.out.println("2. Exit");
        System.out.println("3. Reset counter");
        System.out.println("4. Manual occupancy correction");
        System.out.println("5. Display current occupancy");
        System.out.println("6. Set high occupancy");
        System.out.println("7. View event history");
        System.out.println("8. Set operating hours");
        System.out.println("9. View operating hours");
        System.out.println("10. Analytics");
        System.out.println("11. Run test simulation");
        System.out.println("12. Help");
        System.out.println("13. quit");
    }

    public static void analyticsSubMenu() {
        System.out.println("Choose from the following to view: ");
        System.out.println("1. Average occupancy");
        System.out.println("2. Peak occupancy");
        System.out.println("3. Daily traffic");
        System.out.println("4. Back");
    }

    public static void displayOccupancyPercentageBar(double occupancyPercentage) {
        final int BAR_LENGTH = 20;

        int percentBar = (int) ((occupancyPercentage / 100) * BAR_LENGTH);
        String hashtagRepeat = "#".repeat(percentBar);
        String dashRepeat = "-".repeat(BAR_LENGTH - percentBar);

        System.out.print("[");
        System.out.print(hashtagRepeat);
        System.out.print(dashRepeat);
        System.out.println("]");
    }

    public static void displayLevelOfOccupancy(double occupancyPercentage) {
        if (occupancyPercentage < 40) {
            System.out.println("Low occupancy");
        } else if (occupancyPercentage < 70) {
            System.out.println("Moderate occupancy");
        } else {
            System.out.println("High occupancy");
        }
    }

    public static int getValidInteger(Scanner keyboardInput, int minimumValue) {
        boolean checkValidation = false;

        int value = -1;

        while (!checkValidation) {
            try {
                value = keyboardInput.nextInt();
                if (value < minimumValue) {
                    System.out.println("Input cannot be less than " + minimumValue + ".");
                    System.out.println("Try again: ");
                } else {
                    checkValidation = true;
                }

            } catch (InputMismatchException exp) {
                System.out.println("Error: Input should be an integer.");
                System.out.println("Try again: ");
                keyboardInput.next();
            }
        }
        return value;
    }

    public static void displayEventHistory(Tracker tracker) {
        for (EventRecord eventRecord : tracker.getEventHistory()) {
            System.out.println(eventRecord.getFormattedRecord());
        }
    }

    public static void displayOperatingTimes(OperatingHours operatingHours) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        String openingHour = operatingHours.getOpeningTime().format(formatter);
        String closingHour = operatingHours.getClosingTime().format(formatter);
        System.out.println("Operating hours are " + openingHour + " to " + closingHour);
    }

    public static void setOperatingHours(Scanner scanner, OperatingHours operatingHours) {
        System.out.println("To input operating hours, follow the format hh:mm AM/PM (ex. 07:30 AM to 02:30 PM).");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");

        boolean success = false;

        while (!success) {
            try {
                System.out.println("Input opening time: ");

                String openingTime = scanner.nextLine();
                LocalTime openingHour = LocalTime.parse(openingTime, formatter);

                System.out.println("Input closing time: ");

                String closingTime = scanner.nextLine();
                LocalTime closingHour = LocalTime.parse(closingTime, formatter);

                operatingHours.setOperatingHours(openingHour, closingHour);

                success = true;

            } catch (DateTimeParseException exp) {
                System.out.println("Input should be in format hh:mm AM/PM (ex. 07:30 AM to 02:30 PM).");
                System.out.println("Try again: ");

            } catch (IllegalArgumentException exp) {
                System.out.println("Opening time must come before closing time.");
                System.out.println("Try again: ");
            }
        }
    }

    public static void historicalAnalyticsByDate(Tracker tracker, Scanner keyboardInput, OperatingHours
            operatingHours) {
        tracker.loadEventHistoryData();

        boolean success = false;

        while (!success) {
            try {
                String dateText = keyboardInput.next();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/uuuu").withResolverStyle(ResolverStyle.STRICT);

                LocalDate date = LocalDate.parse(dateText, formatter);

                ArrayList<EventRecord> events = tracker.getEventsForDate(date);
                OccupancyAnalytics analytics = new OccupancyAnalytics(events);

                double average = analytics.getAverageOccupancy(operatingHours);
                System.out.println();

                if (average == -1) {
                    System.out.println("No event data available for " + date.format(formatter) + ".");
                } else {
                    System.out.printf("Average occupancy for %s: %.2f%n", date.format(formatter), average);
                }

                success = true;

            } catch (DateTimeParseException exp) {
                System.out.println("Input should be a valid date in the format MM/dd/yyyy.");
                System.out.println("Try again: ");
            }
        }
    }

    public static void peakOccupancyByDate(Tracker tracker, Scanner keyboardInput) {
        tracker.loadEventHistoryData();

        boolean success = false;

        while (!success) {
            try {
                String dateText = keyboardInput.next();

                System.out.println();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/uuuu").withResolverStyle(ResolverStyle.STRICT);

                LocalDate date = LocalDate.parse(dateText, formatter);

                ArrayList<EventRecord> events = tracker.getEventsForDate(date);
                OccupancyAnalytics analytics = new OccupancyAnalytics(events);

                int peakOccupancy = analytics.getPeakOccupancy();

                if (peakOccupancy == -1) {
                    System.out.println("No event data available for " + date.format(formatter) + ".");
                } else {
                    System.out.printf("Peak occupancy for %s: %d%n", date.format(formatter), peakOccupancy);
                    System.out.println("Time: ");

                    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");

                    for (EventRecord event : events) {
                        if (event.getOccupancyAfter() == peakOccupancy) {
                            System.out.print(event.getEventDateTime().format(timeFormatter) + " \n");
                        }
                    }
                }

                success = true;

            } catch (DateTimeParseException exp) {
                System.out.println("Input should be a valid date in the format MM/dd/yyyy.");
                System.out.println("Try again: ");
            }
        }
    }

    public static void dailyTrafficByDate(Tracker tracker, Scanner keyboardInput) {
        tracker.loadEventHistoryData();

        boolean success = false;

        while (!success) {
            try {
                String dateText = keyboardInput.next();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/uuuu").withResolverStyle(ResolverStyle.STRICT);

                LocalDate date = LocalDate.parse(dateText, formatter);

                ArrayList<EventRecord> events = tracker.getEventsForDate(date);

                if (events.isEmpty()) {
                    System.out.println("No event data available for " + date.format(formatter) + ".");
                    break;
                }

                int entries = 0;
                int exits = 0;

                for (EventRecord event : events) {
                    if (event.getEventType().equals("Enter")) {
                        entries++;
                    } else if (event.getEventType().equals("Exit")) {
                        exits++;
                    }
                }

                System.out.println();

                int totalTraffic = entries + exits;
                System.out.println("Entries: " + entries);
                System.out.println("Exits: " + exits);
                System.out.println("Total traffic: " + totalTraffic);

                success = true;

            } catch (DateTimeParseException exp) {
                System.out.println("Input should be a valid date in the format MM/dd/yyyy.");
                System.out.println("Try again: ");
            }
        }
    }
}