package hotel.gui.controllers;

import hotel.users.Guest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class kkkcontroller {
    private Guest currentguest;

     public void setCurrentguest(Guest currentguest) {
        this.currentguest = currentguest;
    }
    @FXML
    public void RoomB(ActionEvent e) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/hotel/gui/scenes/sceneROoms.fxml")
        );

        Scene scene = new Scene(loader.load(), 1920, 1080);

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void ProfileB(ActionEvent e) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/hotel/gui/scenes/Guestprofile.fxml")
        );

        Scene scene = new Scene(loader.load(), 1920, 1080);

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void BalanceB(ActionEvent e) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/hotel/gui/scenes/MyBalance.fxml")
        );

        Scene scene = new Scene(loader.load(), 1920, 1080);

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    public void Back(ActionEvent e) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/hotel/gui/scenes/GuestOptions.fxml")
        );

        Scene scene = new Scene(loader.load(), 1920, 1080);

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
