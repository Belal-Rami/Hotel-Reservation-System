package hotel.gui.controllers;

// TODO: Ensure these imports match your actual package structure
import hotel.data.Room;
import hotel.gui.GuiData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AdminRoomsController {

    @FXML 
    private FlowPane roomsGrid;
    public void Back(ActionEvent e) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/hotel/gui/scenes/admin-dashboard.fxml")
        );

        Scene scene = new Scene(loader.load(), 1920, 1080);

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void initialize() {
        // Automatically draw the grid the second the page loads
        refreshGrid();
    }

    public void refreshGrid() {
        // 1. Clear the screen so we don't get duplicates
        roomsGrid.getChildren().clear();
        
        try {
            // 2. Loop through every physical room in your database
            for (Room room : GuiData.roomManager.getRooms()) {
                
                // Load the visual Card FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotel/gui/scenes/admin-room-card.fxml"));
                Parent card = loader.load();
                
                // Get the controller for that specific card and hand it the room data
                AdminRoomCardController cardController = loader.getController();
                cardController.setData(room, this); // 'this' lets the card trigger this refreshGrid() later
                
                // Add the finished card to the screen
                roomsGrid.getChildren().add(card);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openAddRoomPopup(ActionEvent event) {
        try {
            System.out.println("Opening Add Room Popup...");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotel/gui/scenes/admin-room-editor.fxml"));
            Parent root = loader.load();

            // Pass 'null' into initializeForm because we are creating a brand new room
            AdminRoomEditorController popupController = loader.getController();
            popupController.initializeForm(null); 

            Stage popupStage = new Stage();
            popupStage.setTitle("Add New Room");
            popupStage.setScene(new Scene(root));
            popupStage.initModality(Modality.APPLICATION_MODAL); // Freezes the background
            popupStage.showAndWait(); // Pauses the code here until the popup is closed

            // If the admin clicked "Save" (not Cancel), redraw the grid to show the new room!
            if (popupController.isSaved()) {
                refreshGrid();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}