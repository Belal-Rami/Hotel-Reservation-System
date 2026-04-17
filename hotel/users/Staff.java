package hotel.users;

import java.time.LocalDate;
import hotel.enums.Role;

public abstract class Staff {
    private String username;
    private String password;
    private LocalDate dateOfBirth;
    private Role role;
    private int workingHours;
    
    
}
