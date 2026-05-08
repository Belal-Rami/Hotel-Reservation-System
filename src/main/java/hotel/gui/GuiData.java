package hotel.gui;

import hotel.data.Reservation;
import hotel.data.Room;
import hotel.data.RoomType;
import hotel.enums.Gender;
import hotel.enums.Status;
import hotel.services.AmenityManager;
import hotel.services.GuestManager;
import hotel.services.RoomManager;
import hotel.services.RoomTypeManager;
import hotel.services.StaffManager;
import hotel.users.Guest;

import java.time.LocalDate;
import java.util.ArrayList;

import static hotel.enums.Gender.FEMALE;
import static hotel.enums.Gender.MALE;

public class GuiData {

    public static RoomManager roomManager;
    public static AmenityManager amenityManager;
    public static GuestManager guestManager;
    public static StaffManager staffManager;
    public static RoomTypeManager roomTypeManager;

    

   

}