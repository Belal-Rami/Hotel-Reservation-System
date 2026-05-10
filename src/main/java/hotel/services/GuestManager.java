package hotel.services;

import java.util.*;
import hotel.users.Guest;

public class GuestManager {


    private ArrayList<Guest> guests;
    public ArrayList<Guest> getGuests(){
        return guests;
    }

    public GuestManager(ArrayList<Guest> guests) {
        this.guests = guests;
    }

    public boolean usernameExists(String username){
        for(Guest g : guests){
            if(g.getUsername() != null && g.getUsername().equalsIgnoreCase(username)){
                return true;
            }
        }
        return false;
    }
    public Guest loginGuest(String username, String password){
        for(Guest g: guests){
            if(g.getUsername() != null && g.getPassword() != null &&
                    g.getUsername().equalsIgnoreCase(username) && g.getPassword().equals(password)){
                return g;
            }
        }
        return null;
    }
public void setBalance(Guest guest, double amount){
        guest.setBalance(amount);
}

public void registerGuest(Guest newGuest){
        if(usernameExists(newGuest.getUsername())){
            System.out.println("Guest already exists");
            return;
        }
        else{
        guests.add(newGuest);
    }
}
    public Guest getGuestById(int guestId) {
        for (int i = 0; i < guests.size(); i++) {
            if (guests.get(i).getGuestId() == guestId) {
                return guests.get(i);
            }
        }
        return null;
    }
}



