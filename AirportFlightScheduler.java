import java.util.Scanner;

public class AirportFlightScheduler {

    static Scanner sc = new Scanner(System.in);
    static Flight[] flights = new Flight[100];
    static int count = 0;

    //Module 4
    static class Flight implements Comparable<Flight> {
        private String number;
        private String destination;
        private String time;
        private String status;

        Flight(String number, String destination,
               String time, String status) {
            this.number = number;
            this.destination = destination;
            this.time = time;
            this.status = status;
        }

        public String getNumber() {
            return number;
        }

        public String getDestination() {
            return destination;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String toString() {
            return number + " | " + destination
                    + " | " + time + " | " + status;
        }

        // Module 5: Sorting
        public int compareTo(Flight other) {
            return number.compareTo(other.number);
        }
    }

    public static void main(String[] args) {

        loadFlights();

        if (count == 0) {
            addFlight("AI101", "Delhi", "10:30 AM", "Scheduled");
            addFlight("6E202", "Mumbai", "12:00 PM", "Delayed");
            addFlight("UK303", "Chennai", "2:30 PM", "Departed");
        }

        int choice = -1;

        //Module 2
        do {
            System.out.println("\n=== AIRPORT FLIGHT BOARD ===");
            System.out.println("1. Display Flights");
            System.out.println("2. Add Flight");
            System.out.println("3. Search Flight");
            System.out.println("4. Update Status");
            System.out.println("5. Sort Flights");
            System.out.println("6. Flight Report");
            System.out.println("7. Save Flights");
            System.out.println("8. Matrix Addition");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            try {
                choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1:
                        displayFlights();
                        break;
                    case 2:
                        addFlight();
                        break;
                    case 3:
                        searchFlight();
                        break;
                    case 4:
                        updateStatus();
                        break;
                    case 5:
                        sortFlights();
                        break;
                    case 6:
                        flightReport();
                        break;
                    case 7:
                        saveFlights();
                        break;
                    case 8:
                        matrixAddition();
                        break;
                    case 0:
                        saveFlights();
                        System.out.println("Thank you!");
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number.");
                choice = -1;
            }

        } while (choice != 0);

        sc.close();
    }

    //Module 3
    static void addFlight() {
        System.out.print("Enter flight number: ");
        String number = sc.nextLine().trim().toUpperCase();

        System.out.print("Enter destination: ");
        String destination = sc.nextLine().trim();

        System.out.print("Enter departure time: ");
        String time = sc.nextLine().trim();

        if (number.isEmpty() || destination.isEmpty()
                || time.isEmpty()) {
            System.out.println("Details cannot be empty.");
            return;
        }

        addFlight(number, destination, time, "Scheduled");
    }

    static void addFlight(String number, String destination,
                          String time, String status) {

        if (count == flights.length) {
            System.out.println("Flight list is full.");
            return;
        }

        if (search(number) != -1) {
            System.out.println("Flight already exists.");
            return;
        }

        flights[count] = new Flight(
                number, destination, time, status);
        count++;

        System.out.println("Flight added successfully.");
    }
    static void displayFlights() {
        if (count == 0) {
            System.out.println("No flights available.");
            return;
        }

        System.out.println("\nFlight | Destination | Time | Status");

        for (int i = 0; i < count; i++) {
            System.out.println(flights[i]);
        }
    }
    static int search(String number) {
        for (int i = 0; i < count; i++) {
            if (flights[i].getNumber()
                    .equalsIgnoreCase(number)) {
                return i;
            }
        }

        return -1;
    }

    static void searchFlight() {
        System.out.print("Enter flight number: ");
        String number = sc.nextLine().trim();

        int index = search(number);

        if (index != -1) {
            System.out.println("Flight found:");
            System.out.println(flights[index]);
        } else {
            System.out.println("Flight not found.");
        }
    }

    static void updateStatus() {
        System.out.print("Enter flight number: ");
        String number = sc.nextLine().trim();

        int index = search(number);

        if (index == -1) {
            System.out.println("Flight not found.");
            return;
        }

        System.out.print("Enter new status: ");
        String status = sc.nextLine().trim();

        if (status.equalsIgnoreCase("Scheduled") || status.equalsIgnoreCase("Delayed") || status.equalsIgnoreCase("Departed")) {

            flights[index].setStatus(status);
            System.out.println("Status updated.");
        } else {
            System.out.println("Invalid status.");
        }
    }

    //Module 5
    static void sortFlights() {
        java.util.Arrays.sort(flights, 0, count);

        System.out.println("Flights sorted by flight number.");
        displayFlights();
    }

    //Module 6
    static void flightReport() {
        long delayed = java.util.Arrays
                .stream(flights, 0, count)
                .filter(f -> f.getStatus()
                        .equalsIgnoreCase("Delayed"))
                .count();

        System.out.println("Total flights: " + count);
        System.out.println("Delayed flights: " + delayed);
    }

    //Module 5
    static void saveFlights() {
        try (java.io.BufferedWriter writer =
                java.nio.file.Files.newBufferedWriter(
                        java.nio.file.Paths.get("flights.txt"))) {

            for (int i = 0; i < count; i++) {
                writer.write(flights[i].getNumber() + "|"
                        + flights[i].getDestination() + "|"
                        + flights[i].time + "|"
                        + flights[i].getStatus());
                writer.newLine();
            }

            System.out.println("Flights saved.");

        } catch (java.io.IOException e) {
            System.out.println("Error saving flights.");
        }
    }
    static void loadFlights() {
        java.nio.file.Path path =
                java.nio.file.Paths.get("flights.txt");

        if (!java.nio.file.Files.exists(path)) {
            return;
        }

        try (java.io.BufferedReader reader =
                java.nio.file.Files.newBufferedReader(path)) {

            String line;

            while ((line = reader.readLine()) != null
                    && count < flights.length) {

                String[] data = line.split("\\|", -1);

                if (data.length == 4
                        && !data[0].trim().isEmpty()
                        && search(data[0].trim()) == -1) {

                    flights[count] = new Flight(
                            data[0].trim(),
                            data[1].trim(),
                            data[2].trim(),
                            data[3].trim());

                    count++;
                }
            }

        } catch (java.io.IOException e) {
            System.out.println("Could not load saved flights.");
        }
    }
    static void matrixAddition() {
        int[][] a = {{1, 2}, {3, 4}};
        int[][] b = {{5, 6}, {7, 8}};

        System.out.println("Matrix addition:");

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                System.out.print((a[i][j] + b[i][j]) + " ");
            }
            System.out.println();
        }
    }
}
