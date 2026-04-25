package hotel.data;
import java.util.Scanner;


import hotel.enums.Gender;
import hotel.users.*;
import hotel.services.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

import hotel.RuntimeTypeAdapterFactory;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.time.LocalDate;

   /*  static{
        guests.add(new Guest("ShahidISmail26",
                "Shahid_2006?",
                LocalDate.of(2006,7,11),
                20000000,
                "Cairo",
                Gender.MALE));
        guests.add(new Guest("Hananelderiny19"
                ,"Hanan-123$",
                LocalDate.of(1987,3,12),
                12345,
                "Alexandria",
                Gender.FEMALE));
    }
    */
public class MainApplication{
    private static final Gson gson= createGsonInstance();

    private static Gson createGsonInstance(){
        //Gson must know what to do when dealing with the staff arraylist since it has subtypes, so we must define how it deals with it
        RuntimeTypeAdapterFactory<Staff> staffAdapter = RuntimeTypeAdapterFactory.of(Staff.class, "type")
            .registerSubtype(Admin.class, "Admin")
            .registerSubtype(Receptionist.class, "Receptionist");


        return new GsonBuilder()

        //Gson must be given a way to handle the LocalDate object found in some of the object

        //This is the "instruction" for Gson to follow when it encounters a LocalDate when it is serialzing the data
        .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>)(date, type, context)
    -> new JsonPrimitive(date.toString()))

    //This is for deserialzation
        .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>)(json, type, context)
   -> LocalDate.parse(json.getAsString()))

        .registerTypeAdapterFactory(staffAdapter)
        .setPrettyPrinting()
        .create();
        
    }



    public static HotelDatabase loadData(){
        //The part between the parenthesis is made to make sure that the file closes within the scope
        try (FileReader reader = new FileReader("Hotel_Database.json")){
            System.out.println("Data loaded");
            return gson.fromJson(reader, HotelDatabase.class);
        }
        catch(IOException e){
            System.out.println("There is no database found, created a new database");
            return new HotelDatabase();
        }



    }

    public static void saveData(HotelDatabase database){
        try (FileWriter writer = new FileWriter("Hotel_Database.json")){
            gson.toJson(database, writer);
            System.out.println("Data saved");
        } catch (Exception e) {
            System.out.println("Error saving: " + e.getMessage());
        }
    }
    public static void main(String[] args){
        //Create an instance for the hote
        HotelDatabase database = loadData();

        //Creating instances for managers to handle operations
        RoomManager roomManager = new RoomManager(database.getRooms());
        AmenityManager amenityManager = new AmenityManager(database.getAmenities());
        GuestManager guestManager = new GuestManager(database.getGuests());
        StaffManager staffManager = new StaffManager(database.getStaffMembers());


        Scanner input = new Scanner(System.in);
        int choice;

        do {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. New Amenity");
            System.out.println("4. View Amenities");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            choice = input.nextInt();
            input.nextLine();
            switch(choice) {
                case 1:
                    System.out.println("Enter username");
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
                    System.out.println("Registration done");
                    break;
                case 2:
                    System.out.print("Enter username: ");
                    String loginUsername = input.nextLine();
                    System.out.print("Enter password: ");
                    String loginPassword = input.nextLine();
                    boolean loggedInGuest = guestManager.loginGuest(loginUsername, loginPassword);
                    if (loggedInGuest) {
                        System.out.println("Login Successfully");
                    }
                    else{
                    System.out.println("Invalid username or password");
                }
                    break;



                case 3:
                    System.out.print("Enter amenity name: ");
                    String amenityName = input.nextLine();
                    System.out.print("Enter amenity price: ");
                    double amenityPrice = input.nextDouble();
                    input.nextLine();
                    //3
                    // Amenity newAmenity = new Amenity(amenityName, amenityPrice);
                    amenityManager.createAmenity(amenityName, amenityPrice);
                    System.out.println("Amenity created");
                    break;
                
                    
                    case 4: 
                    System.out.println("All of the amenities in the hotel:");
                    amenityManager.printAll();
                    break;

                    case 5:
                        System.out.println("Goodbye");
                        break;
                    default:
                    System.out.println("Invalid choice");
            }


        } while(choice != 5);
        saveData(database);
    }
    
}
