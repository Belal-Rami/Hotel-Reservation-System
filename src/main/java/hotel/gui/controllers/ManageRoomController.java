package hotel.gui.controllers;

import hotel.data.Room;
import hotel.gui.GuiData;
import hotel.services.RoomManager;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ManageRoomController
{
    @FXML
    private TableView<Room> roomTable;
    @FXML
    private TableColumn <Room,String> roomNumber;
    @FXML
    private TableColumn<Room, String> roomType;
    @FXML
    private TableColumn<Room, Double> roomPrice;
    @FXML
    private TableColumn<Room, String >roomStatus;
    @FXML
    private TextField searchField;
    public void initialize(){
        roomNumber.setCellValueFactory(new PropertyValueFactory<>("roomNum"));
        roomType.setCellValueFactory(new PropertyValueFactory<>("typeName"));
        roomPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        roomStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        ObservableList list =FXCollections.observableArrayList();
        for(Room r: GuiData.roomManager.getRooms()) {
            list.add(r);
        }
        roomTable.setItems(list);
    }
    @FXML
    private void showAllRooms(){
        ObservableList<Room> roomList= FXCollections.observableArrayList(GuiData.roomManager.getRooms());
        roomTable.setItems(roomList);
    }
    @FXML
    public void Back(ActionEvent e) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/hotel/gui/scenes/receptionist-dashboard.fxml")
        );

        Scene scene = new Scene(loader.load(), 1920, 1080);

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void searchByNum() {
        String num = searchField.getText();
        if (num.isEmpty()) {
            System.out.println("please enter id");
            return;
        }
        ObservableList<Room> filtered = FXCollections.observableArrayList();

        for (Room room : GuiData.roomManager.getRooms()) {
            if (room.getRoomNum() .equals(String.valueOf(num))) {
                filtered.add(room);
            }
        }
        roomTable.setItems(filtered);
        if (filtered.isEmpty()) {
            System.out.println("No room was found");
        }
    }

}
