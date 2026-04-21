package hotel.users;

import hotel.enums.Role;
import java.time.LocalDate;

public abstract class Staff {
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
        public Staff(String username, String password, LocalDate dateOfBirth, Role role, int workingHours) {
            setUsername(username);
            setPassword(password);
            setDateOfBirth(dateOfBirth);
            setRole(role);
            setWorkingHours(workingHours);
        }

        // Getter and Setter methods.
        public String getUsername() {
            return username;
        }
        public void setUsername(String username) {
            if (username == null || username.trim().isEmpty()) {
                throw new IllegalArgumentException("Username cannot be empty.");
            }
            this.username = username;
        }

        public String getPassword() {
            return password;
        }
        public void setPassword(String password) {
            if (password == null || password.length() < 6) {
                throw new IllegalArgumentException("Password must be at least 6 characters long.");
            }
            this.password = password;
        }

        public LocalDate getDateOfBirth() {
            return dateOfBirth;
        }
        public void setDateOfBirth(LocalDate dateOfBirth) {

            this.dateOfBirth = dateOfBirth;
        }

        public Role getRole() {
            return role;
        }
        public void setRole(Role role) {
            if (role == null) {
                throw new IllegalArgumentException("Role cannot be null.");
            }
            this.role = role;
        }

        public int getWorkingHours() {
            return workingHours;
        }
        public void setWorkingHours(int workingHours) {
            if (workingHours < 0 || workingHours > 80) {
                throw new IllegalArgumentException("Invalid working hours.");
            }
            this.workingHours = workingHours;
        }

        // toString() method to print data.
        @Override
        public String toString(){
            return "Username: "+username+"\nDate of Birth: "+dateOfBirth+"\nRole: "+role+"\nWorkingHours: "+workingHours;
        }
}
