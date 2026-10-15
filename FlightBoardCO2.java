import java.util.Scanner;

public class FlightBoardCO2 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\\n=== FLIGHT BOARD MENU ===");
            System.out.println("1. View flights");
            System.out.println("2. Check a flight status");
            System.out.println("3. Count down a boarding reminder");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            while (!input.hasNextInt()) {
                System.out.print("Please enter a number: ");
                input.next();
            }
            choice = input.nextInt();

            switch (choice) {
                case 1:
                    for (int i = 1; i <= 3; i++) {
                        if (i == 1) {
                            System.out.println("AI101 - Delhi - Scheduled");
                        } else if (i == 2) {
                            System.out.println("6E202 - Mumbai - Delayed");
                        } else {
                            System.out.println("UK303 - Chennai - Departed");
                        }
                    }
                    break;
                case 2:
                    input.nextLine();
                    System.out.print("Enter status (Scheduled/Delayed/Departed): ");
                    String status = input.nextLine().trim();
                    if (status.equalsIgnoreCase("Scheduled")) {
                        System.out.println("The flight is on schedule.");
                    } else if (status.equalsIgnoreCase("Delayed")) {
                        System.out.println("The flight is delayed. Please wait for updates.");
                    } else if (status.equalsIgnoreCase("Departed")) {
                        System.out.println("The flight has departed.");
                    } else {
                        System.out.println("Unknown status.");
                    }
                    break;
                case 3:
                    for (int seconds = 5; seconds >= 1; seconds--) {
                        if (seconds == 2) {
                            continue;
                        }
                        System.out.println("Boarding reminder: " + seconds);
                    }
                    System.out.println("Please proceed to the gate.");
                    break;
                case 4:
                    System.out.println("Exiting flight board.");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 4);

        input.close();
    }
}
