package hotel.users;

import java.time.LocalDate;
import java.util.ArrayList;
import hotel.services.RoomManager;
import hotel.data.Reservation;
import hotel.enums.Gender;
import hotel.gui.GuiData;

public class Guest {
    // Data fields.
    private String username;
    private String password;
    private LocalDate dateOfBirth;
    private double balance;
    private String address;
    private Gender gender;
    private int guestId;

    private ArrayList<Reservation> Greservations = new ArrayList<Reservation>();

    // 1. Helper function to find the next ID
    private int generateNextGuestId(ArrayList<Guest> existingGuests) {
        // If the list is empty or null, start the IDs at 1
        if (existingGuests == null || existingGuests.isEmpty()) {
            return 1;
        }
        
        // Loop through the list to find the highest ID currently in use
        int highestId = 0;
        for (Guest g : existingGuests) {
            if (g.getGuestId() > highestId) {
                highestId = g.getGuestId();
            }
        }
        
        // Return the highest ID found plus 1
        return highestId + 1;
    }

    // 2. Updated Constructor (Basic)
    public Guest(String user, String pass, ArrayList<Guest> existingGuests) {
        setUsername(user);
        setPassword(pass);
        this.guestId = generateNextGuestId(existingGuests); // Assign the new ID
    }

    // 3. Updated Constructor (Parameterized)
    public Guest(String username, String password, LocalDate dateOfBirth, String address, Gender gender) {
        setUsername(username);
        setPassword(password);
        setDateOfBirth(dateOfBirth);
        setBalance(0); // Initialize balance to 0
        setAddress(address);
        setGender(gender);
        this.guestId = generateNextGuestId(GuiData.guestManager.getGuests()); // Assign the new ID
    }

    // This no-argument constructor is needed for Gson to work
    public Guest() {
    }

    public void addReservation(Reservation r) {
        Greservations.add(r);
    }

    public ArrayList<Reservation> getReservations() {
        return Greservations;
    }

    // Getter and Setter methods.
    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        if(username==null||username.trim().isEmpty()){
            throw new IllegalArgumentException("Username cannot be empty");
        }
        this.username=username;
    }

    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        if(password==null||password.trim().isEmpty()){
            throw new IllegalArgumentException("Password cannot be empty");
        }
        this.password=password;
    }

    public LocalDate getDateOfBirth(){
        return dateOfBirth;
    }
    public void setDateOfBirth(LocalDate dateOfBirth){
        if(dateOfBirth==null){
            throw new IllegalArgumentException("Date of birth cannot be empty");
        }
        this.dateOfBirth=dateOfBirth;
    }

    public double getBalance(){
        return balance;
    }
    public void setBalance(double balance){
        if(balance<0){
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        this.balance=balance;
    }

    public String getAddress(){
        return address;
    }
    public void setAddress(String address){
        if(address==null||address.trim().isEmpty()){
            throw new IllegalArgumentException("Address cannot be empty");
        }
        this.address=address;
    }

    public Gender getGender(){
        return gender;
    }
    public void setGender(Gender gender){
        if(gender==null){
            throw new IllegalArgumentException("Gender cannot be empty");
        }
        this.gender=gender;
    }

    public int getGuestId() {
        return guestId;
    }
    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    // toString() method to print data info.
    @Override
    public String toString(){
        return "ID: " + guestId + "\nUsername: " + username + "\nDate of birth: " + dateOfBirth + "\nBalance: " + balance+"$"
                + "\nAddress: " + address + "\nGender: "+ gender;
    }
}