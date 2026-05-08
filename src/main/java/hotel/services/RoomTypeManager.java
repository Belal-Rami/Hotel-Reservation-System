package hotel.services;

import java.util.ArrayList;

import hotel.data.RoomType;

public class RoomTypeManager {
    // DATA FEILDS
    private ArrayList<RoomType> roomTypes;

    // CONSTRUCTOR
    public RoomTypeManager(ArrayList<RoomType> roomTypes) {
        this.roomTypes = roomTypes;
    }

    

    public RoomType findRoomType(String name){
        for(RoomType rt: roomTypes){
            if(rt.getName().equalsIgnoreCase(name)){
                return rt; 
            }
        }
        return null;
    }

    // CRUD METHODS
        public void createRoomType(String name, double price){
            roomTypes.add(new RoomType(name, price));
    }
   
    //If the roomtype is removed and is found in as a roomtype in a room, the roomtype cannot be left empty, it has to be replaced with another roomtype.
    
    public void deleteRoomType(RoomType oldRoomType, RoomType newRoomType, RoomManager roomManager) {
               
    // Remove the room type from the hotel's list of room types
              
        boolean isRemoved = roomTypes.remove(oldRoomType);
                
                // Update all rooms that have the old room type to the new room type
                // only runs the loop if there is a room type to be removed
                //the implementation of how this is done is found in the RoomManager class, this is too make sure that each class has specific functionality.
                
            if (isRemoved) {
                roomManager.deleteRoomType(oldRoomType, newRoomType);                        
                }
            }
        
        
    public void updateRoomTypePrice(RoomType roomType, double newprice){
                roomType.setPrice(newprice);
        }



        
    public void printAll(){
        for(RoomType rt : roomTypes){
            rt.toString();
        }
    }



    public ArrayList<RoomType> getRoomTypes() {
        return roomTypes;
    }

}
