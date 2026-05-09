package hotel.gui.controllers;

import hotel.enums.Role;
import hotel.gui.GuiData;
import hotel.gui.UserSession;
import hotel.services.GuestManager;
import hotel.services.StaffManager;
import hotel.users.Guest;
import hotel.users.Staff;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

public class LoginController {
        @FXML private TextField usernameField;
        @FXML private PasswordField passwordField;

       // private GuestManager guestManager = new GuestManager(new java.util.ArrayList<>());
       // private StaffManager staffManager = new StaffManager(new java.util.ArrayList<>());

        @FXML
        void handleLogin() {
            String user = usernameField.getText();
            String pass = passwordField.getText();

            if (UserSession.currentRole == Role.GUEST) {
                Guest g = GuiData.guestManager.loginGuest(user, pass);
                if (g != null) {
                    System.out.println("Guest Login Success!");
                    // Switch to Guest Dashboard
                } else {
                    showError("Invalid Guest Credentials");
                }
            } else {
                // For both Staff and Admin
                Staff s = GuiData.staffManager.loginStaff(user, pass);
                if (s != null) {
                    System.out.println("Staff/Admin Login Success!");
                    // Switch to appropriate Dashboard
                } else {
                    showError("Invalid Staff/Admin Credentials");
                }
            }
        }

        private void showError(String msg) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(msg);
            alert.show();
        }
}



