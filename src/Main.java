import java.util.Scanner;

public class Main {
    public static void main (String[] args) {
        Scanner keyboardInput = new Scanner(System.in);
        Tracker tracker = new Tracker(100);

        System.out.println("This is the occupancy tracker. To use it, enter the number of the command you would like to do. This is the list of available commands: ");
        System.out.println("1. Enter");
        System.out.println("2. Exit");
        System.out.println("3. Reset counter");
        System.out.println("4. Display current occupancy");
        System.out.println("5. Set high occupancy (default is 100)");
        System.out.println("6. View event history");
        System.out.println("7. Help");
        System.out.println("8. quit");
        System.out.println();
        System.out.println("Enter the command you would like to complete: ");

        int input = keyboardInput.nextInt();

        while (input != 8) {
            if (input == 1) {
                tracker.enter();

            } else if (input == 2) {
                tracker.exit();

            } else if (input == 3) {
                tracker.resetCounter();

            } else if (input == 4) {
                System.out.println("Current occupancy: " + tracker.getOccupancyCounter());

                double occupancyPercentage = tracker.getOccupancyPercentage();
                System.out.printf("Occupancy level: %.0f%%\n", occupancyPercentage);

                int percentBar = (int) occupancyPercentage / 5;
                String hashtagRepeat = "#".repeat((int) percentBar);
                String dashRepeat = "-".repeat(20 - (int) percentBar);

                System.out.print("[");
                System.out.print(hashtagRepeat);
                System.out.print(dashRepeat);
                System.out.println("]");

                if (occupancyPercentage < 40)
                    System.out.println("Low occupancy");
                else if (occupancyPercentage < 70)
                    System.out.println("Moderate occupancy");
                else
                    System.out.println("High occupancy");

            } else if (input == 5) {
                System.out.println("Insert high occupancy: ");
                int newHighOccupancy = keyboardInput.nextInt();
                tracker.setHighOccupancy(newHighOccupancy);

            } else if (input == 6) {
                if (tracker.getEventHistory().isEmpty()) {
                    System.out.println("No events have been recorded yet.");
                } else {
                    for (EventRecord eventRecord : tracker.getEventHistory()) {
                        System.out.println(eventRecord.getFormattedRecord());
                    }
                }

            } else if (input == 7) {
                help();

            } else {
                System.out.println("Input must be one of the available command numbers. Try again.");
            }

            System.out.println();
            System.out.println("Insert next command: ");
            input = keyboardInput.nextInt();
        }

        keyboardInput.close();
    }

    public static void help() {
        System.out.println("These are the available commands:");
        System.out.println("1. Enter");
        System.out.println("2. Exit");
        System.out.println("3. Reset counter");
        System.out.println("4. Display current occupancy");
        System.out.println("5. Set high occupancy");
        System.out.println("6. View event history");
        System.out.println("7. Help");
        System.out.println("8. quit");
    }
}