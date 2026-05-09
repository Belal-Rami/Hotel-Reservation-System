package hotel.gui.controllers;

import hotel.services.RoomManager;
import hotel.users.Guest;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class GuestProfileController {

    // ── FXML bindings ──────────────────────────────────────────────
    @FXML private Label lblUsername;
    @FXML private Label lblGender;
    @FXML private Label lblGenderDetail;
    @FXML private Label lblDOB;
    @FXML private Label lblAddress;
    @FXML private Label lblBalance;
    @FXML private Label lblReservationCount;

    private static final DateTimeFormatter DOB_FMT =
            DateTimeFormatter.ofPattern("MMM d, yyyy");

    private Guest       guest;
    private RoomManager roomManager;

    // ── Called by whichever scene opens this one ───────────────────
    public void setContext(Guest guest, RoomManager roomManager) {
        this.guest       = guest;
        this.roomManager = roomManager;
        populateView();
    }

    private void populateView() {
        lblUsername        .setText(guest.getUsername());
        lblGender          .setText(guest.getGender().toString());
        lblGenderDetail    .setText(guest.getGender().toString());
        lblDOB             .setText(guest.getDateOfBirth().format(DOB_FMT));
        lblAddress         .setText(guest.getAddress());
        lblBalance         .setText(String.format("$%.2f", guest.getBalance()));
        lblReservationCount.setText(String.valueOf(guest.getReservations().size()));
    }

    // ── Navigate to My Reservations ────────────────────────────────
    @FXML
    private void goToReservations() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/hotel/gui/scenes/MyReservations.fxml")
            );
            Parent root = loader.load();

            MyReservationsController ctrl = loader.getController();
            ctrl.setContext(guest, roomManager);

            Stage stage = (Stage) lblUsername.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Back
    @FXML
    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/hotel/gui/scenes/kkk.fxml")
            );
            Parent root = loader.load();

            Stage stage = (Stage) lblUsername.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}