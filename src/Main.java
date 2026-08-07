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
        System.out.println("5. View event history");
        System.out.println("6. Help");
        System.out.println("7. quit");
        System.out.println();
        System.out.println("Enter the command you would like to complete: ");

        int input = keyboardInput.nextInt();

        while (input != 7) {
            if (input == 1) {
                tracker.enter();
            } else if (input == 2) {
                tracker.exit();
            } else if (input == 3) {
                tracker.resetCounter();
            } else if (input == 4) {
                System.out.println("Current occupancy: " + tracker.getOccupancyCounter());
            } else if (input == 5) {
                if (tracker.getEventHistory().isEmpty()) {
                    System.out.println("No events have been recorded yet.");
                } else {
                    for (EventRecord eventRecord : tracker.getEventHistory()) {
                        System.out.println(eventRecord.getFormattedRecord());
                    }
                }
            } else if (input == 6) {
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
        System.out.println("5. View event history");
        System.out.println("6. Help");
        System.out.println("7. quit");
    }
}