import java.util.Scanner;

public class FlightBoardCO3 {
    static String[] flightNumbers = {"AI101", "6E202", "UK303", "AI404"};
    static int[] passengers = {120, 98, 145, 110};

    static int findFlight(String[] flights, String wanted) {
        for (int i = 0; i < flights.length; i++) {
            if (flights[i].equalsIgnoreCase(wanted)) {
                return i;
            }
        }
        return -1;
    }

    static int totalPassengers(int[] values, int index) {
        if (index == values.length) {
            return 0;
        }
        return values[index] + totalPassengers(values, index + 1);
    }

    static void showFlights() {
        for (int i = 0; i < flightNumbers.length; i++) {
            System.out.println(flightNumbers[i] + " - Passengers: " + passengers[i]);
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        showFlights();

        System.out.print("\\nEnter flight number to search: ");
        String wanted = input.nextLine();
        int index = findFlight(flightNumbers, wanted);

        if (index >= 0) {
            System.out.println("Found " + flightNumbers[index]
                    + " with " + passengers[index] + " passengers.");
        } else {
            System.out.println("Flight not found.");
        }

        System.out.println("Total passengers across listed flights: "
                + totalPassengers(passengers, 0));
        input.close();
    }
}
