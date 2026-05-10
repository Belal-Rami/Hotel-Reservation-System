package hotel.gui.controllers;

import hotel.data.Amenity;
import hotel.data.Room;
import hotel.services.RoomManager;
import hotel.users.Guest;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RoomDetailController {

    @FXML private Label lblRoomNumber;
    @FXML private Label lblRoomType;
    @FXML private Label lblBasePrice;
    @FXML private Label lblAmenitiesPrice;
    @FXML private Label lblTotalPerNight;
    @FXML private Label lblCheckOut;
    @FXML private Label lblViewingDate;
    @FXML private Label lblStatusBadge;
    @FXML private Label lblNoAmenities;
    @FXML private Label lblReservationError;
    @FXML private ListView<String> listAmenities;
    @FXML private Button btnMakeReservation;

    private static final DateTimeFormatter DATE_FMT  =
            DateTimeFormatter.ofPattern("EEE, MMM d yyyy");
    private static final DateTimeFormatter SHORT_FMT =
            DateTimeFormatter.ofPattern("MMM d, yyyy");

    private Room        room;
    private LocalDate   viewingDate;
    private Guest       currentGuest;
    private RoomManager roomManager;

    // ── Called by RoomsController ──────────────────────────────────
    public void setRoom(Room room, LocalDate viewingDate) {
        this.room        = room;
        this.viewingDate = viewingDate;
        populateView();
    }

    public void setContext(Guest guest, RoomManager roomManager) {
        this.currentGuest = guest;
        this.roomManager  = roomManager;
    }

    // ── Populate UI ────────────────────────────────────────────────
    private void populateView() {
        lblRoomNumber    .setText("Room " + room.getRoomNum());
        lblRoomType      .setText(room.getTypeName());
        lblBasePrice     .setText(String.format("$%.2f", room.getPrice()));
        lblAmenitiesPrice.setText(String.format("$%.2f", room.amenitiesPriceperDay()));
        lblTotalPerNight .setText(String.format("$%.2f", room.totalPricePerDay()));

        LocalDate checkOut = room.getCheckOut();
        lblCheckOut.setText(checkOut != null ? checkOut.format(SHORT_FMT) : "No reservation");
        lblViewingDate.setText(viewingDate.format(DATE_FMT));

        boolean occupied = checkOut != null && checkOut.isAfter(viewingDate);
        styleStatusBadge(occupied);
        populateAmenities();
    }

    private void styleStatusBadge(boolean occupied) {
        if (occupied) {
            lblStatusBadge.setText("Occupied");
            lblStatusBadge.setStyle(
                    "-fx-background-color: rgba(210,60,60,0.82);" +
                            "-fx-background-radius: 10; -fx-padding: 8 24; -fx-text-fill: white;");
            btnMakeReservation.setStyle(
                    "-fx-background-color: rgba(210,60,60,0.55);" +
                            "-fx-background-radius: 16; -fx-text-fill: white; -fx-cursor: hand;");
        } else {
            lblStatusBadge.setText("Available");
            lblStatusBadge.setStyle(
                    "-fx-background-color: rgba(40,160,75,0.82);" +
                            "-fx-background-radius: 10; -fx-padding: 8 24; -fx-text-fill: white;");
            btnMakeReservation.setStyle(
                    "-fx-background-color: rgba(40,160,75,0.85);" +
                            "-fx-background-radius: 16; -fx-text-fill: white; -fx-cursor: hand;");
        }
    }

    private void populateAmenities() {
        listAmenities.getItems().clear();

        if (room.getAmenities() == null || room.getAmenities().isEmpty()) {
            lblNoAmenities.setVisible(true);
            listAmenities .setVisible(false);
            return;
        }

        lblNoAmenities.setVisible(false);
        listAmenities .setVisible(true);

        for (Amenity a : room.getAmenities()) {
            listAmenities.getItems().add(
                    String.format("%-30s  $%.2f / night", a.getName(), a.getPrice())
            );
        }

        listAmenities.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("-fx-background-color: transparent;");
                } else {
                    setText(item);
                    setStyle(
                            "-fx-background-color: transparent;" +
                                    "-fx-text-fill: white;" +
                                    "-fx-font-size: 22px;" +
                                    "-fx-font-family: 'Arial';" +
                                    "-fx-padding: 8 4;");
                }
            }
        });
    }

    // ── Make Reservation ───────────────────────────────────────────
    @FXML
    private void handleMakeReservation() {
        LocalDate checkOut = room.getCheckOut();
        boolean occupied   = checkOut != null && checkOut.isAfter(viewingDate);

        if (occupied) {
            lblReservationError.setVisible(true);
            return;
        }

        lblReservationError.setVisible(false);

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/hotel/gui/scenes/MakeReservation.fxml")
            );
            Parent root = loader.load();

            MakeReservationController ctrl = loader.getController();
            ctrl.setContext(room, viewingDate, currentGuest, roomManager);

            Stage stage = (Stage) lblRoomNumber.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // ── Back to Rooms – forward guest + roomManager ────────────────
    @FXML
    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/hotel/gui/scenes/sceneROoms.fxml")
            );
            Parent root = loader.load();

            RoomsController ctrl = loader.getController();
            ctrl.setContext(currentGuest, roomManager); // ← guest forwarded back

            Stage stage = (Stage) lblRoomNumber.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}