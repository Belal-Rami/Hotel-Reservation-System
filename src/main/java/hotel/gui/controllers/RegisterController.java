package hotel.gui.controllers;

import hotel.gui.GuiData;
import hotel.users.Guest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController {

    @FXML private TextField regUsernameField;
    @FXML private TextField regPasswordField;

    // !! Do NOT create a new GuestManager here — always use the shared one !!
    // private GuestManager guestManager = new GuestManager(...);  ← WRONG
    // Use GuiData.guestManager everywhere so data is shared across the whole app.

    @FXML
    void handleRegister(ActionEvent event) throws Exception {
        String user = regUsernameField.getText().trim();
        String pass = regPasswordField.getText().trim();

        // Register in the SHARED manager so the guest is visible app-wide
        Guest newGuest = new Guest(user, pass);
        GuiData.guestManager.registerGuest(newGuest);
        System.out.println("Guest registered: " + user);

        // Navigate to the guest dashboard and pass the new guest
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/hotel/gui/scenes/kkk.fxml")
        );
        Scene scene = new Scene(loader.load(), 1920, 1080);

        kkkcontroller kk = loader.getController();
        kk.setCurrentguest(newGuest);          // ← pass the actual object

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void Back(ActionEvent e) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/hotel/gui/scenes/GuestOptions.fxml")
        );
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load(), 1920, 1080));
        stage.show();
    }
}