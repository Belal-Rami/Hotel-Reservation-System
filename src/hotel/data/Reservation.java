package hotel.data;

import hotel.users.Guest;
import java.time.LocalDate;

public class Reservation {
    // Data fields.
    private Guest assignedGuest;
    private Room assignedRoom;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    // Parameterized constructor to initialize data.
    public Reservation(Room room,LocalDate checkin,LocalDate checkout,Guest guest){
        this.checkInDate=checkin;
        this.checkOutDate=checkout;
        this.assignedRoom=room;
        this.assignedGuest=guest;
    }
    // Getter and Setter methods.
    public LocalDate getcheckin(){
        return checkInDate;
    }
    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getcheckout(){
        return checkOutDate;
    }
    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
}
