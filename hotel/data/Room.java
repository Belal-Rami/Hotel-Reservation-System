package hotel.data;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Room {
    private int roomnum;
    private String roomtype;
    private double roomprice;
    double sumperday;
    double totalperday;
    double total;
    Long duration;
    private ArrayList<Amenity> amenities = new ArrayList<>();


    public Room(int num,RoomType type,LocalDate checkin,LocalDate checkout){
        this.roomnum=num;
        this.roomtype = type.getName();
        this.roomprice = type.getPrice();
        duration= ChronoUnit.DAYS.between(checkin,checkout);

    }




    public void setroomtype(RoomType type){
        roomtype = type.getName();
        roomprice = type.getPrice();
    }



    public double getroomprice(){
        return roomprice;
    }
    public int getroomnum(){
        return roomnum;
    }
    public String getroomtype(){
        return roomtype;
    }



    public double aamenitiespriceperday(){
        for(Amenity p:amenities){
            sumperday+=p.getprice();
        }
        return sumperday;
    }



    public double totalpriceperday(){
        totalperday+=sumperday;
        return totalperday;
    }

    public ArrayList<Amenity> getamenities(){
        return amenities;
    }




    public void Displayroomdata() {
        System.out.println("Room Number: " + getroomnum() + " | Room type: " + getroomtype());
        System.out.println("---Amenities---");
        for (Amenity p:amenities) {
            System.out.println(p.getName());
        }
    }



    public void totalprice(){
        total=totalperday*duration;
    }
    public void invoice(){
        System.out.println("Your trip lasted "+duration+" days");
        System.out.println("the cost of room perday: "+roomprice);
        System.out.println("the total cost of room through the whole trip is : "+roomprice+"x"+duration +" = "+roomprice*duration);
        System.out.println("The cost of amenities perday: "+sumperday);
        System.out.println("the total cost of amenities through the whole trip is : "+sumperday+"x"+duration +" = "+sumperday*duration);
        System.out.println("The total cost of your trip is: "+total);
        System.out.println("--------------THANK YOU--------------");
    }
}
