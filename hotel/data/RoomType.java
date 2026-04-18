package hotel.data;

public class RoomType {
    String name;
    double price;
    public void singleroom(Room s){
      s.setroomtype("single");
      s.setroomprice(1000.0);
    }
    public void doubleroom(Room s){
        s.setroomtype("Double");
        s.setroomprice(2000.0);
    }

    public void suitroom(Room s){
        s.setroomtype("Suit");
        s.setroomprice(4000.0);
    }
}
