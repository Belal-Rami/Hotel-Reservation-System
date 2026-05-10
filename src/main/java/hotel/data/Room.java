package hotel.data;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Room {
    // 1. DATA FIELDS
    private String roomNum;
    private RoomType roomType;
    private Boolean isAvailable = true;  // FIXED: default to true, not false
    private Long duration;
    private LocalDate checkOut;
    private List<LocalDate> reservedDates;
    private ArrayList<Amenity> roomAmenities = new ArrayList<>();

    // 2. CONSTRUCTOR
    public Room(String roomNum, RoomType roomType, LocalDate checkIn, LocalDate checkOut) {
        this.roomNum = roomNum;
        this.roomType = roomType;
        this.checkOut = checkOut;
        duration = ChronoUnit.DAYS.between(checkIn, checkOut);
    }

    // This no-argument constructor is needed for Gson to work
    public Room() {
    }

    public boolean isReservedOn(LocalDate date) {
        return reservedDates != null && reservedDates.contains(date);
    }

    // 3. GETTERS
    public RoomType getRoomType() {
        return roomType;
    }

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
    public void setAmenities(ArrayList<Amenity> amenities) {
        this.roomAmenities = amenities;
    }

    public String getTypeName() {
        return roomType.getName();
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }
    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    // FIXED: room is occupied only if date falls strictly within [checkIn, checkOut)
    public String getStatus(LocalDate date, ArrayList<Reservation> reservations) {
        if (reservations == null) return "Available";
        for (Reservation r : reservations) {
            if (r.getRoom() == null) continue;
            if (r.getRoom().getRoomNum().equals(this.roomNum)) {
                LocalDate in  = r.getCheckIn();
                LocalDate out = r.getCheckOut();
                if (in == null || out == null) continue;
                // occupied only if date is within [checkIn, checkOut)
                if (!date.isBefore(in) && date.isBefore(out)) {
                    return "Occupied";
                }
            }
        }
        return "Available";
    }

    @Override
    public String toString() {
        return "Room [roomNum=" + roomNum + ", roomType=" + roomType + ", isAvailable=" + isAvailable + ", duration="
                + duration + ", roomAmenities=" + roomAmenities + ", amenitiesPriceperDay()=" + amenitiesPriceperDay()
                + ", totalPricePerDay()=" + totalPricePerDay() + ", totalPrice()=" + totalPrice() + "]";
    }

    public double getPrice() {
        return roomType.getPrice();
    }
}