import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class FlightBoardCO5 {
    public static void main(String[] args) {
        List<String> records = new ArrayList<>(Arrays.asList(
                "UK303,Chennai,14:30,Departed",
                "AI101,Delhi,10:30,Scheduled",
                "6E202,Mumbai,12:00,Delayed"));

        String search = "ai101";
        boolean found = false;

        System.out.println("Search result:");
        for (String record : records) {
            String[] parts = record.split(",");
            if (parts.length == 4 && parts[0].equalsIgnoreCase(search)) {
                System.out.println(String.join(" | ", parts));
                found = true;
            }
        }
        if (!found) {
            System.out.println("Flight not found.");
        }

        records.sort(Comparator.comparing(record -> record.split(",")[0]));
        System.out.println("\\nSorted flight records:");
        records.forEach(record -> System.out.println(record.replace(",", " | ")));

        Path path = Paths.get("co5_flights.txt");
        try {
            Files.write(path, records);
            List<String> loaded = Files.readAllLines(path);
            System.out.println("\\nRead back from " + path + ":");
            loaded.forEach(System.out::println);
        } catch (IOException e) {
            System.out.println("File operation failed: " + e.getMessage());
        }
    }
}
