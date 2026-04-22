package hotel;
import hotel.enums.Gender;
import hotel.users.Guest;

import java.time.LocalDate;
import java.util.ArrayList;

public class HotelDatabase {
    public static ArrayList<Guest> guests= new ArrayList<>();
    static{
        guests.add(new Guest("ShahidISmail26",
                "Shahid_2006?",
                LocalDate.of(2006,7,11),
                20000000,
                "Cairo",
                Gender.MALE));
        guests.add(new Guest("Hananelderiny19"
                ,"Hanan-123$",
                LocalDate.of(1987,3,12),
                12345,
                "Alexandria",
                Gender.FEMALE));
    }
    public static boolean usernameExists(String username){
        for(Guest g : guests){
            if(g.getUsername().equalsIgnoreCase(username)){
                return true;
            }

        }
        return false;
    }
public static Guest loginGuest(String username, String password){
        for(Guest g: guests){
            if(g.getUsername().equalsIgnoreCase(username)&&g.getPassword().equals(password)){
                return g;
            }
        }
        return null;

}
public static void registerGuest(Guest newGuest){
        if(usernameExists(newGuest.getUsername())){
            System.out.println("Guest already exists");
            return;
        }
        guests.add(newGuest);
}
}
