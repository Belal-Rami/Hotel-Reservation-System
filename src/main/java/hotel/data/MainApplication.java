package hotel.data;

import java.util.Scanner;
import java.time.LocalDate;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

import hotel.RuntimeTypeAdapterFactory;
import hotel.enums.Gender;
import hotel.users.*;
import hotel.services.*;

public class MainApplication {
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

    public static void main(String[] args) {
        HotelDatabase database = loadData();

        // Initialize Managers
        RoomManager roomManager = new RoomManager(database.getRooms(), database.getReservations(), database.getGuests());
        AmenityManager amenityManager = new AmenityManager(database.getAmenities());
        GuestManager guestManager = new GuestManager(database.getGuests());
        StaffManager staffManager = new StaffManager(database.getStaffMembers());
        RoomTypeManager roomTypeManager = new RoomTypeManager(database.getRoomTypes());

        Scanner input = new Scanner(System.in);
        int mainChoice;

        do {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Staff Login");
            System.out.println("2. Guest Menu (Login / Register)");
            System.out.println("3. [Setup] Register New Staff");
            System.out.println("4. Exit System");
            System.out.print("Enter choice: ");
            
            mainChoice = input.nextInt();
            input.nextLine();

            switch (mainChoice) {
                case 1:
                    // ---------------- STAFF FLOW ----------------
                    System.out.println("\n--- STAFF LOGIN ---");
                    System.out.print("Enter username: ");
                    String staffUser = input.nextLine();
                    System.out.print("Enter password: ");
                    String staffPass = input.nextLine();

                    // Directly capturing the object returned by the login function
                    Staff loggedInStaff = staffManager.loginStaff(staffUser, staffPass);

                    if (loggedInStaff != null) {
                        loggedInStaff.showDashboard();

                        if (loggedInStaff instanceof Admin) {
                            Admin admin = (Admin) loggedInStaff;
                            boolean adminActive = true;

                            while(adminActive) {
                                System.out.println("\n--- ADMIN DASHBOARD ---");
                                System.out.println("1. Manage Rooms");
                                System.out.println("2. Manage Room Types");
                                System.out.println("3. Manage Amenities");
                                System.out.println("4. Logout");
                                System.out.print("Enter choice: ");
                                int adminChoice = input.nextInt();
                                input.nextLine();

                                if (adminChoice == 1) {
                                    // --------- ROOM MANAGEMENT ---------
                                    System.out.println("\n-- Manage Rooms --");
                                    System.out.println("1. Add a New Room");
                                    System.out.println("2. View All Rooms");
                                    System.out.println("3. Change a Room's Type");
                                    System.out.println("4. Add Amenity to a Room");
                                    System.out.println("5. Remove Amenity from a Room");
                                    System.out.println("6. Delete a Room");
                                    System.out.println("7. Back");
                                    System.out.print("Enter choice: ");
                                    int rChoice = input.nextInt();
                                    input.nextLine();

                                    switch (rChoice) {
                                        case 1:
                                            if (database.getRoomTypes().isEmpty()) {
                                                System.out.println("ERROR: You must create a Room Type first before adding a room!");
                                                break;
                                            }
                                            
                                            // Automatically generate the room number instead of asking the user
                                            String rNum = String.valueOf(roomManager.generateID());
                                            System.out.println("Generated Room Number: " + rNum);

                                            System.out.println("Available Room Types:");
                                            for(RoomType rt : database.getRoomTypes()) {
                                                System.out.println(" - " + rt.getName() + " ($" + rt.getPrice() + ")");
                                            }
                                            System.out.print("Enter the exact name of the Room Type to assign: ");
                                            String rtName = input.nextLine();
                                            RoomType selectedType = roomTypeManager.findRoomType(rtName);
                                            
                                            if (selectedType != null) {
                                                Room newRoom = new Room(rNum, selectedType, LocalDate.now(), LocalDate.now().plusDays(1));
                                                admin.createRoom(newRoom, database);
                                            } else {
                                                System.out.println("Room Type not found. Room creation cancelled.");
                                            }
                                            break;
                                        case 2:
                                            admin.viewAllRooms(database);
                                            break;
                                        case 3:
                                            System.out.print("Enter Room Number to update: ");
                                            String updateNum = input.nextLine();
                                            Room roomToUpdate = admin.readRoom(updateNum, database);
                                            if (roomToUpdate != null) {
                                                System.out.print("Enter new Room Type name: ");
                                                String newRtName = input.nextLine();
                                                RoomType newRt = roomTypeManager.findRoomType(newRtName);
                                                if(newRt != null) {
                                                    roomManager.changeRoomType(newRt, roomToUpdate);
                                                    System.out.println("Room " + updateNum + " updated to " + newRt.getName());
                                                } else { System.out.println("Room Type not found."); }
                                            } else { System.out.println("Room not found."); }
                                            break;
                                        case 4:
                                            System.out.print("Enter Room Number: ");
                                            String rNumA = input.nextLine();
                                            Room roomA = admin.readRoom(rNumA, database);
                                            if (roomA != null) {
                                                System.out.print("Enter Amenity name to assign to room: ");
                                                String amName = input.nextLine();
                                                Amenity foundAm = null;
                                                for(Amenity a : database.getAmenities()) {
                                                    if(a.getName().equalsIgnoreCase(amName)) foundAm = a;
                                                }
                                                if(foundAm != null) {
                                                    roomManager.addAmenityToRoom(roomA, foundAm);
                                                    System.out.println(amName + " added to Room " + rNumA);
                                                } else { System.out.println("Amenity does not exist in the hotel database."); }
                                            } else { System.out.println("Room not found."); }
                                            break;
                                        case 5:
                                            System.out.print("Enter Room Number: ");
                                            String rNumR = input.nextLine();
                                            Room roomR = admin.readRoom(rNumR, database);
                                            if (roomR != null) {
                                                System.out.print("Enter Amenity name to remove from room: ");
                                                String remAmName = input.nextLine();
                                                Amenity amToRemove = null;
                                                for(Amenity a : roomR.getAmenities()) {
                                                    if(a.getName().equalsIgnoreCase(remAmName)) amToRemove = a;
                                                }
                                                if(amToRemove != null) {
                                                    roomR.deleteAmenity(amToRemove);
                                                    System.out.println("Amenity removed from room.");
                                                } else { System.out.println("Room does not have that amenity."); }
                                            } else { System.out.println("Room not found."); }
                                            break;
                                        case 6:
                                            System.out.print("Enter Room Number to delete: ");
                                            String delNum = input.nextLine();
                                            admin.deleteRoom(delNum, database);
                                            break;
                                    }
                                } else if (adminChoice == 2) {
                                    // --------- ROOM TYPE MANAGEMENT ---------
                                    System.out.println("\n-- Manage Room Types --");
                                    System.out.println("1. Create Room Type | 2. View Room Types | 3. Delete Room Type | 4. Back");
                                    System.out.print("Enter choice: ");
                                    int rtChoice = input.nextInt(); input.nextLine();
                                    
                                    switch(rtChoice) {
                                        case 1:
                                            System.out.print("Enter Room Type Name (e.g. Single, Double): ");
                                            String tName = input.nextLine();
                                            System.out.print("Enter Price: ");
                                            double tPrice = input.nextDouble(); input.nextLine();
                                            roomTypeManager.createRoomType(tName, tPrice);
                                            System.out.println("Room Type Created.");
                                            break;
                                        case 2:
                                            for(RoomType rt : database.getRoomTypes()) { System.out.println(rt.toString()); }
                                            break;
                                        case 3:
                                            System.out.print("Enter Room Type to delete: ");
                                            String oldT = input.nextLine();
                                            RoomType oldType = roomTypeManager.findRoomType(oldT);
                                            if(oldType != null) {
                                                System.out.print("Enter replacement Room Type for existing rooms: ");
                                                String newT = input.nextLine();
                                                RoomType newType = roomTypeManager.findRoomType(newT);
                                                if (newType != null) {
                                                    roomTypeManager.deleteRoomType(oldType, newType, roomManager);
                                                    System.out.println("Room Type deleted. Existing rooms updated to " + newType.getName());
                                                } else { System.out.println("Replacement Room Type not found. Deletion cancelled."); }
                                            } else { System.out.println("Room Type not found."); }
                                            break;
                                    }
                                } else if (adminChoice == 3) {
                                    // --------- AMENITY MANAGEMENT ---------
                                    System.out.println("\n-- Manage Amenities --");
                                    System.out.println("1. Create Amenity | 2. View Amenities | 3. Delete Amenity | 4. Back");
                                    System.out.print("Enter choice: ");
                                    int amChoice = input.nextInt(); input.nextLine();

                                    switch(amChoice) {
                                        case 1:
                                            System.out.print("Enter Amenity Name: ");
                                            String aName = input.nextLine();
                                            System.out.print("Enter Price: ");
                                            double aPrice = input.nextDouble(); input.nextLine();
                                            amenityManager.createAmenity(aName, aPrice);
                                            System.out.println("Amenity Created.");
                                            break;
                                        case 2:
                                            amenityManager.printAll();
                                            break;
                                        case 3:
                                            System.out.print("Enter Amenity Name to globally delete: ");
                                            String delAm = input.nextLine();
                                            Amenity targetAm = null;
                                            for(Amenity a : database.getAmenities()) {
                                                if(a.getName().equalsIgnoreCase(delAm)) targetAm = a;
                                            }
                                            if(targetAm != null) {
                                                amenityManager.deleteAmenity(targetAm, roomManager);
                                                System.out.println("Amenity deleted from hotel and all associated rooms.");
                                            } else { System.out.println("Amenity not found."); }
                                            break;
                                    }
                                } else if (adminChoice == 4) {
                                    System.out.println("Logging out Admin...");
                                    adminActive = false;
                                }
                            }

                        } else if (loggedInStaff instanceof Receptionist) {
                            System.out.println("Receptionist Dashboard coming soon...");
                        }
                    } else {
                        System.out.println("Invalid staff credentials.");
                    }
                    break;

                case 2:
                    // ---------------- GUEST FLOW ----------------
                    int guestMenuChoice;
                    do {
                        System.out.println("\n--- GUEST MENU ---");
                        System.out.println("1. Register");
                        System.out.println("2. Login");
                        System.out.println("3. Back to Main Menu");
                        System.out.print("Enter choice: ");
                        guestMenuChoice = input.nextInt();
                        input.nextLine();

                        switch (guestMenuChoice) {
                            case 1:
                                System.out.print("Enter username: ");
                                String username = input.nextLine();
                                System.out.print("Enter password: ");
                                String password = input.nextLine();
                                System.out.print("Enter birth year: ");
                                int year = input.nextInt();
                                System.out.print("Enter birth month: ");
                                int month = input.nextInt();
                                System.out.print("Enter birth day: ");
                                int day = input.nextInt();
                                input.nextLine();
                                LocalDate dob = LocalDate.of(year, month, day);
                                System.out.print("Enter balance: ");
                                double balance = input.nextDouble();
                                input.nextLine();
                                System.out.print("Enter address: ");
                                String address = input.nextLine();
                                System.out.print("Enter gender (MALE/FEMALE): ");
                                String g = input.nextLine();
                                Gender gender = Gender.valueOf(g.toUpperCase());
                                Guest newGuest = new Guest(username, password, dob, balance, address, gender);
                                guestManager.registerGuest(newGuest);
                                System.out.println("Registration successful.");
                                break;

                            case 2:
                                System.out.print("Enter username: ");
                                String loginUsername = input.nextLine();
                                System.out.print("Enter password: ");
                                String loginPassword = input.nextLine();
                                
                                // Directly capturing the object returned by the login function
                                Guest loggedInGuest = guestManager.loginGuest(loginUsername, loginPassword);

                                if (loggedInGuest != null) {
                                    System.out.println("Login Successful!");
                                    int guestActionChoice;
                                    do {
                                        System.out.println("\n--- GUEST DASHBOARD ---");
                                        System.out.println("1. View Available Rooms");
                                        System.out.println("2. Make a Reservation");
                                        System.out.println("3. View My Reservations");
                                        System.out.println("4. Logout");
                                        System.out.print("Enter choice: ");
                                        guestActionChoice = input.nextInt();
                                        input.nextLine();

                                        switch (guestActionChoice) {
                                            case 1:
                                                System.out.print("Enter date to check (YYYY-MM-DD) or press Enter for today: ");
                                                String dateStr = input.nextLine();
                                                LocalDate checkDate = dateStr.isEmpty() ? LocalDate.now() : LocalDate.parse(dateStr);
                                                roomManager.checkRoomStatus(checkDate);
                                                break;
                                            case 2:
                                                System.out.println("Making a reservation (not implemented yet)");
                                                break;
                                            case 3:
                                                roomManager.viewGuestReservations(loggedInGuest);
                                                break;
                                            case 4:
                                                System.out.println("Guest logging out...");
                                                break;
                                            default:
                                                System.out.println("Invalid choice.");
                                        }
                                    } while (guestActionChoice != 4);
                                } else {
                                    System.out.println("Invalid username or password.");
                                }
                                break;

                            case 3:
                                System.out.println("Returning to Main Menu...");
                                break;
                            default:
                                System.out.println("Invalid choice.");
                        }
                    } while (guestMenuChoice != 3);
                    break;

                case 3:
                    // ---------------- STAFF REGISTRATION FLOW ----------------
                    System.out.println("\n--- NEW STAFF REGISTRATION ---");
                    System.out.println("1. Register Admin");
                    System.out.println("2. Register Receptionist");
                    System.out.print("Select role: ");
                    int roleChoice = input.nextInt();
                    input.nextLine();

                    System.out.print("Enter new username: ");
                    String newUsername = input.nextLine();
                    System.out.print("Enter new password (min 6 chars): ");
                    String newPassword = input.nextLine();
                    
                    System.out.print("Enter birth year: ");
                    int sYear = input.nextInt();
                    System.out.print("Enter birth month: ");
                    int sMonth = input.nextInt();
                    System.out.print("Enter birth day: ");
                    int sDay = input.nextInt();
                    input.nextLine();
                    LocalDate sDob = LocalDate.of(sYear, sMonth, sDay);

                    System.out.print("Enter working hours (1-80): ");
                    int workHours = input.nextInt();
                    input.nextLine();

                    try {
                        if (roleChoice == 1) {
                            Admin newAdmin = new Admin(newUsername, newPassword, sDob, workHours); 
                            staffManager.registerStaff(newAdmin);
                            System.out.println("Admin registered successfully!");
                        } else if (roleChoice == 2) {
                            Receptionist newReceptionist = new Receptionist(newUsername, newPassword, sDob, workHours);
                            staffManager.registerStaff(newReceptionist);
                            System.out.println("Receptionist registered successfully!");
                        } else {
                            System.out.println("Invalid role choice.");
                        }
                        saveData(database); 
                    } catch (InvalidInputException e) {
                        System.out.println("Failed to register staff: " + e.getMessage());
                    }
                    break;

                case 4:
                    System.out.println("Saving database and exiting...");
                    break;

                default:
                    System.out.println("Invalid main menu choice.");
            }
        } while (mainChoice != 4);

        saveData(database);
        input.close();
    }
}