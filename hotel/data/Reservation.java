package hotel.data;
import hotel.users.Guest;

import java.time.LocalDate;

public class Reservation {
    private Guest assignedGuest;
    private Room assignedRoom;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    public Reservation(Room room,LocalDate checkin,LocalDate checkout,Guest guest){
        this.checkInDate=checkin;
        this.checkOutDate=checkout;
        this.assignedRoom=room;
        this.assignedGuest=guest;
    }
    public LocalDate getcheckin(){
        return checkInDate;
    }
    public LocalDate getcheckout(){
        return checkOutDate;
    }
 
}
