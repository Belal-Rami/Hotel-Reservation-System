package hotel.services;
import java.util.ArrayList;
import hotel.data.Room;
import hotel.data.RoomType;

public class RoomManager {
    private ArrayList<Room> rooms = new ArrayList<>();
    public void changeroomtype(Room room, RoomType type){
        room.setroomtype(type);



    }
    public void addroom(Room room){
        rooms.add(room);
    }
    public void removeroom(Room room){
        for(int i=0;i<rooms.size();i++){
            if(rooms.get(i).getroomnum()==room.getroomnum()){
                rooms.remove(room);
                System.out.println("Room "+room+" removed successfully!");
            }else{
                System.out.println("Room doesnot exist!");
            }
        }
    }




    public void findroom(Room room){
        for(int i=0;i<rooms.size();i++){
            if(rooms.get(i).getroomnum()==room.getroomnum()){
                System.out.println("Room " + rooms.get(i).getroomnum()+" founed");
            }else{
                System.out.println("room not found!");
            }
        }
    }





    public void roominfo(Room room){
        for(int i=0;i<rooms.size();i++){
            if(rooms.get(i).getroomnum()==room.getroomnum()){
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

}
