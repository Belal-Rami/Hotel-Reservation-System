package hotel.services;

import java.util.ArrayList;
import hotel.data.Room;
import hotel.data.RoomType;

public class RoomManager {

    private ArrayList<Room> rooms = new ArrayList<>();

    public void changeroomtype(Room room, RoomType type){
        room.setRoomType(type);
    }

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
                System.out.println("Room " + rooms.get(i).getRoomNum()+" founed");
            }else{
                System.out.println("room not found!");
            }
        }
    }

    public void roominfo(Room room){
        for(int i=0;i<rooms.size();i++){
            if(rooms.get(i).getRoomNum()==room.getRoomNum()){
                room.Displayroomdata();

            }
        }
    }

    public void allroominfo(){
        for(Room r:rooms){
            r.Displayroomdata();
        }
    }

    public ArrayList<Room> getrooms(){

        return rooms;

    }

    public void addRoomType(RoomType type) {
    }

    public void removeRoom(int roomNumber) {
    }

    public void listAllRooms() {
    }

    public void addRoom(Room room) {
    }

    public void updateRoom(int roomNumber, Room updatedRoom) {
    }
}
