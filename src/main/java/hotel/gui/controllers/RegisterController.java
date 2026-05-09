package hotel.gui.controllers;

import hotel.services.GuestManager;
import hotel.users.Guest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.ArrayList;

    public class RegisterController {
        @FXML private TextField regUsernameField;
        @FXML private TextField regPasswordField;


        private GuestManager guestManager = new GuestManager(new ArrayList<>());

//        //public RegisterController(TextField regPasswordField) {
//            this.regPasswordField = regPasswordField;
//        }

        @FXML
        void handleRegister(ActionEvent event) throws Exception {
            String user = regUsernameField.getText();
            String pass = regPasswordField.getText();

            // Create a new Guest object
            Guest newGuest = new Guest(user, pass);

            guestManager.registerGuest(newGuest);

            System.out.println("Guest registered successfully!");
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/hotel/gui/scenes/kkk.fxml")
            );

            Scene scene = new Scene(loader.load(), 1920, 1080);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        public void Back(ActionEvent e) throws Exception {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/hotel/gui/scenes/GuestOptions.fxml")
            );

            Scene scene = new Scene(loader.load(), 1920, 1080);

            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

