package hotel.gui.controllers;

import hotel.data.Reservation;
import hotel.data.Room;
import hotel.enums.Status;
import hotel.gui.GuiData;
import hotel.gui.GuiMain;
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
import java.time.temporal.ChronoUnit;

public class MakeReservationController {

    @FXML private Label      lblSummaryRoom;
    @FXML private Label      lblSummaryType;
    @FXML private Label      lblSummaryPrice;
    @FXML private Label      lblCheckIn;
    @FXML private Label      lblNights;
    @FXML private Label      lblTotalCost;
    @FXML private Label      lblGuestBalance;

    @FXML private DatePicker datePickerCheckOut;

    @FXML private RadioButton rbCash;
    @FXML private RadioButton rbCredit;
    @FXML private RadioButton rbBalance;
    @FXML private ToggleGroup paymentGroup;

    @FXML private Label  lblDateError;
    @FXML private Label  lblBalanceError;
    @FXML private Label  lblSuccess;
    @FXML private Button btnConfirm;

    private static final DateTimeFormatter SHORT_FMT =
            DateTimeFormatter.ofPattern("MMM d, yyyy");

    private Room room;
    private LocalDate checkInDate;
    private Guest guest;
    private RoomManager roomManager;

    // ── Called when navigating from kkk / RoomDetail ───────────────
    public void setContext(Room room, LocalDate checkIn,
                           Guest guest, RoomManager roomManager) {
        this.room        = room;
        this.checkInDate = checkIn;
        this.guest       = guest;       // ← was wrongly commented out before
        this.roomManager = roomManager;
        populateSummary();
        wireListeners();
    }

    /**
     * Legacy entry point kept for backward-compat with kkkcontroller.
     * Prefer setContext() which sets everything in one call.
     */
    public void setCurrentguest1(Guest currentguest) {
        this.guest = currentguest;
        // Refresh balance label if the summary is already showing
        if (lblGuestBalance != null && guest != null) {
            lblGuestBalance.setText(String.format("$%.2f", guest.getBalance()));
        }
    }

    // ── Populate summary panel ─────────────────────────────────────
    private void populateSummary() {
        lblSummaryRoom .setText("Room " + room.getRoomNum());
        lblSummaryType .setText(room.getTypeName());
        lblSummaryPrice.setText(String.format("$%.2f / night", room.totalPricePerDay()));
        lblCheckIn     .setText(checkInDate.format(SHORT_FMT));
        lblGuestBalance.setText(String.format("$%.2f", guest.getBalance()));

        lblNights   .setText("—");
        lblTotalCost.setText("—");

        datePickerCheckOut.setDayCellFactory(dp -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date.isBefore(checkInDate.plusDays(1))) {
                    setDisable(true);
                    setStyle("-fx-background-color: rgba(100,100,100,0.4);");
                }
            }
        });
    }

    // ── Live-update nights + total ─────────────────────────────────
    private void wireListeners() {
        datePickerCheckOut.valueProperty().addListener((obs, oldVal, newVal) -> {
            lblDateError   .setVisible(false);
            lblBalanceError.setVisible(false);
            lblSuccess     .setVisible(false);

            if (newVal == null) {
                lblNights   .setText("—");
                lblTotalCost.setText("—");
                return;
            }

            long   nights = ChronoUnit.DAYS.between(checkInDate, newVal);
            double total  = room.totalPricePerDay() * nights;
            lblNights   .setText(nights + " night" + (nights == 1 ? "" : "s"));
            lblTotalCost.setText(String.format("$%.2f", total));
        });
    }

    // ── Confirm reservation ────────────────────────────────────────
    @FXML
    private void handleConfirm() {
        lblDateError   .setVisible(false);
        lblBalanceError.setVisible(false);
        lblSuccess     .setVisible(false);

        LocalDate checkOut = datePickerCheckOut.getValue();

        if (checkOut == null || !checkOut.isAfter(checkInDate)) {
            lblDateError.setText("⚠  Please pick a check-out date after the check-in date.");
            lblDateError.setVisible(true);
            return;
        }

        if (isRoomConflicting(checkInDate, checkOut)) {
            lblDateError.setText("⚠  Room is already reserved during part of that period.");
            lblDateError.setVisible(true);
            return;
        }

        double totalCost = room.totalPricePerDay()
                * ChronoUnit.DAYS.between(checkInDate, checkOut);

        if (rbBalance.isSelected()) {
            if (guest.getBalance() < totalCost) {
                lblBalanceError.setVisible(true);
                return;
            }
            guest.setBalance(guest.getBalance() - totalCost);
            lblGuestBalance.setText(String.format("$%.2f", guest.getBalance()));
        }

        Reservation reservation = new Reservation(
                guest, room, checkInDate, checkOut, Status.PENDING
        );
        roomManager.addReservation(reservation);
        System.out.println(guest.getUsername());
        room.setCheckOut(checkOut);
        GuiMain.saveData(GuiData.database);


        lblSuccess.setVisible(true);
        btnConfirm.setDisable(true);
        btnConfirm.setStyle(
                "-fx-background-color: rgba(40,160,75,0.40);" +
                        "-fx-background-radius: 14;" +
                        "-fx-text-fill: white;"
        );
    }

    private boolean isRoomConflicting(LocalDate newCheckIn, LocalDate newCheckOut) {
        for (Reservation r : roomManager.getReservations()) {
            if (!r.getRoom().getRoomNum().equals(room.getRoomNum())) continue;
            LocalDate existIn  = r.getCheckIn();
            LocalDate existOut = r.getCheckOut();
            if (newCheckIn.isBefore(existOut) && existIn.isBefore(newCheckOut)) return true;
        }
        return false;
    }

    // ── Back to Room Detail ────────────────────────────────────────
    @FXML
    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/hotel/gui/scenes/RoomDetail.fxml")
            );
            Parent root = loader.load();

            RoomDetailController ctrl = loader.getController();
            ctrl.setRoom(room, checkInDate);
            ctrl.setContext(guest, roomManager);   // guest and roomManager forwarded

            Stage stage = (Stage) lblSummaryRoom.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}