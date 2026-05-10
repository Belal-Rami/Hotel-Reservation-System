package hotel.gui.controllers;

// TODO: Ensure these imports match your actual package structure
import hotel.users.Admin; 
import hotel.users.Receptionist;
import hotel.users.Staff;
import hotel.gui.GuiData;
// import hotel.exceptions.InvalidInputException; // Add your exception import here
import hotel.gui.GuiMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.time.LocalDate;

public class AdminRegisterController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private DatePicker dobPicker;
    @FXML private TextField hoursField;
    
    @FXML private ToggleGroup roleGroup;
    @FXML private RadioButton adminRadio;
    @FXML private RadioButton receptionistRadio;
    
    @FXML private Label errorLabel;

    @FXML
    void handleRegister(ActionEvent event)throws Exception {
        // 1. Reset error label
        errorLabel.setText("");

        // 2. Extract text from fields
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        LocalDate dob = dobPicker.getValue();
        String hoursText = hoursField.getText().trim();

        // 3. Basic UI Null Checks (Ensuring fields aren't empty before sending to the constructor)
        if (username.isEmpty() || password.isEmpty() || hoursText.isEmpty()) {
            errorLabel.setText("Error: All fields must be filled out.");
            return;
        }

        if (dob == null) {
            errorLabel.setText("Error: Please select a Date of Birth.");
            return;
        }

        int workingHours;
        try {
            workingHours = Integer.parseInt(hoursText);
        } catch (NumberFormatException e) {
            errorLabel.setText("Error: Working hours must be a valid number.");
            return;
        }

        // 4. Object Creation & Backend Validation
        Staff newStaff;
        try {
            // This is where your constructor throws the InvalidInputException if it fails
            if (adminRadio.isSelected()) {
                newStaff = new Admin(username, password, dob, workingHours);
            } else {
                newStaff = new Receptionist(username, password, dob, workingHours);
            }
            
        } catch (Exception e) { 
            // Catch the InvalidInputException and show its message to the user!
            // (Change 'Exception' to 'InvalidInputException' if you imported it)
            errorLabel.setText("Error: " + e.getMessage());
            return;
        }

        // 5. Save to System
        // Note: Make sure GuiData.staffManager has an addStaff method or similar
        GuiData.staffManager.registerStaff(newStaff); 
                    GuiMain.saveData(GuiData.database);

                FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/hotel/gui/scenes/admin-dashboard.fxml")
        );

        Scene scene = new Scene(loader.load(), 1920, 1080);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    
    }


    private void closeWindow() {
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.close();
    }

            @FXML
    public void Back(ActionEvent e) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/hotel/gui/scenes/admin-dashboard.fxml")
        );

        Scene scene = new Scene(loader.load(), 1920, 1080);

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}