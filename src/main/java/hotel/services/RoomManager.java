package hotel.services;

import java.util.ArrayList;

import hotel.data.Amenity;
import hotel.data.Room;
import hotel.data.RoomType;

public class RoomManager {
    //The array list of rooms in the hotel
    private ArrayList<Room> rooms;

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
}




