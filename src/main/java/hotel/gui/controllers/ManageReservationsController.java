package hotel.gui.controllers;
import hotel.data.Reservation;
import hotel.gui.GuiData;
import hotel.services.RoomManager;
import hotel.data.Reservation;
import hotel.enums.Status;
import hotel.services.RoomManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;

public class ManageReservationsController {

    @FXML
    private TableView<ReservationRow> reservationTable;
    @FXML
    private TextField searchIdField;
    @FXML
    private TableColumn<ReservationRow,String> reservationIdColumn;
    @FXML
    private TableColumn<ReservationRow, String> guestNameColumn;

    @FXML
    private TableColumn<ReservationRow, String> roomNumberColumn;

    @FXML
    private TableColumn<ReservationRow, String> checkInColumn;

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
        reservationIdColumn.setCellValueFactory(new PropertyValueFactory<>("reservationId"));
        reservations = FXCollections.observableArrayList();
        for(Reservation r: GuiData.roomManager.getReservations() ){
            reservations.add(new ReservationRow(r));
        }
        reservationTable.setItems(reservations);
        System.out.println("Items count = " + reservationTable.getItems().size());
    }
    @FXML
    public void searchById(ActionEvent e){
        String Idtext=searchIdField.getText();
        if(Idtext==null){
            System.out.println("No id entered");
            return;
        }
        int id;
        try{
            id=Integer.parseInt(Idtext.trim());
        }catch(NumberFormatException ex){
            System.out.println("cannot have any letters");
            return;
        }
        ObservableList<ReservationRow> filtered= FXCollections.observableArrayList();
        for(ReservationRow row:reservations){
            if(row.getReservationId()==id) {
            filtered.add(row);
            }
        }
        reservationTable.setItems(filtered);
        if(filtered.isEmpty()){
            System.out.println("No id was found");
        }
    }
    @FXML
    public void showAll(ActionEvent e){
        reservationTable.setItems(reservations);
        searchIdField.clear();
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
    public void checkIn(ActionEvent e) {
        ReservationRow selected = reservationTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            System.out.println("please choose");
            return;
        }
        LocalDate today=LocalDate.now();
        LocalDate checkInDate = LocalDate.parse(selected.getCheckIn());

        if(checkInDate.equals(today)){
        selected.getReservation().setStatus(Status.CONFIRMED);
        System.out.println("checkIn done successfuly");
    }else{
            selected.getReservation().setStatus(Status.PENDING);
            System.out.println("check in allowed only on reservation date");

        }
        reservationTable.refresh();

    }

    @FXML
    public void checkOut(ActionEvent e) {
        ReservationRow selected = reservationTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            System.out.println("please choose");
            return;
        }
        LocalDate today=LocalDate.now();
        LocalDate checkOutDate = LocalDate.parse(selected.getCheckOut());
        if(checkOutDate.equals(today)){
        selected.getReservation().setStatus(Status.COMPLETED);
        System.out.println("the checkOut done successfully");
        }else{
            selected.getReservation().setStatus(Status.CONFIRMED);
            System.out.println("check out allowed only on reservation date");

        }
        reservationTable.refresh();

    }
}