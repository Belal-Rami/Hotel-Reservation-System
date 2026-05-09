package hotel.users;

import java.time.LocalDate;
import java.util.ArrayList;
import hotel.services.RoomManager;
import hotel.data.Reservation;
import hotel.enums.Gender;

public class Guest {
    // Data fields.
    private String username;
    private String password;
    private LocalDate dateOfBirth;
    private double balance;
    private String address;
    private Gender gender;

    private ArrayList<Reservation> Greservations = new ArrayList<Reservation>();

    public void addReservation(Reservation r) {
        Greservations.add(r);
    }

    public ArrayList<Reservation> getReservations() {
        return Greservations;
    }

    //Parameterized constructor.
    public Guest(String username,String password,LocalDate dateOfBirth,double balance,String address,Gender gender){
        setUsername(username);
        setPassword(password);
        setDateOfBirth(dateOfBirth);
        setBalance(balance);
        setAddress(address);
        setGender(gender);
    }

    //This no-argument constructor is needed for Gson to work
    public Guest() {
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

    public double  getBalance(){
        return balance
                ;
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

       // toString() method to print data info.
    @Override
    public String toString(){
        return "Username: " + username + "\nDate of birth: " + dateOfBirth + "\nBalance: " + balance+"$"
                + "\nAddress: " + address + "\nGender: "+ gender;
    }
}


