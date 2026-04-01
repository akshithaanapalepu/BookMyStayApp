import java.util.*;

// Custom Exception for Invalid Bookings
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Inventory Service with Validation
class InventoryService {
    private Map<String, Integer> roomInventory;

    public InventoryService() {
        roomInventory = new HashMap<>();
        roomInventory.put("DELUXE", 2);
        roomInventory.put("STANDARD", 3);
        roomInventory.put("SUITE", 1);
    }

    // Validate room type
    public void validateRoomType(String roomType) throws InvalidBookingException {
        if (!roomInventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }
    }

    // Allocate room if available
    public void allocateRoom(String roomType) throws InvalidBookingException {
        validateRoomType(roomType);
        int count = roomInventory.get(roomType);
        if (count <= 0) {
            throw new InvalidBookingException("No rooms available for type: " + roomType);
        }
        roomInventory.put(roomType, count - 1);
    }

    public int getAvailableRooms(String roomType) {
        return roomInventory.getOrDefault(roomType, 0);
    }
}

// Booking History
class BookingHistory {
    private List<Reservation> reservations;

    public BookingHistory() {
        reservations = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public List<Reservation> getAllReservations() {
        return new ArrayList<>(reservations);
    }
}

// Booking Service with Validation
class BookingService {
    private InventoryService inventory;
    private BookingHistory history;

    public BookingService(InventoryService inventory, BookingHistory history) {
        this.inventory = inventory;
        this.history = history;
    }

    public void createBooking(String reservationId, String customerName, String roomType) {
        try {
            inventory.allocateRoom(roomType);
            Reservation reservation = new Reservation(reservationId, customerName, roomType);
            history.addReservation(reservation);
            System.out.println("Booking confirmed: " + reservation);
        } catch (InvalidBookingException e) {
            System.out.println("Booking failed: " + e.getMessage());
        }
    }
}

// Main Class for UC9
public class UC9 {
    public static void main(String[] args) {
        InventoryService inventoryService = new InventoryService();
        BookingHistory bookingHistory = new BookingHistory();
        BookingService bookingService = new BookingService(inventoryService, bookingHistory);

        // Test bookings (some valid, some invalid)
        bookingService.createBooking("DEL-101", "Alice", "DELUXE");     // valid
        bookingService.createBooking("STD-201", "Bob", "STANDARD");      // valid
        bookingService.createBooking("SUI-301", "Charlie", "SUITE");     // valid
        bookingService.createBooking("STD-202", "David", "PRESIDENT");   // invalid room type
        bookingService.createBooking("DEL-102", "Eve", "DELUXE");        // valid
        bookingService.createBooking("DEL-103", "Frank", "DELUXE");      // no rooms available

        // Display all confirmed bookings
        System.out.println("\nAll confirmed bookings:");
        for (Reservation r : bookingHistory.getAllReservations()) {
            System.out.println(r);
        }
    }
}
