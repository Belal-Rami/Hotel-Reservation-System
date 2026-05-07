package hotel.gui.controllers;

import hotel.data.Reservation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.io.IOException;

public class ManageReservationsController {
    @FXML
    private TableView<ReservationRow> reservationTable;
    @FXML
    private TableColumn<ReservationRow, String> guestNameColumn;
    @FXML
    private TableColumn<ReservationRow, Integer> roomNumberColumn;
    @FXML
    private TableColumn<ReservationRow, String > checkInColumn;
    @FXML
    private TableColumn<ReservationRow, String> checkOutColumn;
    @FXML
    private TableColumn<ReservationRow, String> statusColumn;
    private ObservableList<ReservationRow> reservations;
    @FXML
    private void initialize() {
        guestNameColumn.setCellValueFactory(new PropertyValueFactory<>("guestName"));
        roomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        checkInColumn.setCellValueFactory(new PropertyValueFactory<>("checkIn"));
        checkOutColumn.setCellValueFactory(new PropertyValueFactory<>("checkOut"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        reservations = FXCollections.observableArrayList();

        reservations.add(new ReservationRow("Ahmed", 101, "2026-05-10", "2026-05-15", "Pending"));
        reservations.add(new ReservationRow("Mona", 205, "2026-05-11", "2026-05-13", "Confirmed"));
        reservations.add(new ReservationRow("Omar", 310, "2026-05-12", "2026-05-16", "Pending"));

        reservationTable.setItems(reservations);
    }
    @FXML
    public void goBack(ActionEvent e) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotel/gui/scenes/receptionist-dashboard.fxml"));
    Scene scene = new Scene(loader.load(),600,400);
    Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
    }
    @FXML
    public void confirm(ActionEvent e)throws Exception{
        ReservationRow selected=reservationTable.getSelectionModel().getSelectedItem();
        if(selected==null){
            System.out.println("please choose");
            return;
        }
        selected.setStatus("Confirmed");
        reservationTable.refresh();
    }
    @FXML
    public void cancel(ActionEvent e) throws Exception {
        ReservationRow selected= reservationTable.getSelectionModel().getSelectedItem();
        if(selected==null) {
            System.out.println("please choose");
            return;
        }
        selected.setStatus("canceled");
        reservationTable.refresh();
    }

}
