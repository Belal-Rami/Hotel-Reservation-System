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
import hotel.data.HotelDatabase;

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
    public static HotelDatabase database;
/* 
   static
    {
        RoomType single= new RoomType("Single",1200);
        LocalDate checkIn= LocalDate.of(2026,11,21);
        LocalDate checkout= LocalDate.of(2027,11,21);
        Room room1= new Room("101",single,checkIn,checkout);
        LocalDate DateOfBirth= LocalDate.of(2007,11,21);
        Guest guest=new Guest("Jana","124928",DateOfBirth,20000, "Cairo",FEMALE);
        Reservation res1= new Reservation(guest,room1,checkIn,checkout,Status.PENDING);
        roomManager.addReservation(res1);



        RoomType singlee= new RoomType("Single",1200);
        LocalDate checkInn= LocalDate.of(2022,11,21);
        LocalDate checkoutt= LocalDate.of(2025,11,21);
        Room room11= new Room("111",singlee,checkInn,checkoutt);
        LocalDate DateOfBirth1= LocalDate.of(2007,11,21);
        Guest guest1=new Guest("Adel","12071980",DateOfBirth1,50000, "Cairo",MALE);
        Reservation res2= new Reservation(guest1,room11,checkInn,checkoutt,Status.PENDING);
        roomManager.addReservation(res2);
        roomManager.addroom(room1);
        roomManager.addroom(room11);
        guestManager.registerGuest(guest1);
        guestManager.registerGuest(guest);



    }
    */

}