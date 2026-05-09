package hotel.gui.controllers;

import hotel.data.Reservation;
import hotel.data.Room;
import hotel.enums.Status;
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


    @FXML private Label    lblSummaryRoom;
    @FXML private Label    lblSummaryType;
    @FXML private Label    lblSummaryPrice;
    @FXML private Label    lblCheckIn;
    @FXML private Label    lblNights;
    @FXML private Label    lblTotalCost;
    @FXML private Label    lblGuestBalance;

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


    private Room        room;
    private LocalDate   checkInDate;
    private Guest       guest;
    private RoomManager roomManager;



    public void setContext(Room room, LocalDate checkIn,
                           Guest guest, RoomManager roomManager) {
        this.room        = room;
        this.checkInDate = checkIn;
        this.guest       = guest;
        this.roomManager = roomManager;
        populateSummary();
        wireListeners();
    }


    private void populateSummary() {
        lblSummaryRoom .setText("Room " + room.getRoomNum());
        lblSummaryType .setText(room.getTypeName());
        lblSummaryPrice.setText(String.format("$%.2f / night", room.totalPricePerDay()));
        lblCheckIn     .setText(checkInDate.format(SHORT_FMT));
        lblGuestBalance.setText(String.format("$%.2f", guest.getBalance()));

        // Default night/cost labels until a date is picked
        lblNights   .setText("—");
        lblTotalCost.setText("—");

        // Prevent picking dates before check-in +1day
        datePickerCheckOut.setDayCellFactory(dp -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                // Disable any date that is not AFTER the check-in date
                if (date.isBefore(checkInDate.plusDays(1))) {
                    setDisable(true);
                    setStyle("-fx-background-color: rgba(100,100,100,0.4);");
                }
            }
        });
    }

    // Live update: nights + total whenever date changes
    private void wireListeners() {
        datePickerCheckOut.valueProperty().addListener((obs, oldVal, newVal) -> {
            lblDateError.setVisible(false);
            lblBalanceError.setVisible(false);
            lblSuccess.setVisible(false);

            if (newVal == null) {
                lblNights   .setText("—");
                lblTotalCost.setText("—");
                return;
            }

            long nights = ChronoUnit.DAYS.between(checkInDate, newVal);
            double total = room.totalPricePerDay() * nights;

            lblNights   .setText(nights + " night" + (nights == 1 ? "" : "s"));
            lblTotalCost.setText(String.format("$%.2f", total));
        });
    }

    //Confirm Reservation
    @FXML
    private void handleConfirm() {

        // Reset all error / success labels
        lblDateError   .setVisible(false);
        lblBalanceError.setVisible(false);
        lblSuccess     .setVisible(false);

        LocalDate checkOut = datePickerCheckOut.getValue();

        // 1. Must pick a check-out date
        if (checkOut == null || !checkOut.isAfter(checkInDate)) {
            lblDateError.setText("⚠  Please pick a check-out date after the check-in date.");
            lblDateError.setVisible(true);
            return;
        }

        // 2. Check if the room is already reserved during ANY part of the chosen period
        if (isRoomConflicting(checkInDate, checkOut)) {
            lblDateError.setText("⚠  Room is already reserved during part of that period.");
            lblDateError.setVisible(true);
            return;
        }

        // 3. Payment method
        double totalCost = room.totalPricePerDay()
                * ChronoUnit.DAYS.between(checkInDate, checkOut);

        if (rbBalance.isSelected()) {
            if (guest.getBalance() < totalCost) {
                lblBalanceError.setVisible(true);
                return;
            }
            // Deduct from balance
            guest.setBalance(guest.getBalance() - totalCost);
            lblGuestBalance.setText(String.format("$%.2f", guest.getBalance()));
        }
        // Cash and Credit: no balance change needed (handled physically / externally)

        // 4. Create and register the reservation
        Reservation reservation = new Reservation(
                guest, room, checkInDate, checkOut, Status.CONFIRMED
        );
        roomManager.addReservation(reservation);

        // 5. Update room's checkout so availability reflects the new booking
        room.setCheckOut(checkOut);

        // 6. Show success and disable confirm so they can't double-submit
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

            // Overlap condition
            if (newCheckIn.isBefore(existOut) && existIn.isBefore(newCheckOut)) {
                return true;
            }
        }
        return false;
    }

    // Back to Room Detail scene
    @FXML
    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/hotel/gui/scenes/RoomDetail.fxml")
            );
            Parent root = loader.load();

            RoomDetailController ctrl = loader.getController();
            ctrl.setRoom(room, checkInDate);
            ctrl.setContext(guest, roomManager);

            Stage stage = (Stage) lblSummaryRoom.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}