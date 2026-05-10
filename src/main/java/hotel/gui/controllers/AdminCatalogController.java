package hotel.gui.controllers;

import java.io.IOException;
import java.util.List;

import hotel.data.Amenity;
import hotel.data.RoomType;
import hotel.gui.GuiData;
import hotel.gui.GuiMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Node;

public class AdminCatalogController {

    @FXML
    private Button createAmenityBtn;

    @FXML
    private Button createRoomTypeBtn;

    @FXML
    private VBox amenitiesGrid;

    @FXML
    private VBox roomTypesGrid;

    @FXML
    public void initialize() {
        refreshGrids();
    }


    public void refreshGrids() {
        // Clear the old cards first so they don't stack infinitely
        roomTypesGrid.getChildren().clear();
        amenitiesGrid.getChildren().clear();

        // 1. Populate Room Types
        List<RoomType> roomTypes = GuiData.roomTypeManager.getRoomTypes();
        for (RoomType rt : roomTypes) {
            loadCard(rt.getName(), rt.getPrice(), rt, roomTypesGrid);
        }

        // 2. Populate Amenities
        List<Amenity> amenities = GuiData.amenityManager.getAmenities();
        for (Amenity am : amenities) {
            loadCard(am.getName(), am.getPrice(), am, amenitiesGrid);
        }
    }

    /**
     * Generic helper to "Stamp" a card into a specific grid.
     */
    private void loadCard(String name, double price, Object data, VBox targetGrid) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotel/gui/scenes/admin-catalog-card.fxml"));
            Parent cardRow = loader.load();

            // Connect the Mini-Brain
            AdminCatalogCardController cardController = loader.getController();
            
            // Fill the card with data and give it a reference to THIS controller
            cardController.setData(name, price, data);
            cardController.setMasterController(this); // This allows the "Delete" to call refreshGrids()

            targetGrid.getChildren().add(cardRow);
            
        } catch (IOException e) {

        }
    }
    @FXML
    void createNewRoomType(ActionEvent event) {
            try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotel/gui/scenes/admin-create-item.fxml"));
            Parent root = loader.load();

            AdminCreateItemController popupController = loader.getController();
            popupController.setFormContext("Create New Room Type", "Room Name:");

            Stage popupStage = new Stage();
            popupStage.setTitle("New Room Type");
            popupStage.setScene(new Scene(root));
            popupStage.initModality(javafx.stage.Modality.APPLICATION_MODAL); 
            popupStage.showAndWait(); 

            if (popupController.isSaved()) {
                String name = popupController.getNewName();
                double price = popupController.getNewPrice();
                
                GuiData.roomTypeManager.createRoomType(name, price);
                // GuiData.roomTypeManager.saveToDisk(); // Uncomment if your manager has this method
            
                GuiMain.saveData(GuiData.database);
                refreshGrids(); 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void createNewAmenity(ActionEvent event) {
        System.out.println("The Create Room button was clicked!"); // ADD THIS LINE
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotel/gui/scenes/admin-create-item.fxml"));
            Parent root = loader.load();

            AdminCreateItemController popupController = loader.getController();
            popupController.setFormContext("Create New Amenity", "Amenity Name:");

            Stage popupStage = new Stage();
            popupStage.setTitle("New Amenity");
            popupStage.setScene(new Scene(root));
            popupStage.initModality(javafx.stage.Modality.APPLICATION_MODAL); 
            popupStage.showAndWait(); 

            if (popupController.isSaved()) {
                String name = popupController.getNewName();
                double price = popupController.getNewPrice();
                

                GuiData.amenityManager.createAmenity(name, price);
            GuiMain.saveData(GuiData.database);
                
                refreshGrids(); 
            }
        } catch (Exception e) {
            System.out.println("Error opening create amenity popup: " + e.getMessage());
        }
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


