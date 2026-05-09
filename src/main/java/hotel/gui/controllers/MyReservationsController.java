package hotel.gui.controllers;

import hotel.data.Reservation;
import hotel.services.RoomManager;
import hotel.users.Guest;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class MyReservationsController {

    // ── FXML bindings ──────────────────────────────────────────────
    @FXML private VBox   cardsContainer;
    @FXML private Label  lblCount;
    @FXML private Label  lblEmpty;

    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("MMM d, yyyy");

    private Guest       guest;
    private RoomManager roomManager;

    // ── Called by the previous scene ──────────────────────────────
    public void setContext(Guest guest, RoomManager roomManager) {
        this.guest       = guest;
        this.roomManager = roomManager;
        buildCards();
    }

    // ── Build one card per reservation ────────────────────────────
    private void buildCards() {
        cardsContainer.getChildren().clear();

        var list = guest.getReservations();

        if (list.isEmpty()) {
            lblEmpty.setVisible(true);
            lblCount.setText("0 bookings");
            return;
        }

        lblEmpty.setVisible(false);
        lblCount.setText(list.size() + (list.size() == 1 ? " booking" : " bookings"));

        for (Reservation r : list) {
            cardsContainer.getChildren().add(buildCard(r));
        }
    }

    private HBox buildCard(Reservation r) {

        long nights = ChronoUnit.DAYS.between(r.getCheckIn(), r.getCheckOut());
        double total = r.getRoom().totalPricePerDay() * nights;

        // ── Card container ─────────────────────────────────────────
        HBox card = new HBox(40);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setStyle(
                "-fx-background-color: rgba(255,255,255,0.08);" +
                        "-fx-background-radius: 16;" +
                        "-fx-padding: 32 40;"
        );
        card.setPrefHeight(160);

        // ── Reservation ID pill ────────────────────────────────────
        Label lblId = new Label("#" + r.getReservationID());
        lblId.setStyle(
                "-fx-background-color: rgba(60,130,210,0.70);" +
                        "-fx-background-radius: 8;" +
                        "-fx-padding: 6 18;" +
                        "-fx-text-fill: white;"
        );
        lblId.setFont(new Font("Arial Bold", 26));
        lblId.setMinWidth(140);

        // ── Room number ────────────────────────────────────────────
        VBox roomBox = labelPair("Room", r.getRoom().getRoomNum());

        // ── Check-in ───────────────────────────────────────────────
        VBox checkInBox = labelPair("Check-in", r.getCheckIn().format(FMT));

        // ── Check-out ──────────────────────────────────────────────
        VBox checkOutBox = labelPair("Check-out", r.getCheckOut().format(FMT));

        // ── Duration ───────────────────────────────────────────────
        VBox durationBox = labelPair("Duration",
                nights + " night" + (nights == 1 ? "" : "s"));

        // ── Total cost ─────────────────────────────────────────────
        VBox costBox = labelPair("Total cost", String.format("$%.2f", total));

        // ── Status ─────────────────────────────────────────────────
        Label lblStatus = new Label(r.getStatus().toString());
        lblStatus.setFont(new Font("Arial Bold", 24));
        String statusColor = r.getStatus().toString().equalsIgnoreCase("CONFIRMED")
                ? "rgba(40,160,75,0.80)"
                : "rgba(210,60,60,0.80)";
        lblStatus.setStyle(
                "-fx-background-color: " + statusColor + ";" +
                        "-fx-background-radius: 8;" +
                        "-fx-padding: 6 18;" +
                        "-fx-text-fill: white;"
        );

        // ── Spacer ─────────────────────────────────────────────────
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // ── Cancel button ──────────────────────────────────────────
        Button btnCancel = new Button("Cancel");
        btnCancel.setFont(new Font("Arial Bold", 26));
        btnCancel.setPrefHeight(70);
        btnCancel.setPrefWidth(180);
        btnCancel.setStyle(
                "-fx-background-color: rgba(210,60,60,0.75);" +
                        "-fx-background-radius: 10;" +
                        "-fx-text-fill: white;" +
                        "-fx-cursor: hand;"
        );
        btnCancel.setOnAction(e -> cancelReservation(r, card, btnCancel, lblStatus));

        card.getChildren().addAll(
                lblId, roomBox, checkInBox, checkOutBox,
                durationBox, costBox, spacer, lblStatus, btnCancel
        );

        return card;
    }

    /** Creates a small VBox with a dim caption above a bold value. */
    private VBox labelPair(String caption, String value) {
        Label cap = new Label(caption.toUpperCase());
        cap.setStyle("-fx-text-fill: rgba(255,255,255,0.50);");
        cap.setFont(new Font("Arial Bold", 20));

        Label val = new Label(value);
        val.setStyle("-fx-text-fill: white;");
        val.setFont(new Font("Arial Bold", 30));

        VBox box = new VBox(4, cap, val);
        box.setMinWidth(200);
        return box;
    }

    // ── Cancel logic ──────────────────────────────────────────────
    private void cancelReservation(Reservation r, HBox card,
                                   Button btnCancel, Label lblStatus) {

        boolean removed = roomManager.removeReservation(
                r.getReservationID(), r.getpassword()
        );

        if (removed) {
            // Visually strike through the card
            card.setStyle(
                    "-fx-background-color: rgba(210,60,60,0.12);" +
                            "-fx-background-radius: 16;" +
                            "-fx-padding: 32 40;" +
                            "-fx-opacity: 0.55;"
            );
            lblStatus.setText("CANCELLED");
            lblStatus.setStyle(
                    "-fx-background-color: rgba(130,130,130,0.70);" +
                            "-fx-background-radius: 8;" +
                            "-fx-padding: 6 18;" +
                            "-fx-text-fill: white;"
            );
            btnCancel.setDisable(true);
            btnCancel.setStyle(
                    "-fx-background-color: rgba(130,130,130,0.40);" +
                            "-fx-background-radius: 10;" +
                            "-fx-text-fill: white;"
            );

            // Update count badge
            long active = guest.getReservations().size();
            lblCount.setText(active + (active == 1 ? " booking" : " bookings"));
            if (guest.getReservations().isEmpty()) lblEmpty.setVisible(true);
        }
    }

    // ── Back to Guest Profile ──────────────────────────────────────
    @FXML
    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/hotel/gui/scenes/GuestProfile.fxml")
            );
            Parent root = loader.load();

            GuestProfileController ctrl = loader.getController();
            ctrl.setContext(guest, roomManager);

            Stage stage = (Stage) cardsContainer.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}