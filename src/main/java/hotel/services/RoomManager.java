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
import hotel.users.Guest;

public class RoomManager {
    //The array list of rooms in the hotel
    private ArrayList<Room> rooms;
    private ArrayList<Reservation> reservations = new ArrayList<>();
    public ArrayList<Reservation> getReservations() {
        return reservations;
    }
    public ArrayList<Room> getRooms(){
        return rooms;
    }
    //CONSTRUCTOR
    public RoomManager(ArrayList<Room> rooms) {
        this.rooms = rooms;
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
            //Here, == is used since the actual reference of the two objects are being compared
            if (r.getRoomType() == oldRoomType) {
                r.setRoomType(newRoomType);
            }
        }

    }


    // RANDOM ID GENERATOR
    Set<Integer> usedIDs = new HashSet<>();
    Random rand = new Random();

    public int generateID() {
        int id;

        do {
            id = 10000 + rand.nextInt(90000);
        } while (usedIDs.contains(id)); // keep trying if duplicate

        usedIDs.add(id);
        return id;
    }

    public void checkRoomStatus(LocalDate date) {

        for (Room room : rooms) {

             room.setAvailable(true) ;

            for (Reservation r : reservations) {
                if (r.getRoom().equals(room)) {

                    // if date is inside reservation period → NOT available
                    if (!(date.isBefore(r.getCheckIn()) || date.isEqual(r.getCheckOut()) || date.isAfter(r.getCheckOut()))) {
                        room.setAvailable(false) ;
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
        reservation.getGuest().addReservation(reservation);

    }

    public boolean removeReservation(int reservationID, String password) {

        for (int i = reservations.size(); i>=0; i--) {
            Reservation r = reservations.get(i);

            if (r.getReservationID() == reservationID &&
                    r.getpassword().equals(password)) {

                reservations.remove(i);
                r.getGuest().getReservations().remove(r);
                System.out.println("Reservation removed successfully!");
                return true;
            }
        }

        System.out.println("Invalid ID or password.");
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
}




