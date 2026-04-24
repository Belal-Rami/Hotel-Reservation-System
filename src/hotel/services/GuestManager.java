package hotel.services;

import hotel.users.Guest;
import java.util.*;

public class GuestManager {

    private List<Guest> guests;

    public GuestManager(List<Guest> guests) {
        this.guests = guests;
    }

      public boolean usernameExists(String username){
        for(Guest g : guests){
            if(g.getUsername().equalsIgnoreCase(username)){
                return true;
            }

        }
        return false;
    }
public Guest loginGuest(String username, String password){
        for(Guest g: guests){
            if(g.getUsername().equalsIgnoreCase(username)&&g.getPassword().equals(password)){
                return g;
            }
        }
        return null;

}

public void registerGuest(Guest newGuest){
        if(usernameExists(newGuest.getUsername())){
            System.out.println("Guest already exists");
            return;
        }
        guests.add(newGuest);
    }
    
    
}
