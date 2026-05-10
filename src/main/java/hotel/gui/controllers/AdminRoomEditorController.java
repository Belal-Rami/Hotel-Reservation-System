package hotel.gui.controllers;

import hotel.data.*;
import hotel.gui.GuiData;
import hotel.gui.GuiMain;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AdminRoomEditorController {

    @FXML private Label headerLabel;
    @FXML private TextField roomNumberField;
    @FXML private ComboBox<String> roomTypeComboBox;
    @FXML private VBox amenitiesBox;
    @FXML private Label errorLabel;

    private Room editingRoom = null;
    private boolean saved = false;
    private List<CheckBox> amenityCheckBoxes = new ArrayList<>();

    // Called right before the window opens
    public void initializeForm(Room room) {
        this.editingRoom = room;

        // 1. Load Room Types into Dropdown
        for (RoomType rt : GuiData.roomTypeManager.getRoomTypes()) {
            roomTypeComboBox.getItems().add(rt.getName());
        }

        // 2. Load Amenities as Checkboxes
        for (Amenity amenity : GuiData.amenityManager.getAmenities()) {
            CheckBox cb = new CheckBox(amenity.getName());
            cb.getStyleClass().add("text-body");
            amenityCheckBoxes.add(cb);
            amenitiesBox.getChildren().add(cb);
        }

        // 3. If editing, pre-fill the data
        if (room != null) {
            headerLabel.setText("Edit Room " + room.getRoomNum());
            roomNumberField.setText(String.valueOf(room.getRoomNum()));
            roomNumberField.setDisable(true); // Lock room number from being changed
            roomTypeComboBox.setValue(room.getRoomType().getName());

            // Check the boxes for amenities this room already has
            for (CheckBox cb : amenityCheckBoxes) {
                for (Amenity roomAmenity : room.getAmenities()) {
                    if (cb.getText().equals(roomAmenity.getName())) {
                        cb.setSelected(true);
                        break;
                    }
                }
            }
        } else {
            headerLabel.setText("Create New Room");
        }
    }

    @FXML
    void handleSave() {
        if (roomNumberField.getText().isEmpty() || roomTypeComboBox.getValue() == null) {
            errorLabel.setText("Please fill out the Room Number and Type.");
            return;
        }

        try {
            String roomNum = roomNumberField.getText().trim();
            
            // --- NEW VALIDATION: Check for duplicate room number ---
            // We only need to check this if we are creating a new room
            if (editingRoom == null) {
                for (Room existingRoom : GuiData.roomManager.getRooms()) {
                    // Convert both to strings for safe comparison
                    if (String.valueOf(existingRoom.getRoomNum()).equalsIgnoreCase(roomNum)) {
                        errorLabel.setText("Error: Room " + roomNum + " already exists!");
                        return; // Stop the save process
                    }
                }
            }
            // --------------------------------------------------------

            // 1. Find the selected RoomType using a local loop instead of a manager method
            String selectedTypeName = roomTypeComboBox.getValue();
            RoomType selectedType = null;
            for (RoomType rt : GuiData.roomTypeManager.getRoomTypes()) {
                if (rt.getName().equals(selectedTypeName)) {
                    selectedType = rt;
                    break;
                }
            }

            // 2. Gather all checked amenities using a local loop
            List<Amenity> selectedAmenities = new ArrayList<>();
            for (CheckBox cb : amenityCheckBoxes) {
                if (cb.isSelected()) {
                    for (Amenity amenity : GuiData.amenityManager.getAmenities()) {
                        if (amenity.getName().equals(cb.getText())) {
                            selectedAmenities.add(amenity);
                            break;
                        }
                    }
                }
            }

            // Fallback check just in case something went wrong
            if (selectedType == null) {
                errorLabel.setText("Error: Could not find the selected Room Type.");
                return;
            }

            if (editingRoom == null) {
                LocalDate today = LocalDate.now();
                // Creating a brand new room
                Room newRoom = new Room(roomNum, selectedType, today, today); // Assuming check-in and check-out dates are the same for new rooms
                // Safe copy into an ArrayList
                newRoom.setAmenities(new ArrayList<>(selectedAmenities)); 
                GuiData.roomManager.createRoom(newRoom);
            } else {
                // Updating existing room
                editingRoom.setRoomType(selectedType);
                editingRoom.setAmenities(new ArrayList<>(selectedAmenities));
                // GuiData.roomManager.updateRoom(editingRoom); // Uncomment if needed
            }
            
            GuiMain.saveData(GuiData.database);
            saved = true;
            closeWindow();
            
        } catch (NumberFormatException e) {
            errorLabel.setText("Room number must be an integer.");
        } catch (Exception e) {
            errorLabel.setText("Error saving room details.");
            e.printStackTrace();
        }
    }

    @FXML void handleCancel() { closeWindow(); }
    private void closeWindow() { ((Stage) roomNumberField.getScene().getWindow()).close(); }
    public boolean isSaved() { return saved; }
}