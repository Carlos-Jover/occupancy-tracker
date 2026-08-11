import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner keyboardInput = new Scanner(System.in);
        Tracker tracker = new Tracker(100);

        tracker.systemStart();

        System.out.println("This is the occupancy tracker. To use it, enter the number of the command you would like to do. This is the list of available commands: ");
        System.out.println("1. Enter");
        System.out.println("2. Exit");
        System.out.println("3. Reset counter");
        System.out.println("4. Manual occupancy correction");
        System.out.println("5. Display current occupancy");
        System.out.println("6. Set high occupancy (default is 100)");
        System.out.println("7. View event history");
        System.out.println("8. Help");
        System.out.println("9. quit");
        System.out.println();
        System.out.println("Enter the command you would like to complete: ");

        int input = getValidInteger(keyboardInput, 1);

        while (input != 9) {
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

            } else if (input == 6) {
                System.out.println("Insert high occupancy: ");

                int newHighOccupancy = getValidInteger(keyboardInput, 1);
                tracker.setHighOccupancy(newHighOccupancy);

            } else if (input == 7) {
                displayEventHistory(tracker);

            } else if (input == 8) {
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
        System.out.println("8. Help");
        System.out.println("9. quit");
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
}