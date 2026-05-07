package hotel.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ReceptionistDashboardController {
public void openreservation(ActionEvent e) throws Exception{
    FXMLLoader load= new FXMLLoader(getClass().getResource("/hotel/gui/scenes/ViewReservation.fxml"));
    Scene scene = new Scene(load.load());
    Stage stage=(Stage)((Node)e.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
}
    public void openrooms(ActionEvent e) throws Exception {
        System.out.println("Rooms button clicked");

        FXMLLoader load = new FXMLLoader(
                getClass().getResource("/hotel/gui/scenes/ViewRooms.fxml")
        );

        System.out.println("FXML location = " + getClass().getResource("/hotel/gui/scenes/ViewRooms.fxml"));

        Scene scene = new Scene(load.load());

        System.out.println("FXML loaded successfully");

        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

        System.out.println("Scene changed");
    }

public void openguests(ActionEvent e)throws Exception{
    FXMLLoader load = new FXMLLoader(
            getClass().getResource("/hotel/gui/scenes/ViewGuests.fxml"));
    Scene scene = new Scene(load.load());
    Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();

    stage.setScene(scene);
    stage.show();
}
public void logout(ActionEvent e){
    System.out.println("logout clicked");
}
}
