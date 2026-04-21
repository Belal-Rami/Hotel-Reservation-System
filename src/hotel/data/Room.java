package hotel.data;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Room {
    // Data Fields
    private int roomnum;
    private String roomtype;
    private double roomprice;
    double sumperday;
    double totalperday;
    double total;
    Long duration;
    private ArrayList<Amenity> amenities = new ArrayList<>();

    // Parametarized constructor to initialize data.
    public Room(int num, RoomType type, LocalDate checkin, LocalDate checkout) {
        this.roomnum = num;
        this.roomtype = type.getname();
        this.roomprice = type.getprice();
        duration = ChronoUnit.DAYS.between(checkin, checkout);
    }

    public double totalpriceperday() {
        totalperday += sumperday;
        return totalperday;
    }

    public double aamenitiespriceperday() {
        for (Amenity p : amenities) {
            sumperday += p.getprice();
        }
        return sumperday;
    }

    public void Displayroomdata() {
        System.out.println("Room Number: " + getroomnum() + " | Room type: " + getroomtype());
        System.out.println("---Amenities---");
        for (Amenity p : amenities) {
            System.out.println(p.getname());
        }
    }

    public void totalprice() {
        total = totalperday * duration;
    }

    public void invoice() {
        System.out.println("Your trip lasted " + duration + " days");
        System.out.println("the cost of room perday: " + roomprice);
        System.out.println("the total cost of room through the whole trip is : " + roomprice + "x" + duration + " = " + roomprice * duration);
        System.out.println("The cost of amenities perday: " + sumperday);
        System.out.println("the total cost of amenities through the whole trip is : " + sumperday + "x" + duration + " = " + sumperday * duration);
        System.out.println("The total cost of your trip is: " + total);
        System.out.println("--------------THANK YOU--------------");
    }

    // Getter and Setter methods.
    public String getroomtype() {
        return roomtype;
    }

    public void setroomtype(RoomType type) {
        roomtype = type.getname();
        roomprice = type.getprice();
    }

    public double getroomprice() {
        return roomprice;
    }

    public void setRoomprice(double roomprice) {
        this.roomprice = roomprice;
    }

    public int getroomnum() {
        return roomnum;
    }

    public void setRoomnum(int roomnum) {
        this.roomnum = roomnum;
    }

    public ArrayList<Amenity> getamenities() {
        return amenities;
    }

    public void setAmenities(ArrayList<Amenity> amenities) {
        this.amenities = amenities;
    }

    public int getRoomNumber() {
        return  roomnum ;
    }
}
