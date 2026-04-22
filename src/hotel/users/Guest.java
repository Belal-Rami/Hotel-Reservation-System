package hotel.users;
import java.time.LocalDate;
import hotel.enums.Gender;
public class Guest {
    private String username;
    private String password;
    private LocalDate dateOfBirth;
    private double balance;
    private String address;
    private Gender gender;
    public Guest(String username,String password,LocalDate dateOfBirth,double balance,String address,Gender gender){
        setUsername(username);
        setPassword(password);
        setDateOfBirth(dateOfBirth);
        setBalance(balance);
        setAddress(address);
        setGender(gender);
    }
public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
    public LocalDate getDateOfBirth(){
        return dateOfBirth;
    }
    public double  getBalance(){
        return balance;
    }
    public String getAddress(){
        return address;
    }
    public Gender getGender(){
        return gender;
    }
    public void setUsername(String username){
        if(username==null||username.trim().isEmpty()){
            throw new IllegalArgumentException("Username cannot be empty");
        }
        this.username=username;
    }
    public void setPassword(String password){
        if(password==null||password.trim().isEmpty()){
            throw new IllegalArgumentException("Password cannot be empty");
        }
        this.password=password;
    }
    public void setDateOfBirth(LocalDate dateOfBirth){
        if(dateOfBirth==null){
            throw new IllegalArgumentException("Date of birth cannot be empty");
        }
        this.dateOfBirth=dateOfBirth;
    }
    public void setBalance(double balance){
        if(balance<0){
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        this.balance=balance;
    }
    public void setAddress(String address){
        if(address==null||address.trim().isEmpty()){
            throw new IllegalArgumentException("Address cannot be empty");
        }
        this.address=address;
    }
    public void setGender(Gender gender){
        if(gender==null){
            throw new IllegalArgumentException("Gender cannot be empty");
        }
        this.gender=gender;
    }
    @Override
    public String toString(){
        return "Username: " + username + "\nDate of birth: " + dateOfBirth + "\nBalance: " + balance+"$"
                + "\nAddress: " + address + "\nGender: "+ gender;
    }
}
