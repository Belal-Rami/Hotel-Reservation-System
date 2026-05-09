package hotel.gui;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

import hotel.RuntimeTypeAdapterFactory;
import hotel.data.HotelDatabase;
import hotel.services.AmenityManager;
import hotel.services.GuestManager;
import hotel.services.RoomManager;
import hotel.services.RoomTypeManager;
import hotel.services.StaffManager;
import hotel.users.Admin;
import hotel.users.Receptionist;
import hotel.users.Staff;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GuiMain extends Application {

    private static final Gson gson = createGsonInstance();

    private static Gson createGsonInstance() {
        RuntimeTypeAdapterFactory<Staff> staffAdapter = RuntimeTypeAdapterFactory.of(Staff.class, "type")
                .registerSubtype(Admin.class, "Admin")
                .registerSubtype(Receptionist.class, "Receptionist");

        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (date, type, context) -> new JsonPrimitive(date.toString()))
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, type, context) -> LocalDate.parse(json.getAsString()))
                .registerTypeAdapterFactory(staffAdapter)
                .setPrettyPrinting()
                .create();
    }

    public static HotelDatabase loadData() {
        try (FileReader reader = new FileReader("Hotel_Database.json")) {
            System.out.println("Data loaded successfully.");
            return gson.fromJson(reader, HotelDatabase.class);
        } catch (IOException e) {
            System.out.println("No database found. Creating a new database...");
            return new HotelDatabase();
        }
    }

    public static void saveData(HotelDatabase database) {
        try (FileWriter writer = new FileWriter("Hotel_Database.json")) {
            gson.toJson(database, writer);
            System.out.println("Data saved successfully.");
        } catch (Exception e) {
            System.out.println("Error saving: " + e.getMessage());
        }
    }
    @Override
    public void init() {
        HotelDatabase database = loadData();

        // Initialize Managers
        GuiData.roomManager = new RoomManager(database.getRooms(), database.getReservations());
        GuiData.amenityManager = new AmenityManager(database.getAmenities());
        GuiData.guestManager = new GuestManager(database.getGuests());
        GuiData.staffManager = new StaffManager(database.getStaffMembers());
        GuiData.roomTypeManager = new RoomTypeManager(database.getRoomTypes());
        GuiData.database = database; 




    }
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/hotel/gui/scenes/start-screen.fxml")
        );

        Scene scene = new Scene(loader.load(), 1920, 1080);
        stage.setTitle("Start Screen");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
        
    }
}