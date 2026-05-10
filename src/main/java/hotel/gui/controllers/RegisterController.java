package hotel.gui.controllers;

import hotel.gui.GuiData;
import hotel.gui.GuiMain;
import hotel.users.Guest;
import hotel.enums.Gender; // Ensure this Enum exists in your project!

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.time.LocalDate;

public class RegisterController {

    // These variables MUST match the fx:id attributes in the FXML perfectly
    @FXML private TextField regUsernameField;
    @FXML private PasswordField regPasswordField;
    @FXML private TextField regAddressField;
    @FXML private DatePicker regDobPicker;
    @FXML private RadioButton maleRadio;
    @FXML private RadioButton femaleRadio;
    @FXML private ToggleGroup genderGroup;

    @FXML
    void handleRegister(ActionEvent event) throws Exception {
        // 1. Gather Data
        String user = regUsernameField.getText().trim();
        String pass = regPasswordField.getText().trim();
        String address = regAddressField.getText().trim();
        LocalDate dob = regDobPicker.getValue();

        // 2. Validate Empty Text Fields
        if (user.isEmpty() || pass.isEmpty() || address.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "Please fill in all text fields.");
            return;
        }

        // 3. Validate Date of Birth
        if (dob == null) {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "Please select your date of birth.");
            return;
        }
        if (dob.isAfter(LocalDate.now().minusYears(18))) {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "You must be at least 18 years old to register.");
            return;
        }

        // 4. Validate Gender Selection
        if (genderGroup.getSelectedToggle() == null) {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "Please select a gender.");
            return;
        }
        Gender gender = maleRadio.isSelected() ? Gender.MALE : Gender.FEMALE;

        // 5. Create Guest (Make sure your Guest class constructor expects these exact 5 parameters!)
        Guest newGuest = new Guest(user, pass, dob, address, gender);

        // 6. Save Data
        GuiData.guestManager.registerGuest(newGuest);
        GuiMain.saveData(GuiData.database);

        // 7. Success Message & Navigation
        showAlert(Alert.AlertType.INFORMATION, "Success", "Account created successfully!");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotel/gui/scenes/guest-dashboard.fxml"));
        Scene scene = new Scene(loader.load(), 1920, 1080);

        kkkcontroller kk = loader.getController();
        kk.setCurrentguest(newGuest); 

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void Back(ActionEvent e) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotel/gui/scenes/GuestOptions.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load(), 1920, 1080));
        stage.show();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}