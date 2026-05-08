package hotel.data;

import hotel.gui.GuiData;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Room {
    // 1. DATA FIELDS
    private String roomNum;
    private RoomType roomType;
    private Boolean isAvailable=false;
    private Long duration;
    private LocalDate checkOut;

    private ArrayList<Amenity> roomAmenities = new ArrayList<>();

    // 2. CONSTRUCTOR
    public Room(String roomNum, RoomType roomType, LocalDate checkIn, LocalDate checkOut) {
        this.roomNum = roomNum;
        this.roomType = roomType;
        this.checkOut=checkOut;
        duration = ChronoUnit.DAYS.between(checkIn, checkOut);
    }

        //This no-argument constructor is needed for Gson to work
    public Room() {
    }

    
    // 3. GETTERS

    public RoomType getRoomType() {
        return roomType;
    }
    //The functions that calculate the expenses of the room
    public double amenitiesPriceperDay() {
        double amenitesPricePerDay = 0;
        for (Amenity i : roomAmenities) {
            amenitesPricePerDay += i.getPrice();
        }
        return amenitesPricePerDay;
    }

    public double totalPricePerDay() {
        return roomType.getPrice() + amenitiesPriceperDay();
    }

    public double totalPrice() {
        return totalPricePerDay() * duration;
    }


    //Functions to edit the amenity list inside each room

    public void createAmenity(Amenity amenity) {
        roomAmenities.add(amenity);
    }

    public void deleteAmenity(Amenity amenity) {
        roomAmenities.remove(amenity);
    }


    public void invoice() {
        System.out.println("Your trip lasted " + duration + " days");
        System.out.println("the cost of room perday: " + totalPricePerDay());
        System.out.println("the total cost of room through the whole trip is : " + totalPricePerDay() + "x" + duration + " = " + totalPrice() * duration);
        System.out.println("The cost of amenities perday: " + amenitiesPriceperDay());
        System.out.println("the total cost of amenities through the whole trip is : " + amenitiesPriceperDay() + "x" + duration + " = " + amenitiesPriceperDay() * duration);
        System.out.println("The total cost of your trip is: " + totalPrice());
        System.out.println("--------------THANK YOU--------------");
    }

    // Getter and Setter methods.


    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }


    public Boolean getAvailable() {
        return isAvailable;
    }
    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public String getRoomNum() {
        return roomNum;
    }
    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }

    public ArrayList<Amenity> getAmenities() {
        return roomAmenities;
    }
    public String getTypeName(){
        return roomType.getName();
    }
    public LocalDate getCheckOut(){
        return checkOut;
    }
    public String getStatus(){

        if(checkOut.isBefore(LocalDate.now())){
            return "Available";
        }

        return "Occupied";
    }


    @Override
    public String toString() {
        return "Room [roomNum=" + roomNum + ", roomType=" + roomType + ", isAvailable=" + isAvailable + ", duration="
                + duration + ", roomAmenities=" + roomAmenities + ", amenitiesPriceperDay()=" + amenitiesPriceperDay()
                + ", totalPricePerDay()=" + totalPricePerDay() + ", totalPrice()=" + totalPrice() + "]";
    }
    public double getPrice(){
        return roomType.getPrice();
    }

}
