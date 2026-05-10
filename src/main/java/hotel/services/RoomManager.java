package hotel.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import hotel.data.Amenity;
import hotel.data.Reservation;
import hotel.data.Room;
import hotel.data.RoomType;
import hotel.gui.GuiData;
import hotel.users.Guest;

public class RoomManager {
    //The array list of rooms in the hotel
    private ArrayList<Room> rooms;
    private ArrayList<Reservation> reservations;

    //CONSTRUCTOR
    public RoomManager(ArrayList<Room> rooms, ArrayList<Reservation> reservations, ArrayList<Guest> guests) {
        this.rooms = rooms;
        this.reservations = reservations;
    }

    // SETTER for the array list
    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    //CRUD METHODS for the rooms themselves
    public void addroom(Room room){
        rooms.add(room);
    }

    public void removeroom(Room room){
        for(int i=0 ; i<rooms.size() ; i++){
            if(rooms.get(i).getRoomNum()==room.getRoomNum()){
                rooms.remove(room);
                System.out.println("Room "+room+" removed successfully!");
            }
            else{
                System.out.println("Room doesnot exist!");
            }
        }
    }

    public void findroom(Room room){
        for(int i=0;i<rooms.size();i++){
            if(rooms.get(i).getRoomNum()==room.getRoomNum()){
                System.out.println("Room " + rooms.get(i).getRoomNum()+" found");
            }else{
                System.out.println("room not found!");
            }
        }
    }

    public void roominfo(Room room){
        for(int i=0;i<rooms.size();i++){
            if(rooms.get(i).getRoomNum()==room.getRoomNum()){
                System.out.println(room.toString());
            }
        }
    }

    public void allroominfo(){
        for(Room r:rooms){
            System.out.println(r.toString());
        }
    }

    public void addAmenityToRoom(Room room, Amenity amenity) {
        room.createAmenity(amenity);
    }

    public void deleteAmenity(Amenity amenity) {
        for (Room r : rooms) {
            r.deleteAmenity(amenity);
        }
    }

    public void changeRoomType(RoomType type, Room room) {
        room.setRoomType(type);
    }

    public void deleteRoomType(RoomType oldRoomType, RoomType newRoomType) {
        for (Room r : rooms) {
            if (r.getRoomType() == oldRoomType) {
                r.setRoomType(newRoomType);
            }
        }
    }

    // RANDOM ID GENERATOR for reservations
    Set<Integer> usedIDs = new HashSet<>();
    Random rand = new Random();

    public int generateID() {
        int id;
        do {
            id = 10000 + rand.nextInt(90000);
        } while (usedIDs.contains(id));
        usedIDs.add(id);
        return id;
    }

    // RANDOM ID GENERATOR for guests
    Set<Integer> usedGuestIDs = new HashSet<>();

    public int generateGuestId() {
        int id;
        do {
            id = 10000 + rand.nextInt(90000);
        } while (usedGuestIDs.contains(id));
        usedGuestIDs.add(id);
        return id;
    }

    public void checkRoomStatus(LocalDate date) {
        for (Room room : rooms) {
            room.setAvailable(true);
            for (Reservation r : reservations) {
                if (r.getRoom().equals(room)) {
                    if (!(date.isBefore(r.getCheckIn()) || date.isEqual(r.getCheckOut()) || date.isAfter(r.getCheckOut()))) {
                        room.setAvailable(false);
                        break;
                    }
                }
            }
            System.out.println("Room " + room.getRoomNum() + ": " +
                    (room.getAvailable()? "Available" : "Occupied"));
        }
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        reservation.setReservationID(generateID());
        // Loop to find the guest by ID and add reservation to their list
        Guest guest = GuiData.guestManager.getGuestById(reservation.getGuestId());
        if (guest != null) {
            guest.addReservation(reservation);
        }
    }

    // FIXED: was reservations.size(), caused IndexOutOfBoundsException
    public boolean removeReservation(int reservationID, String password) {
        for (int i = reservations.size() - 1; i >= 0; i--) {
            Reservation r = reservations.get(i);
            // Loop to find the guest by ID to verify password
            Guest guest = GuiData.guestManager.getGuestById(r.getGuestId());
            if (guest != null &&
                    r.getReservationID() == reservationID &&
                    guest.getPassword().equals(password)) {
                reservations.remove(i);
                guest.getReservations().remove(r);
                System.out.println("Reservation removed successfully!");
                return true;
            }
        }
        System.out.println("Invalid ID or password.");
        return false;
    }

    public boolean removeReservation(int reservationID) {
        for (int i = reservations.size() - 1; i >= 0; i--) {
            Reservation r = reservations.get(i);
            if (r.getReservationID() == reservationID) {
                reservations.remove(i);
                Guest guest = GuiData.guestManager.getGuestById(r.getGuestId());
                if (guest != null) {
                    guest.getReservations().remove(r);
                }
                return true;
            }
        }
        System.out.println("Invalid ID.");
        return false;
    }

    public void viewGuestReservations(Guest guest) {
        ArrayList<Reservation> guestRes = guest.getReservations();
        if (guestRes.isEmpty()) {
            System.out.println("No reservations found.");
            return;
        }
        for (Reservation r : guestRes) {
            System.out.println("Reservation ID: " + r.getReservationID() +
                    ", Room: " + r.getRoom().getRoomNum() +
                    ", Check-in: " + r.getCheckIn() +
                    ", Check-out: " + r.getCheckOut() +
                    ", Status: " + r.getStatus());
        }
    }

    public void createRoom(Room room) {
        rooms.add(room);
        System.out.println("Room " + room.getRoomNum() + " added successfully.");
    }

    public Room readRoom(String roomNumber) {
        for (Room r : rooms) {
            if (r.getRoomNum().equals(roomNumber)) return r;
        }
        return null;
    }

    public void updateRoom(String roomNumber, Room updatedData) {
        Room existing = readRoom(roomNumber);
        if (existing != null) {
            existing.setRoomType((RoomType) updatedData.getRoomType());
            System.out.println("Room updated.");
        } else {
            System.out.println("Room not found.");
        }
    }

    public void deleteRoom(String roomNumber) {
        Room r = readRoom(roomNumber);
        if (r != null) {
            rooms.remove(r);
            System.out.println("Room deleted.");
        }
    }

    public ArrayList<Reservation> getReservations() {
        return reservations;
    }

    public ArrayList<Room> getRooms(){
        return rooms;
    }

}