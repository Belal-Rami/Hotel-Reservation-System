package hotel.users;

import hotel.data.HotelDatabase;
import hotel.data.Reservation;
import hotel.data.Room1;
import hotel.enums.Role;
import java.time.LocalDate;
import hotel.enums.Status;

public class Receptionist extends Staff {

    public Receptionist(String username, String password, LocalDate dateOfBirth, int workingHours) throws InvalidInputException {
        super(username, password, dateOfBirth, Role.RECEPTIONIST, workingHours);
    }

    // Welcome message.
    @Override
    public void showDashboard() {
        System.out.println("Welcome to the Receptionist Desk, " + getUsername());
    }

    //Validates the reservation and marks the assigned room as occupied to begin the guest's stay.
    public void manageCheckIn(Reservation reservation, HotelDatabase db) throws RoomNotAvailableException {
        Status ReservationStatus = null;
        if (reservation.getStatus() != ReservationStatus.CONFIRMED) {
            throw new IllegalStateException("Reservation is not confirmed. Cannot check-in.");
        }
        Room1 room = reservation.getRoom();
        if (!room.getAvailable()) {
            throw new RoomNotAvailableException("Room " + room.getRoomNum() + " is currently occupied or under maintenance.");
        }

        room.setAvailable(false);
        System.out.println("Guest " + reservation.getGuest().getUsername() + " checked into room " + room.getRoomNum());
    }

    //Finalizes the reservation and restores the room status to available for future bookings.
    public void manageCheckOut(Reservation reservation, HotelDatabase db) {
        Room1 room = reservation.getRoom();
        room.setAvailable(true);
        Object ReservationStatus = new Object();
        reservation.setStatus((Status) ReservationStatus);
        System.out.println("Guest " + reservation.getGuest().getUsername() + " checked out of room " + room.getRoomNum());
    }
}

