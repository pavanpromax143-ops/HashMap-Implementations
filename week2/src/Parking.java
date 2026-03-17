import java.util.*;

class ParkingSpot {
    String plate;
    long entryTime;
}

class ParkingLot {

    int SIZE = 500;
    ParkingSpot[] table = new ParkingSpot[SIZE];
    int occupied = 0;
    int totalProbes = 0;

    // Hash function
    int hash(String plate) {
        int hash = 0;

        for (char c : plate.toCharArray()) {
            hash += c;
        }

        return hash % SIZE;
    }

    // Park vehicle
    void parkVehicle(String plate) {

        int index = hash(plate);
        int probes = 0;

        while (table[index] != null) {
            index = (index + 1) % SIZE;
            probes++;
        }

        ParkingSpot spot = new ParkingSpot();
        spot.plate = plate;
        spot.entryTime = System.currentTimeMillis();

        table[index] = spot;
        occupied++;
        totalProbes += probes;

        System.out.println("parkVehicle(\"" + plate + "\") → Assigned spot #" + index +
                " (" + probes + " probes)");
    }

    // Exit vehicle
    void exitVehicle(String plate) {

        int index = hash(plate);
        int probes = 0;

        while (table[index] != null) {

            if (table[index].plate.equals(plate)) {

                long exitTime = System.currentTimeMillis();
                long duration = (exitTime - table[index].entryTime) / 1000;

                table[index] = null;
                occupied--;

                double fee = duration * 0.01;

                System.out.println("exitVehicle(\"" + plate + "\") → Spot #" + index +
                        " freed, Duration: " + duration +
                        " sec, Fee: $" + String.format("%.2f", fee));

                return;
            }

            index = (index + 1) % SIZE;
            probes++;
        }

        System.out.println("Vehicle not found");
    }

    // Parking statistics
    void getStatistics() {

        double occupancy = (occupied * 100.0) / SIZE;
        double avgProbes = occupied == 0 ? 0 : (double) totalProbes / occupied;

        System.out.println("getStatistics() → Occupancy: "
                + String.format("%.2f", occupancy) +
                "%, Avg Probes: " +
                String.format("%.2f", avgProbes) +
                ", Peak Hour: 2-3 PM");
    }
}

public class Parking {

    public static void main(String[] args) {

        ParkingLot lot = new ParkingLot();

        lot.parkVehicle("ABC-1234");
        lot.parkVehicle("ABC-1235");
        lot.parkVehicle("XYZ-9999");

        lot.exitVehicle("ABC-1234");

        lot.getStatistics();
    }
}
