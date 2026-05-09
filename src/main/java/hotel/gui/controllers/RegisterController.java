package hotel.gui.controllers;

import hotel.services.GuestManager;
import hotel.users.Guest;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.util.ArrayList;

    public class RegisterController {
        @FXML private TextField regUsernameField;
        @FXML private TextField regPasswordField;


        private GuestManager guestManager = new GuestManager(new ArrayList<>());

        public RegisterController(TextField regPasswordField) {
            this.regPasswordField = regPasswordField;
        }

        @FXML
        void handleRegister() {
            String user = regUsernameField.getText();
            String pass = regPasswordField.getText();

            // Create a new Guest object
            Guest newGuest = new Guest(user, pass);

            guestManager.registerGuest(newGuest);

            System.out.println("Guest registered successfully!");
        }
    }

