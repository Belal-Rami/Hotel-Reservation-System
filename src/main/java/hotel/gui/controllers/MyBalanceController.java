package hotel.gui.controllers;

import hotel.gui.GuiData;
import hotel.services.RoomManager;
import hotel.users.Guest;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MyBalanceController {

    @FXML private Label     lblBalance;
    @FXML private Label     lblUsername;
    @FXML private TextField txtTopUp;
    @FXML private Label     lblFeedback;

    private Guest       guest;
    private RoomManager roomManager;

    // ── Context injection ──────────────────────────────────────────
    public void setContext(Guest guest, RoomManager roomManager) {
        this.guest       = guest;
        this.roomManager = roomManager;
        populateView();
    }

    private void populateView() {
        lblUsername.setText(guest.getUsername());
        refreshBalance();
    }

    private void refreshBalance() {
        lblBalance.setText(String.format("$%.2f", guest.getBalance()));
    }

    // ── Top-up ────────────────────────────────────────────────────
    @FXML
    private void handleTopUp() {
        lblFeedback.setVisible(false);

        String raw = txtTopUp.getText().trim();
        if (raw.isEmpty()) { showError("⚠  Please enter an amount."); return; }

        double amount;
        try {
            amount = Double.parseDouble(raw);
        } catch (NumberFormatException e) {
            showError("⚠  Invalid amount. Numbers only.");
            return;
        }

        if (amount <= 0) { showError("⚠  Amount must be greater than zero."); return; }

        showCardPopup(amount);
    }

    private void showCardPopup(double amount) {

        VBox root = new VBox(28);
        root.setAlignment(Pos.CENTER);
        root.setStyle(
                "-fx-background-color: #1a1a2e;" +
                        "-fx-background-radius: 20;" +
                        "-fx-padding: 50 60;"
        );
        root.setPrefWidth(620);

        Label title     = new Label("Enter Card Details");
        title.setStyle("-fx-text-fill: white; -fx-font-family: 'Arial Bold'; -fx-font-size: 36px;");

        Label icon      = new Label("💳");
        icon.setStyle("-fx-font-size: 64px;");

        Label amountLbl = new Label(String.format("Adding  $%.2f  to your balance", amount));
        amountLbl.setStyle(
                "-fx-text-fill: rgba(120,220,140,1.0);" +
                        "-fx-font-family: 'Arial Bold'; -fx-font-size: 26px;");

        Label fieldLabel = new Label("Card Number (12 digits)");
        fieldLabel.setStyle(
                "-fx-text-fill: rgba(255,255,255,0.60);" +
                        "-fx-font-family: 'Arial Bold'; -fx-font-size: 22px;");

        TextField cardField = new TextField();
        cardField.setPromptText("············");
        cardField.setMaxWidth(460);
        cardField.setPrefHeight(60);
        cardField.setStyle(
                "-fx-background-color: rgba(255,255,255,0.10);" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-color: rgba(255,255,255,0.28);" +
                        "-fx-border-radius: 10;" +
                        "-fx-text-fill: white;" +
                        "-fx-prompt-text-fill: rgba(255,255,255,0.30);" +
                        "-fx-font-size: 28px; -fx-alignment: center;");

        cardField.textProperty().addListener((obs, oldVal, newVal) -> {
            String digitsOnly = newVal.replaceAll("[^0-9]", "");
            if (digitsOnly.length() > 12) digitsOnly = digitsOnly.substring(0, 12);
            if (!digitsOnly.equals(newVal)) cardField.setText(digitsOnly);
        });

        Label popupError = new Label("");
        popupError.setStyle(
                "-fx-text-fill: rgba(255,80,80,0.95);" +
                        "-fx-font-family: 'Arial Bold'; -fx-font-size: 22px;");
        popupError.setVisible(false);

        Button btnConfirm = new Button("Confirm Payment");
        btnConfirm.setPrefHeight(66); btnConfirm.setPrefWidth(280);
        btnConfirm.setStyle(
                "-fx-background-color: rgba(40,160,75,0.85);" +
                        "-fx-background-radius: 10; -fx-text-fill: white;" +
                        "-fx-font-family: 'Arial Bold'; -fx-font-size: 26px; -fx-cursor: hand;");

        Button btnCancel = new Button("Cancel");
        btnCancel.setPrefHeight(66); btnCancel.setPrefWidth(160);
        btnCancel.setStyle(
                "-fx-background-color: rgba(255,255,255,0.12);" +
                        "-fx-background-radius: 10; -fx-text-fill: white;" +
                        "-fx-font-family: 'Arial Bold'; -fx-font-size: 26px; -fx-cursor: hand;");

        HBox buttons = new HBox(20, btnCancel, btnConfirm);
        buttons.setAlignment(Pos.CENTER);

        root.getChildren().addAll(icon, title, amountLbl, fieldLabel, cardField, popupError, buttons);

        javafx.stage.Stage popup = new javafx.stage.Stage();
        popup.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        popup.initStyle(StageStyle.TRANSPARENT);
        popup.setScene(new javafx.scene.Scene(root));
        popup.getScene().setFill(Color.TRANSPARENT);

        btnCancel .setOnAction(e -> popup.close());
        btnConfirm.setOnAction(e -> {
            String card = cardField.getText().trim();
            if (card.length() != 12) {
                popupError.setText("⚠  Card number must be exactly 12 digits.");
                popupError.setVisible(true);
                return;
            }
            GuiData.guestManager.setBalance(guest,guest.getBalance() + amount);
            refreshBalance();
            txtTopUp.clear();
            popup.close();

            lblFeedback.setText(String.format("✔  $%.2f added successfully.", amount));
            lblFeedback.setStyle("-fx-text-fill: rgba(80,220,120,0.95);");
            lblFeedback.setVisible(true);
        });

        popup.show();
    }

    private void showError(String msg) {
        lblFeedback.setText(msg);
        lblFeedback.setStyle("-fx-text-fill: rgba(255,80,80,0.95);");
        lblFeedback.setVisible(true);
    }

    // ── Back to guest dashboard – forward guest + roomManager ──────
    @FXML
    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/hotel/gui/scenes/guest-dashboard.fxml")
            );
            Parent root = loader.load();

            kkkcontroller kk = loader.getController();
            kk.setCurrentguest(guest);             // ← guest forwarded back

            Stage stage = (Stage) lblUsername.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}