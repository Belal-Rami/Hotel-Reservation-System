package hotel.gui.controllers;

import hotel.enums.Role;
import hotel.gui.GuiData;
import hotel.gui.UserSession;
import hotel.users.Guest;
import hotel.users.Staff;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField     usernameField;
    @FXML private PasswordField passwordField;

    @FXML
    void handleLogin(ActionEvent e) throws Exception {
        String user = usernameField.getText().trim();
        String pass = passwordField.getText().trim();

        if (UserSession.currentRole == Role.GUEST) {

            Guest g = GuiData.guestManager.loginGuest(user, pass);
            if (g != null) {
                System.out.println("Guest Login Success!");

                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/hotel/gui/scenes/kkk.fxml")
                );
                Scene scene = new Scene(loader.load(), 1920, 1080);

                // ── ONE controller reference, pass guest to it ──────────
                kkkcontroller kk = loader.getController();
                kk.setCurrentguest(g);
                // kkkcontroller should also implement setCurrentguest1 OR
                // MakeReservationController should be set through kkk's own init.
                // If kkk.fxml embeds MakeReservation as a sub-scene, handle it there.

                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();

            } else {
                showError("Invalid Guest Credentials");
            }

        } else {
            // Staff / Admin
            Staff s = GuiData.staffManager.loginStaff(user, pass);
            if (s != null) {
                System.out.println("Staff/Admin Login Success!");

                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/hotel/gui/scenes/choose.fxml")
                );
                Scene scene = new Scene(loader.load(), 1920, 1080);

                // Pass staff context if choose-controller needs it
                // ChooseController ctrl = loader.getController();
                // ctrl.setStaff(s);

                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();

            } else {
                showError("Invalid Staff/Admin Credentials");
            }
        }
    }

    @FXML
    public void Back(ActionEvent e) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/hotel/gui/scenes/start-screen.fxml")
        );
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load(), 1920, 1080));
        stage.show();
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(msg);
        alert.show();
    }
}