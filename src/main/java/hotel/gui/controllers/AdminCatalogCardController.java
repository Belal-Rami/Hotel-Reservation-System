package hotel.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import hotel.data.Amenity;
import hotel.data.RoomType;
import hotel.gui.GuiData;
import hotel.gui.GuiMain;

public class AdminCatalogCardController{


    @FXML
    private Button deleteBtn;

    @FXML
    private Button editBtn;

    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLabel;

    private AdminCatalogController masterController; 

    public void setMasterController(AdminCatalogController masterController) {
        this.masterController = masterController;
    }

    // This stores the object (Amenity or RoomType) so we know what to edit/delete
    private Object itemData; 

    // The "Pen" that fills in the "Blanks"

    public void setData(String name, double price, Object item) {
        this.nameLabel.setText(name);
        this.priceLabel.setText(String.format("$%.2f", price));
        this.itemData = item;
    }





    @FXML
    void editItem(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog(priceLabel.getText().replace("$", ""));
    
    dialog.setTitle("Edit Price");
    dialog.setHeaderText("Updating: " + nameLabel.getText());
    dialog.setContentText("Enter new price:");

    Optional<String> result = dialog.showAndWait();

    // 3. Process the result
    result.ifPresent(newPriceString -> {
        try {
            double newPrice = Double.parseDouble(newPriceString);
            
            priceLabel.setText(String.format("$%.2f", newPrice));

        if (itemData instanceof RoomType) {
            GuiData.roomTypeManager.updateRoomTypePrice((RoomType)itemData, newPrice);
            
        } else if (itemData instanceof Amenity) {
            GuiData.amenityManager.updateAmenityPrice((Amenity)itemData, newPrice);
        }

            
            
        } catch (NumberFormatException e) {

        }
    });
    GuiMain.saveData(GuiData.database);
}

    @FXML
    void deleteItem(ActionEvent event) {
        if (itemData instanceof RoomType) {
            List<RoomType> otherRoomTypes = new ArrayList<>(GuiData.roomTypeManager.getRoomTypes());
            otherRoomTypes.remove(itemData); 
            if (otherRoomTypes.isEmpty()) {
            return;
    }

    // 2. Create the ChoiceDialog
    // The first parameter is the default selection
    ChoiceDialog<RoomType> dialog = new ChoiceDialog<>(otherRoomTypes.get(0), otherRoomTypes);
    
    dialog.setTitle("Confirm Deletion");
    dialog.setHeaderText("Replacing: " + nameLabel.getText());
    dialog.setContentText("Choose a room type to transfer existing data to:");

    // 3. Show and handle the result
    Optional<RoomType> result = dialog.showAndWait();

    result.ifPresent(selectedReplacement -> {
        GuiData.roomTypeManager.deleteRoomType((RoomType)itemData, (RoomType)selectedReplacement, GuiData.roomManager);
        masterController.refreshGrids();
    });
            
        } else if (itemData instanceof Amenity) {
            GuiData.amenityManager.deleteAmenity((Amenity)itemData, GuiData.roomManager);      
            masterController.refreshGrids();   
        }
        
        GuiMain.saveData(GuiData.database);


    }

}
