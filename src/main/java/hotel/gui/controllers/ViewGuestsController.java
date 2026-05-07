package hotel.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewGuestsController {

    public void backToDashboard(ActionEvent e) throws Exception {
        FXMLLoader load = new FXMLLoader(
                getClass().getResource("/hotel/gui/scenes/receptionist-dashboard.fxml")
        );

        Scene scene = new Scene(load.load());

        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();

        stage.setScene(scene);
        stage.show();
    }
}