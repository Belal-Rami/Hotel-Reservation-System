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

    // ── Context injection ──────────────────────────────────────────
    public void setContext(Guest guest, RoomManager roomManager) {
        this.guest       = guest;
        this.roomManager = roomManager;
        populateView();
    }

    private void populateView() {
        // Username — always set at registration
        lblUsername.setText(guest.getUsername());

        // Gender — null for guests registered with username+password only
        String gender = guest.getGender() != null
                ? guest.getGender().toString()
                : "Not set";
        lblGender      .setText(gender);
        lblGenderDetail.setText(gender);

        // Date of birth — optional
        String dob = guest.getDateOfBirth() != null
                ? guest.getDateOfBirth().format(DOB_FMT)
                : "Not set";
        lblDOB.setText(dob);

        // Address — optional
        String address = (guest.getAddress() != null && !guest.getAddress().isBlank())
                ? guest.getAddress()
                : "Not set";
        lblAddress.setText(address);

        // Balance — primitive double, always safe
        lblBalance.setText(String.format("$%.2f", guest.getBalance()));

        // Reservations — guard against null list
        int count = guest.getReservations() != null
                ? guest.getReservations().size()
                : 0;
        lblReservationCount.setText(String.valueOf(count));
    }

    // ── Go to My Reservations ──────────────────────────────────────
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

    // ── Back to guest dashboard ────────────────────────────────────
    @FXML
    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/hotel/gui/scenes/guest-dashboard.fxml")
            );
            Parent root = loader.load();

            kkkcontroller kk = loader.getController();
            kk.setCurrentguest(guest);

            Stage stage = (Stage) lblUsername.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}