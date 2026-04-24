package hotel.users;

import java.time.LocalDate;
import hotel.data.HotelDatabase;
import hotel.data.RoomType;
import hotel.enums.Role;
import hotel.data.Room;
import hotel.data.Amenity;
import hotel.interfaces.Manageable;
import hotel.services.RoomManager;

public class Admin extends Staff implements Manageable {
    private RoomManager roomManager;

    // Parameterized Constructor to initialize data.
    public Admin(String username, String password, LocalDate dateOfBirth, int workingHours, RoomManager roomManager) throws InvalidInputException {
        super(username, password, dateOfBirth, Role.ADMIN, workingHours);
        this.roomManager = roomManager;
    }

    // Add room to the database for the first time.
    public void createRoom(Room room, HotelDatabase db) {
        db.getRooms().add(room);
        System.out.println("Room " + room.getRoomNum() + " added successfully.");
    }

    public Room readRoom(String roomNumber, HotelDatabase db) {
        for (Room r : db.getRooms()) {
            if (r.getRoomNum().equals(roomNumber)) return r;
        }
        return null;
    }

    // Modify the details of the room that already exists in the database.
    public void updateRoom(String roomNumber, Room updatedData, HotelDatabase db) {
        Room existing = readRoom(roomNumber, db);
        if (existing != null) {
            existing.setRoomType((RoomType) updatedData.getRoomType());
            System.out.println("Room updated.");
        } else {
            System.out.println("Room not found.");
        }
    }
    // Delete room from the database.
    public void deleteRoom(String roomNumber, HotelDatabase db) {
        Room r = readRoom(roomNumber, db);
        if (r != null) {
            db.getRooms().remove(r);
            System.out.println("Room deleted.");
        }
    }

    // Adds an amenity to the database.
    public void createAmenity(String name, double price) {
        roomManager.createAmenity(name, price);

   
    }

    //Removes an amenity from the database.
    public void deleteAmenity(Amenity amenity, HotelDatabase db) {
        if (db.getAmenities().contains(amenity)) {
            db.getAmenities().remove(amenity);
            System.out.println("Amenity removed.");
        } else {
            System.out.println("Amenity not found.");
        }
    }
}
