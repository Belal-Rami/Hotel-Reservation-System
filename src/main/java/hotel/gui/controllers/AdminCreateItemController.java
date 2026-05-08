package hotel.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AdminCreateItemController {

    @FXML private Label headerLabel;
    @FXML private Label namePromptLabel;
    @FXML private TextField nameField;
    @FXML private TextField priceField;

    private boolean saved = false;

    public void setFormContext(String headerText, String promptText) {
        this.headerLabel.setText(headerText);
        this.namePromptLabel.setText(promptText);
    }

    @FXML
    void handleSave(ActionEvent event) {
        if (nameField.getText().isEmpty() || priceField.getText().isEmpty()) {
            // Optional: Add an error label here later to warn the user
            return; 
        }
        
        // Ensure price is an actual number before allowing save
        try {
            Double.parseDouble(priceField.getText());
        } catch (NumberFormatException e) {
            return;
        }

        this.saved = true;
        closeWindow();
    }

    @FXML
    void handleCancel(ActionEvent event) {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    // --- GETTERS for the Main Controller ---
    public boolean isSaved() { return saved; }
    
    public String getNewName() { return nameField.getText(); }

    public double getNewPrice() {
        return Double.parseDouble(priceField.getText());
    }
}