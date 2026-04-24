package hotel.users;

import java.time.LocalDate;

import hotel.data.HotelDatabase;
import hotel.data.Reservation;
import hotel.data.Room;
import hotel.enums.Role;
import hotel.interfaces.Manageable;

public abstract class Staff implements Manageable {
        // Data fields.
        private String username;
        private String password;
        private LocalDate dateOfBirth;
        private Role role;
        private int workingHours;

        // No-arg constructor.
        public Staff(){
        }

        // Parameterized constructor to initialize data.
        public Staff(String username, String password, LocalDate dateOfBirth, Role role, int workingHours) throws InvalidInputException {
            setUsername(username);
            setPassword(password);
            setDateOfBirth(dateOfBirth);
            setRole(role);
            setWorkingHours(workingHours);
        }

    @Override
    public void viewAllGuests(HotelDatabase db) {
        System.out.println("--- Guest List ---");
        for (int i = 0; i < db.getGuests().size(); i++) {
            Guest guest = db.getGuests().get(i);
            System.out.println(guest);
        }
    }

    @Override
    public void viewAllRooms(HotelDatabase db) {
        System.out.println("--- Room List ---");
        for (int i = 0; i < db.getRooms().size(); i++) {
            Room room = db.getRooms().get(i);
            System.out.println(room);
        }
    }

    @Override
    public void viewAllReservations(HotelDatabase db) {
        System.out.println("--- Reservation List ---");
        for (int i = 0; i < db.getReservations().size(); i++) {
            Reservation res = db.getReservations().get(i);
            System.out.println(res);
        }
    }

    // Abstract method to force child classes to implement their specific dashboard.
    public abstract void showDashboard();

    // toString() method to print data info.
        @Override
        public String toString(){
        return "Username: "+username+"\nDate of Birth: "+dateOfBirth+"\nRole: "+role+"\nWorkingHours: "+workingHours;
    }

        // Getter and Setter methods.
        public String getUsername() {
            return username;
        }
        public void setUsername(String username) throws InvalidInputException {
        if (username == null || username.trim().isEmpty()) {
            throw new InvalidInputException("Username cannot be empty.");
        }
        this.username = username;
    }

        public String getPassword() {
            return password;
        }
        public void setPassword(String password) throws InvalidInputException {
        if (password == null || password.length() < 6) {
            throw new InvalidInputException("Password must be at least 6 characters long.");
        }
        this.password = password;
    }

        public LocalDate getDateOfBirth() {
            return dateOfBirth;
        }
        public void setDateOfBirth(LocalDate dateOfBirth) throws InvalidInputException {
        if (dateOfBirth == null || dateOfBirth.isAfter(LocalDate.now().minusYears(20))) {
            throw new InvalidInputException("Staff member must be at least 20 years old.");
        }
        this.dateOfBirth = dateOfBirth;
    }

        public Role getRole() {
            return role;
        }
        public void setRole(Role role) {
            this.role = role;
        }

        public int getWorkingHours() {
            return workingHours;
        }
        public void setWorkingHours(int workingHours) throws InvalidInputException {
        if (workingHours <= 0 || workingHours > 80) {
            throw new InvalidInputException("Invalid working hours,must be between 1 and 80.");
        }
        this.workingHours = workingHours;
    }
}
