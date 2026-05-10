package hotel.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AdminDashboardController {
    public void roomManagment(ActionEvent e) throws Exception{
        FXMLLoader load= new FXMLLoader(getClass().getResource("/hotel/gui/scenes/admin-rooms.fxml"));
        Scene scene = new Scene(load.load(),1920,1080);
        Stage stage=(Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    public void staffRegistration(ActionEvent e) throws Exception{
        FXMLLoader load= new FXMLLoader(getClass().getResource("/hotel/gui/scenes/admin-registeration.fxml"));
        Scene scene = new Scene(load.load(),1920,1080);
        Stage stage =(Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    public void systemCatalog(ActionEvent e) throws Exception{
      FXMLLoader load= new FXMLLoader(getClass().getResource("/hotel/gui/scenes/admin-catalog.fxml"));
      Scene scene = new Scene(load.load(),1920,1080);
      Stage stage =(Stage)((Node)e.getSource()).getScene().getWindow();
      stage.setScene(scene);
      stage.show();
    }
    public void logout(ActionEvent e) throws Exception{
        FXMLLoader load= new FXMLLoader(getClass().getResource("/hotel/gui/scenes/start-screen.fxml"));
        Scene scene = new Scene(load.load(),1920,1080);
        Stage stage =(Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();;
    }
}