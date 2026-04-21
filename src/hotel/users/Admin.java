package hotel.users;

import java.time.LocalDate;
import hotel.enums.Role;
import hotel.data.Room;
import hotel.data.RoomType;
import hotel.data.Amenity;
import hotel.interfaces.Manageble;
import hotel.services.RoomManager;
import hotel.services.AmenityManager;

public class Admin extends Staff implements Manageble {

    // Parameterized Constructor to initialize data.
    public Admin(String username, String password, LocalDate dateOfBirth, int workingHours) {
        super(username, password, dateOfBirth, Role.ADMIN, workingHours);
    }


    public void createRoom(Room room, RoomManager roomManager) {
        roomManager.addRoom(room);
        System.out.println("Room " + room.getRoomNumber() + " added successfully.");
    }

    public void updateRoom(int roomNumber, Room updatedRoom, RoomManager roomManager) {
        roomManager.updateRoom(roomNumber, updatedRoom);
    }

    public void deleteRoom(int roomNumber, RoomManager roomManager) {
        roomManager.removeRoom(roomNumber);
    }

    public void addRoomType(RoomType type, RoomManager roomManager) {
        roomManager.addRoomType(type);
    }

    public void addAmenity(Amenity amenity, AmenityManager amenityManager) {
        amenityManager.addAmenity(amenity);
    }

    public void removeAmenity(String amenityName, AmenityManager amenityManager) {
        amenityManager.deleteAmenity(amenityName);
    }

    public void viewAllSystemData(RoomManager rm, AmenityManager am) {
        System.out.println("--- System Data Report ---");
        rm.listAllRooms();
        am.listAllAmenities();
    }

    // toString() method to print data.
    @Override
    public String toString() {
        return "Admin username: " + getUsername() + "\nRole: " + getRole();
    }
}
