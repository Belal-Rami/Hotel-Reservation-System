package hotel.data;
import java.util.ArrayList;

public class Room {
private String roomtype;
private double roomprice;
private double totalamenitesprice;
private double totalroomcost;
public Room(String roomtype){
    setroomtype(roomtype);
}
ArrayList<Amenity> aminites= new ArrayList<>();
ArrayList<Double> aminitesprices= new ArrayList<Double>();
public String getroomtype(){
    return roomtype;
}
public void setroomtype(String r){
    this.roomtype=r;
}
public void setroomprice(double p) {
    this.roomprice = p;
}
public void addamenity(String amenityname,double price){
    aminites.add(new Amenity(amenityname));
    aminitesprices.add(price);
}
public void removeamenity(int index){
    aminites.remove(index);
    aminitesprices.remove(index);
}
public double allroomcost(){
    for(double f:aminitesprices){
        totalamenitesprice=+f;
    }
   return totalroomcost=totalamenitesprice+roomprice;
}
    public double gettotalroomcost(){
        return totalroomcost;
    }
    public double getamenitesprice(){
        return totalamenitesprice;
    }
    public double getroomprice(){
        return roomprice;
    }
}
