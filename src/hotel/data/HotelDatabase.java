package hotel.data;

import hotel.users.*;
import java.util.ArrayList;

public class HotelDatabase {

    private static ArrayList<Guest> guests = new ArrayList<>();
    private ArrayList<Room> rooms = new ArrayList<>();
    private ArrayList<Reservation> reservations = new ArrayList<>();
    private ArrayList<Amenity> amenities = new ArrayList<>();
    private ArrayList<Invoice> invoices = new ArrayList<>();
    private ArrayList<Staff> staffMembers = new ArrayList<>();

    // Getter and Setter methods.
    public ArrayList<Guest> getGuests() {
        return guests;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public ArrayList<Reservation> getReservations() {
        return reservations;
    }
    public ArrayList<Amenity> getAmenities() {
        return amenities;
    }
}
