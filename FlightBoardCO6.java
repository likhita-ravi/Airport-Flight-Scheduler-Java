import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class FlightRecord {
    private final String number;
    private final String destination;
    private final String status;

    FlightRecord(String number, String destination, String status) {
        this.number = number;
        this.destination = destination;
        this.status = status;
    }

    String getNumber() { return number; }
    String getDestination() { return destination; }
    String getStatus() { return status; }

    @Override
    public String toString() {
        return number + " | " + destination + " | " + status;
    }
}

public class FlightBoardCO6 {
    public static void main(String[] args) {
        List<FlightRecord> flights = new ArrayList<>();
        flights.add(new FlightRecord("AI101", "Delhi", "Scheduled"));
        flights.add(new FlightRecord("6E202", "Mumbai", "Delayed"));
        flights.add(new FlightRecord("UK303", "Chennai", "Departed"));
        flights.add(new FlightRecord("AI404", "Hyderabad", "Delayed"));

        Map<String, FlightRecord> flightMap = new HashMap<>();
        for (FlightRecord flight : flights) {
            flightMap.put(flight.getNumber(), flight);
        }

        Set<String> destinations = new TreeSet<>();
        flights.forEach(f -> destinations.add(f.getDestination()));

        Predicate<FlightRecord> isDelayed =
                flight -> flight.getStatus().equalsIgnoreCase("Delayed");

        System.out.println("Flight lookup AI101: " + flightMap.get("AI101"));
        System.out.println("Destinations: " + destinations);

        List<FlightRecord> delayed = flights.stream()
                .filter(isDelayed)
                .sorted(Comparator.comparing(FlightRecord::getNumber))
                .collect(Collectors.toList());

        System.out.println("\\nDelayed flights:");
        delayed.forEach(System.out::println);
        System.out.println("Delayed flight count: " + delayed.stream().count());

        String report = flights.stream()
                .map(FlightRecord::getDestination)
                .distinct()
                .sorted()
                .collect(Collectors.joining(", "));
        System.out.println("Destination report: " + report);
    }
}
