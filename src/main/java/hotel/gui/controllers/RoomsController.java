package hotel.gui.controllers;

import hotel.data.Room;
import hotel.gui.GuiData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class RoomsController {

    @FXML private TilePane tileRooms;
    @FXML private Label lblCurrentDate;
    @FXML private ComboBox<String> cmbRoomType;

    private LocalDate currentDate = LocalDate.now();
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEE, MMM d yyyy");

    // Inject this from wherever you store your rooms (GuiData, a service, etc.)
    private List<Room> allRooms;

    @FXML
    public void initialize() {
        // Replace this with however you load rooms, e.g. GuiData.getInstance().getRooms()
        allRooms = loadRooms();
        populateRoomTypeFilter();
        refreshView();
    }


    // Day navigation


    @FXML
    private void prevDay() {
        currentDate = currentDate.minusDays(1);
        refreshView();
    }

    @FXML
    private void nextDay() {
        currentDate = currentDate.plusDays(1);
        refreshView();
    }


    // Filter


    @FXML
    private void filterByRoomType() {
        refreshView();
    }


    // View refresh


    private void refreshView() {
        lblCurrentDate.setText(currentDate.format(dateFormatter));

        String selectedType = cmbRoomType.getValue();

        List<Room> filtered = allRooms.stream()
                .filter(r -> selectedType == null
                        || selectedType.equals("All")
                        || r.getTypeName().equals(selectedType))
                .collect(Collectors.toList());

        tileRooms.getChildren().clear();
        for (Room room : filtered) {
            tileRooms.getChildren().add(buildRoomCard(room));
        }
    }


    // Card builder


    private VBox buildRoomCard(Room room) {
        boolean occupied = room.getCheckOut() != null
                && room.getCheckOut().isAfter(currentDate);

        String status  = occupied ? "Occupied"  : "Available";
        String bgColor = occupied ? "rgba(200, 50, 50, 0.82)"
                : "rgba(40, 160, 75, 0.82)";

        VBox card = new VBox(12);
        card.setPrefWidth(340);
        // No fixed prefHeight — let content decide

        card.setStyle(
                "-fx-background-color: " + bgColor + ";" +
                        "-fx-background-radius: 16;" +
                        "-fx-padding: 24 20;" +
                        "-fx-cursor: hand;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.35), 12, 0, 0, 4);"
        );


        Label lblNumber = new Label("Room " + room.getRoomNum());
        lblNumber.setFont(new Font("Arial Bold", 28));
        lblNumber.setStyle("-fx-text-fill: white;");
        lblNumber.setMaxWidth(Double.MAX_VALUE);


        Label lblType = new Label(room.getTypeName());
        lblType.setFont(new Font("Arial", 18));
        lblType.setStyle("-fx-text-fill: rgba(255,255,255,0.85);");
        lblType.setMaxWidth(Double.MAX_VALUE);


        Label lblPrice = new Label(String.format("$%.2f / night", room.getPrice()));
        lblPrice.setFont(new Font("Arial", 15));
        lblPrice.setStyle("-fx-text-fill: rgba(255,255,255,0.80);");
        lblPrice.setMaxWidth(Double.MAX_VALUE);


        Label lblCheckout = new Label(
                occupied
                        ? "Checkout: " + room.getCheckOut().format(DateTimeFormatter.ofPattern("MMM d, yyyy"))
                        : "No active reservation"
        );
        lblCheckout.setFont(new Font("Arial", 13));
        lblCheckout.setStyle("-fx-text-fill: rgba(255,255,255,0.70);");
        lblCheckout.setMaxWidth(Double.MAX_VALUE);
        lblCheckout.setWrapText(true);

        // Status badge
        Label lblStatus = new Label(status);
        lblStatus.setFont(new Font("Arial Bold", 14));
        lblStatus.setStyle(
                "-fx-background-color: rgba(255,255,255,0.22);" +
                        "-fx-background-radius: 6;" +
                        "-fx-padding: 4 14;" +
                        "-fx-text-fill: white;"
        );

        card.getChildren().addAll(lblNumber, lblType, lblPrice, lblCheckout, lblStatus);


        card.setOnMouseClicked(e -> openRoomDetail(room));

        // Hover effect
        String hoverStyle =
                "-fx-background-color: " + bgColor + ";" +
                        "-fx-background-radius: 16;" +
                        "-fx-padding: 24 20;" +
                        "-fx-cursor: hand;" +
                        "-fx-scale-x: 1.03; -fx-scale-y: 1.03;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 18, 0, 0, 6);";
        String normalStyle =
                "-fx-background-color: " + bgColor + ";" +
                        "-fx-background-radius: 16;" +
                        "-fx-padding: 24 20;" +
                        "-fx-cursor: hand;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.35), 12, 0, 0, 4);";

        card.setOnMouseEntered(e -> card.setStyle(hoverStyle));
        card.setOnMouseExited(e  -> card.setStyle(normalStyle));

        return card;
    }


    // Scene transition


    private void openRoomDetail(Room room) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/hotel/gui/scenes/RoomDetail.fxml")
            );
            Parent root = loader.load();

            RoomDetailController detail = loader.getController();
            detail.setRoom(room, currentDate);

            Stage stage = (Stage) tileRooms.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    // Back button


    @FXML
    private void switchScene1() {
        try {
            Parent root = FXMLLoader.load(
                    getClass().getResource("/hotel/gui/scenes/kkk.fxml")
            );
            Stage stage = (Stage) tileRooms.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Helpers

    private void populateRoomTypeFilter() {
        List<String> types = allRooms.stream()
                .map(Room::getTypeName)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        types.add(0, "All");
        cmbRoomType.getItems().setAll(types);
        cmbRoomType.setValue("All");
    }

    // Replace this with GuiData.getInstance().getRooms() or however you load rooms
    private List<Room> loadRooms() {
        return GuiData.roomManager.getRooms(); // adjust to your actual data source
    }
}