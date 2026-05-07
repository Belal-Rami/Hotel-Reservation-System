package hotel.gui.controllers;

public class ReservationRow {

    private String guestName;
    private int roomNumber;
    private String checkIn;
    private String checkOut;
    private String status;

    public ReservationRow(String guestName, int roomNumber, String checkIn, String checkOut, String status) {
        this.guestName = guestName;
        this.roomNumber = roomNumber;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.status = status;
    }

    public String getGuestName() {
        return guestName;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}