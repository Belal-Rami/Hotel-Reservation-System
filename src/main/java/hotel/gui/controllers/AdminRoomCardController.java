package hotel.gui.controllers;

// TODO: Ensure these imports match your actual package structure
import hotel.data.Room;
import hotel.gui.GuiData;
import hotel.gui.GuiMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AdminRoomCardController {

    @FXML private Label roomNumberLabel;
    @FXML private Label roomTypeLabel;
    @FXML private Label amenitiesLabel;

    private Room room;
    private AdminRoomsController parentController;

    // The Main Grid calls this method to hand the data to the card
    public void setData(Room room, AdminRoomsController parentController) {
        this.room = room;
        this.parentController = parentController;

        // 1. Set the basic labels
        roomNumberLabel.setText("Room " + room.getRoomNum());
        roomTypeLabel.setText(room.getRoomType().getName());

        // 2. Format the amenities list into a clean, comma-separated string
        StringBuilder amenitiesText = new StringBuilder();
        for (int i = 0; i < room.getAmenities().size(); i++) {
            amenitiesText.append(room.getAmenities().get(i).getName());
            if (i < room.getAmenities().size() - 1) {
                amenitiesText.append(", ");
            }
        }
        
        // 3. Display the amenities (or a fallback message if it has none)
        if (amenitiesText.length() == 0) {
            amenitiesLabel.setText("No specific amenities");
        } else {
            amenitiesLabel.setText(amenitiesText.toString());
        }
    }

    @FXML
    void handleEdit(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotel/gui/scenes/admin-room-editor.fxml"));
            Parent root = loader.load();

            // Pass this specific room's data into the popup so it can pre-fill the fields!
            AdminRoomEditorController popupController = loader.getController();
            popupController.initializeForm(this.room); 

            Stage popupStage = new Stage();
            popupStage.setTitle("Edit Room " + room.getRoomNum());
            popupStage.setScene(new Scene(root));
            popupStage.initModality(Modality.APPLICATION_MODAL); // Freeze background
            popupStage.showAndWait(); // Wait for user to close popup

            // If the admin saved changes, tell the Main Grid to refresh everything
            if (popupController.isSaved()) {
                parentController.refreshGrid();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleDelete(ActionEvent event) {
        // 1. Remove this specific room from the database
        // Note: Check what your actual delete method is called in RoomManager. 
        // It might be removeRoom(room) or deleteRoom(room.getRoomNumber())
        GuiData.roomManager.removeroom(room); 
        GuiMain.saveData(GuiData.database);

        // 2. Tell the Main Grid to redraw itself so the card disappears from the screen
        parentController.refreshGrid();
    }
}