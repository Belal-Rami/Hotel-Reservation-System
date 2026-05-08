package hotel.data;

import java.util.Scanner;
import hotel.users.Guest;
import java.time.LocalDate;
import hotel.enums.Gender;
import hotel.enums.PaymentMethod;

public class Main {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        int choice;

        do {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Create Invoice");
            System.out.println("4. Exit");
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
                    HotelDatabase.registerGuest(newGuest);
                    System.out.println("Registration done");
                    break;
                case 2:
                    System.out.print("Enter username: ");
                    String loginUsername = input.nextLine();
                    System.out.print("Enter password: ");
                    String loginPassword = input.nextLine();
                    Guest loggedInGuest = HotelDatabase.loginGuest(loginUsername, loginPassword);
                    if (loggedInGuest != null) {
                        System.out.println("Login Successfully");
                        System.out.println(loggedInGuest);
                    }
                    else{
                    System.out.println("Invalid username or password");
                }
                    break;

                case 3:
                    System.out.print("Enter total amount: ");
                    double totalAmount = input.nextDouble();
                    input.nextLine();

                    System.out.println("Choose payment method:");
                    System.out.println("1. CASH");
                    System.out.println("2. CREDIT_CARD");
                    System.out.println("3. ONLINE");
                    int paymentChoice = input.nextInt();
                    input.nextLine();

                    PaymentMethod paymentMethod;

                    if (paymentChoice == 1) {
                        paymentMethod = PaymentMethod.CASH;
                    } else if (paymentChoice == 2) {
                        paymentMethod = PaymentMethod.CREDIT_CARD;
                    } else {
                        paymentMethod = PaymentMethod.ONLINE;
                    }

                    Invoice invoice = new Invoice(totalAmount, paymentMethod, LocalDate.now());

                    System.out.println("Invoice created successfully");
                    System.out.println(invoice);
                    break;



                case 4:
                    System.out.println("Goodbye");
                    break;
                default:
                    System.out.println("Invalid choice");
            }


        } while(choice != 4);
    }
}
