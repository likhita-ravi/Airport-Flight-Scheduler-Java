import java.util.Scanner;

public class FlightBoardCO1 {
    public static void main(String[] args) {
        // IPO: input flight details -> store and format them -> display a report.
        Scanner input = new Scanner(System.in);

        System.out.println("=== AIRPORT FLIGHT BOARD ===");
        System.out.print("Enter flight number: ");
        String flightNumber = input.nextLine();

        System.out.print("Enter destination: ");
        String destination = input.nextLine();

        System.out.print("Enter departure hour (24-hour format): ");
        int hour = input.nextInt();

        System.out.print("Enter departure minute: ");
        int minute = input.nextInt();

        final int MINUTES_PER_HOUR = 60;
        int totalMinutes = hour * MINUTES_PER_HOUR + minute;
        double timeInHours = totalMinutes / 60.0;
        boolean validTime = hour >= 0 && hour <= 23 && minute >= 0 && minute <= 59;

        System.out.println("\\n=== FLIGHT DETAILS ===");
        System.out.printf("Flight: %s%nDestination: %s%n", flightNumber, destination);
        System.out.printf("Entered time: %02d:%02d%n", hour, minute);
        System.out.println("Total minutes from midnight: " + totalMinutes);
        System.out.printf("Time in hours (decimal): %.2f%n", timeInHours);
        System.out.println("Time valid: " + validTime);

        input.close();
    }
}
