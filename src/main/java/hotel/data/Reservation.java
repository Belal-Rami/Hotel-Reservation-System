package hotel.data;

import java.time.LocalDate;

import hotel.enums.Status;
import hotel.users.Guest;

public class Reservation {
    // Data fields.
    // Stores only the guest's ID instead of the full Guest object to avoid circular reference
    private int guestId;
    private Room assignedRoom;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Status status;
    private int reservationID;

    // Parameterized constructor to initialize data.
    public Reservation(Guest guest, Room room, LocalDate checkIn, LocalDate checkOut, Status status) {
        this.checkInDate= checkIn;
        this.checkOutDate=checkOut;
        this.assignedRoom=room;
        this.guestId = guest.getGuestId();
        this.status=status;
    }

    // Getter and Setter methods.
    public int getGuestId() {
        return guestId;
    }
    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    public Room getRoom() {
        return assignedRoom;
    }
    public void setRoom(Room assignedRoom) {
        this.assignedRoom = assignedRoom;
    }

    public LocalDate getCheckIn(){
        return checkInDate;
    }
    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOut(){
        return checkOutDate;
    }
    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }

    public int getReservationID() { return reservationID; }
    public void setReservationID(int reservationID) { this.reservationID = reservationID; }
}