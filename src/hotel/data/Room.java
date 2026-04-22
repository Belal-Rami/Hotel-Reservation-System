package hotel.data;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Room {
    // Data Fields
    private String roomnum;
    private String roomtype;
    private double roomprice;
    private Boolean isAvailable=false;
    double sumperday;
    double totalperday;
    double total;
    Long duration;

    private ArrayList<Amenity> amenities = new ArrayList<>();

    // Parameterized constructor to initialize data.
    public Room(String num, RoomType type, LocalDate checkin, LocalDate checkout) {
        this.roomnum = num;
        this.roomtype = type.getName();
        this.roomprice = type.getPrice();
        duration = ChronoUnit.DAYS.between(checkin, checkout);
    }

    public double totalpriceperday() {
        totalperday += sumperday;
        return totalperday;
    }

    public double aamenitiespriceperday() {
        for (Amenity p : amenities) {
            sumperday += p.getPrice();
        }
        return sumperday;
    }

    public void Displayroomdata() {
        System.out.println("Room Number: " + getRoomNum() + " | Room type: " + getRoomType());
        System.out.println("---Amenities---");
        for (Amenity p : amenities) {
            System.out.println(p.getName());
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
    public Object getRoomType() {
        return roomtype;
    }

    public void setRoomType(RoomType type) {
        roomtype = type.getName();
        roomprice = type.getPrice();
    }

    public double getRoomPrice() {
        return roomprice;
    }
    public void setRoomprice(double roomprice) {
        this.roomprice = roomprice;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }
    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public String getRoomNum() {
        return roomnum;
    }
    public void setRoomNum(String roomnum) {
        this.roomnum = roomnum;
    }

    public ArrayList<Amenity> getAmenities() {
        return amenities;
    }
    public void setAmenities(ArrayList<Amenity> amenities) {
        this.amenities = amenities;
    }


}
