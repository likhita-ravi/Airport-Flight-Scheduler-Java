import java.util.Scanner;

class Flight {
    private final String flightNumber;
    private String destination;
    private String status;

    public Flight(String flightNumber, String destination) {
        this(flightNumber, destination, "Scheduled");
    }

    public Flight(String flightNumber, String destination, String status) {
        this.flightNumber = flightNumber;
        this.destination = destination;
        this.status = status;
    }

    public String getFlightNumber() { return flightNumber; }
    public String getDestination() { return destination; }
    public String getStatus() { return status; }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return flightNumber + " | " + destination + " | " + status;
    }
}

class InternationalFlight extends Flight {
    private final String country;

    public InternationalFlight(String number, String destination, String status, String country) {
        super(number, destination, status);
        this.country = country;
    }

    @Override
    public String toString() {
        return super.toString() + " | Country: " + country;
    }
}

public class FlightBoardCO4 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Flight flight = new Flight("AI101", "Delhi");
        Flight international = new InternationalFlight("AI901", "Singapore", "Scheduled", "Singapore");

        System.out.println("Domestic flight: " + flight);
        System.out.println("International flight: " + international);

        System.out.print("Enter new status for AI101 (Scheduled/Delayed/Departed): ");
        String status = input.nextLine().trim();

        try {
            if (!status.equalsIgnoreCase("Scheduled")
                    && !status.equalsIgnoreCase("Delayed")
                    && !status.equalsIgnoreCase("Departed")) {
                throw new IllegalArgumentException("Status must be Scheduled, Delayed, or Departed.");
            }
            flight.setStatus(status);
            System.out.println("Updated flight: " + flight);
        } catch (IllegalArgumentException e) {
            System.out.println("Could not update flight: " + e.getMessage());
        } finally {
            System.out.println("Status update attempt completed.");
            input.close();
        }
    }
}
