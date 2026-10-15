import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class ScheduledFlight {
    private final String number;
    private String destination;
    private String departureTime;
    private String status;

    ScheduledFlight(String number, String destination, String departureTime, String status) {
        this.number = number;
        this.destination = destination;
        this.departureTime = departureTime;
        this.status = status;
    }

    String getNumber() { return number; }
    String getDestination() { return destination; }
    String getDepartureTime() { return departureTime; }
    String getStatus() { return status; }
    void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return number + " | " + destination + " | " + departureTime + " | " + status;
    }

    String toFileLine() {
        return String.join(",", number, destination, departureTime, status);
    }

    static ScheduledFlight fromFileLine(String line) {
        String[] p = line.split(",", -1);
        if (p.length != 4) return null;
        return new ScheduledFlight(p[0], p[1], p[2], p[3]);
    }
}

public class AirportFlightScheduler {
    private static final Scanner input = new Scanner(System.in);
    private static final List<ScheduledFlight> flights = new ArrayList<>();
    private static final Path FILE = Paths.get("flights.txt");

    public static void main(String[] args) {
        addSampleFlights();
        int choice = 0;

        do {
            System.out.println("\\n===== AIRPORT FLIGHT BOARD & SCHEDULER =====");
            System.out.println("1. Show all flights");
            System.out.println("2. Search flight");
            System.out.println("3. Update flight status");
            System.out.println("4. Show delayed flights");
            System.out.println("5. Sort flights by number");
            System.out.println("6. Add a flight");
            System.out.println("7. Save flights");
            System.out.println("8. Load flights");
            System.out.println("9. Exit");
            System.out.print("Enter choice: ");

            while (!input.hasNextInt()) {
                System.out.print("Enter a number from 1 to 9: ");
                input.next();
            }
            choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1: showFlights(); break;
                case 2: searchFlight(); break;
                case 3: updateStatus(); break;
                case 4: showDelayedFlights(); break;
                case 5: sortFlights(); break;
                case 6: addFlight(); break;
                case 7: saveFlights(); break;
                case 8: loadFlights(); break;
                case 9: System.out.println("Thank you for using the scheduler!"); break;
                default: System.out.println("Invalid choice.");
            }
        } while (choice != 9);

        input.close();
    }

    private static void addSampleFlights() {
        flights.add(new ScheduledFlight("AI101", "Delhi", "10:30 AM", "Scheduled"));
        flights.add(new ScheduledFlight("6E202", "Mumbai", "12:00 PM", "Delayed"));
        flights.add(new ScheduledFlight("UK303", "Chennai", "2:30 PM", "Departed"));
    }

    private static void showFlights() {
        if (flights.isEmpty()) {
            System.out.println("No flights available.");
            return;
        }
        System.out.println("\\nFlight | Destination | Departure | Status");
        flights.forEach(System.out::println);
    }

    private static void searchFlight() {
        System.out.print("Enter flight number: ");
        String wanted = input.nextLine().trim();
        Optional<ScheduledFlight> found = flights.stream()
                .filter(f -> f.getNumber().equalsIgnoreCase(wanted))
                .findFirst();
        System.out.println(found.map(Object::toString).orElse("Flight not found."));
    }

    private static void updateStatus() {
        System.out.print("Enter flight number: ");
        String wanted = input.nextLine().trim();
        ScheduledFlight match = null;
        for (ScheduledFlight flight : flights) {
            if (flight.getNumber().equalsIgnoreCase(wanted)) {
                match = flight;
                break;
            }
        }
        if (match == null) {
            System.out.println("Flight not found.");
            return;
        }

        System.out.print("Enter status (Scheduled/Delayed/Departed): ");
        String status = input.nextLine().trim();
        try {
            if (!status.equalsIgnoreCase("Scheduled")
                    && !status.equalsIgnoreCase("Delayed")
                    && !status.equalsIgnoreCase("Departed")) {
                throw new IllegalArgumentException("Choose Scheduled, Delayed, or Departed.");
            }
            match.setStatus(capitalize(status));
            System.out.println("Updated: " + match);
        } catch (IllegalArgumentException e) {
            System.out.println("Update failed: " + e.getMessage());
        }
    }

    private static String capitalize(String value) {
        String lower = value.toLowerCase(Locale.ROOT);
        return Character.toUpperCase(lower.charAt(0)) + lower.substring(1);
    }

    private static void showDelayedFlights() {
        Predicate<ScheduledFlight> delayed =
                flight -> flight.getStatus().equalsIgnoreCase("Delayed");
        List<ScheduledFlight> result = flights.stream()
                .filter(delayed)
                .sorted(Comparator.comparing(ScheduledFlight::getNumber))
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            System.out.println("No delayed flights.");
        } else {
            result.forEach(System.out::println);
            System.out.println("Total delayed flights: " + result.size());
        }
    }

    private static void sortFlights() {
        flights.sort(Comparator.comparing(ScheduledFlight::getNumber));
        System.out.println("Flights sorted by number.");
        showFlights();
    }

    private static void addFlight() {
        System.out.print("Flight number: ");
        String number = input.nextLine().trim();
        if (number.isEmpty() || findFlight(number) != null) {
            System.out.println("Flight number is blank or already exists.");
            return;
        }
        System.out.print("Destination: ");
        String destination = input.nextLine().trim();
        System.out.print("Departure time: ");
        String time = input.nextLine().trim();

        if (destination.isEmpty() || time.isEmpty()) {
            System.out.println("Destination and time cannot be blank.");
            return;
        }
        flights.add(new ScheduledFlight(number, destination, time, "Scheduled"));
        System.out.println("Flight added.");
    }

    private static ScheduledFlight findFlight(String number) {
        for (ScheduledFlight flight : flights) {
            if (flight.getNumber().equalsIgnoreCase(number)) return flight;
        }
        return null;
    }

    private static void saveFlights() {
        List<String> lines = flights.stream()
                .map(ScheduledFlight::toFileLine)
                .collect(Collectors.toList());
        try {
            Files.write(FILE, lines);
            System.out.println("Saved " + lines.size() + " flight(s) to " + FILE.toAbsolutePath());
        } catch (IOException e) {
            System.out.println("Could not save flights: " + e.getMessage());
        }
    }

    private static void loadFlights() {
        if (!Files.exists(FILE)) {
            System.out.println("No saved file yet. Choose Save flights first.");
            return;
        }
        try {
            List<ScheduledFlight> loaded = new ArrayList<>();
            for (String line : Files.readAllLines(FILE)) {
                ScheduledFlight flight = ScheduledFlight.fromFileLine(line);
                if (flight != null) loaded.add(flight);
            }
            flights.clear();
            flights.addAll(loaded);
            System.out.println("Loaded " + flights.size() + " flight(s).");
        } catch (IOException e) {
            System.out.println("Could not load flights: " + e.getMessage());
        }
    }
}
