package hotel.gui.controllers;

import hotel.enums.Role;
import hotel.gui.GuiData;
import hotel.gui.UserSession;
import hotel.services.GuestManager;
import hotel.services.StaffManager;
import hotel.users.Guest;
import hotel.users.Staff;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class LoginController {
        @FXML private TextField usernameField;
        @FXML private PasswordField passwordField;

       // private GuestManager guestManager = new GuestManager(new java.util.ArrayList<>());
       // private StaffManager staffManager = new StaffManager(new java.util.ArrayList<>());

        @FXML
        void handleLogin(ActionEvent e)throws Exception {
            String user = usernameField.getText();
            String pass = passwordField.getText();

            if (UserSession.currentRole == Role.GUEST) {
                Guest g = GuiData.guestManager.loginGuest(user, pass);
                if (g != null) {

                    System.out.println("Guest Login Success!");
                    FXMLLoader loader = new FXMLLoader(
                            getClass().getResource("/hotel/gui/scenes/kkk.fxml")
                    );

                    Scene scene = new Scene(loader.load(), 1920, 1080);

                    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                    kkkcontroller kk= loader.getController();
                    kk.setCurrentguest(g);
                    MakeReservationController mm= loader.getController();
                    mm.setCurrentguest1(g);
                    // Switch to Guest Dashboard
                } else {
                    showError("Invalid Guest Credentials");
                }
            } else {
                // For both Staff and Admin
                Staff s = GuiData.staffManager.loginStaff(user, pass);
                if (s != null) {
                    System.out.println("Staff/Admin Login Success!");
                    FXMLLoader loader = new FXMLLoader(
                            getClass().getResource("/hotel/gui/scenes/choose.fxml")
                    );

                    Scene scene = new Scene(loader.load(), 1920, 1080);

                    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
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
    public void Back(ActionEvent e) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/hotel/gui/scenes/start-screen.fxml")
        );

        Scene scene = new Scene(loader.load(), 1920, 1080);

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}



