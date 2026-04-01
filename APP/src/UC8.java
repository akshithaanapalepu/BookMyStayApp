import java.util.*;

// Reservation Model
class ReservationRecord {
    private String reservationId;
    private String customerName;
    private String roomType;

    public ReservationRecord(String reservationId, String customerName, String roomType) {
        this.reservationId = reservationId;
        this.customerName = customerName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getRoomType() {
        return roomType;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Customer: " + customerName +
                ", Room Type: " + roomType;
    }
}

// Booking History (Storage)
class BookingHistory {
    private List<ReservationRecord> reservations;

    public BookingHistory() {
        reservations = new ArrayList<>();
    }

    // Add confirmed reservation
    public void addReservation(ReservationRecord reservation) {
        reservations.add(reservation);
    }

    // Retrieve all reservations
    public List<ReservationRecord> getAllReservations() {
        return new ArrayList<>(reservations); // return copy to prevent modification
    }
}

// Booking Report Service
class BookingReportService {

    // Display all bookings
    public void displayAllBookings(List<ReservationRecord> reservations) {
        System.out.println("\n--- Booking History ---");

        if (reservations.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (ReservationRecord r : reservations) {
            System.out.println(r);
        }
    }

    // Generate summary report
    public void generateSummaryReport(List<ReservationRecord> reservations) {
        System.out.println("\n--- Booking Summary Report ---");

        Map<String, Integer> roomTypeCount = new HashMap<>();

        for (ReservationRecord r : reservations) {
            roomTypeCount.put(
                    r.getRoomType(),
                    roomTypeCount.getOrDefault(r.getRoomType(), 0) + 1
            );
        }

        for (String type : roomTypeCount.keySet()) {
            System.out.println(type + " Rooms Booked: " + roomTypeCount.get(type));
        }

        System.out.println("Total Reservations: " + reservations.size());
    }
}

// Main Class
public class UC8 {

    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Simulated confirmed bookings
        history.addReservation(new ReservationRecord("DEL-101", "Alice", "DELUXE"));
        history.addReservation(new ReservationRecord("STD-201", "Bob", "STANDARD"));
        history.addReservation(new ReservationRecord("DEL-102", "Charlie", "DELUXE"));
        history.addReservation(new ReservationRecord("SUI-301", "David", "SUITE"));

        // Admin views booking history
        List<ReservationRecord> reservations = history.getAllReservations();

        reportService.displayAllBookings(reservations);

        // Generate summary report
        reportService.generateSummaryReport(reservations);
    }
}