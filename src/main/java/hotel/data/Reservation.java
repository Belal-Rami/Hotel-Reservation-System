package hotel.data;

import java.time.LocalDate;

import hotel.enums.Status;
import hotel.users.Guest;

public class Reservation {
    // Data fields.
    private Guest assignedGuest;
    private Room assignedRoom;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Status status;

    // Parameterized constructor to initialize data.
    public Reservation(Guest guest,Room room, LocalDate checkIn, LocalDate checkOut, Status status) {
        this.checkInDate= checkIn;
        this.checkOutDate=checkOut;
        this.assignedRoom=room;
        this.assignedGuest=guest;
        this.status=status;
    }
    // Getter and Setter methods.
    public Guest getGuest() {
        return assignedGuest;
    }
    public void setGuest(Guest assignedGuest) {
        this.assignedGuest = assignedGuest;
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



}
