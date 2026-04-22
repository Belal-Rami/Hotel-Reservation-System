package hotel.interfaces;

import hotel.data.HotelDatabase;

public interface Manageable {
    void viewAllGuests(HotelDatabase db);

    void viewAllRooms(HotelDatabase db);

    void viewAllReservations(HotelDatabase db);
}
