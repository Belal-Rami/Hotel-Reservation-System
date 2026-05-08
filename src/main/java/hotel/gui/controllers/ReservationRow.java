package hotel.gui.controllers;

import hotel.data.Reservation;
import hotel.enums.Status;
import javafx.animation.Animation;

public class ReservationRow {
    private Reservation reservation;
    public ReservationRow(Reservation reservation){
    this.reservation=reservation;
    }
    public Reservation getReservation(){
        return reservation;
    }
    public String getGuestName(){
        return reservation.getGuest().getUsername();
    }
    public String getRoomNumber(){
        return reservation.getRoom().getRoomNum();
    }
    public int getReservationId(){
        return reservation.getReservationID();
    }
    public String getCheckIn(){
        return reservation.getCheckIn().toString();
    }
    public String getCheckOut(){
        return reservation.getCheckOut().toString();
    }
    public String getStatus(){
        return reservation.getStatus().toString();
    }
}