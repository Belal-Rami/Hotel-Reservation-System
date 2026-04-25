package hotel.services;

import java.util.ArrayList;

import hotel.users.Staff;

public class StaffManager {
private ArrayList<Staff> staff;

    public StaffManager(ArrayList<Staff> staff) {
        this.staff = staff;
    }


    public boolean usernameExists(String username){
    for(Staff s : staff){
        if(s.getUsername().equalsIgnoreCase(username)){
           return true;
        }

    }
    return false;
    }
public boolean loginStaff(String username, String password){
        for(Staff s: staff){
            if(s.getUsername().equalsIgnoreCase(username)&&s.getPassword().equals(password)){
                return true;
            }
        }
        return false;

}

public void registerStaff(Staff newStaff){
        if(usernameExists(newStaff.getUsername())){
            System.out.println("Staff member already exists");
            return;
        }
        else{
        staff.add(newStaff);
    }
}

    
}
