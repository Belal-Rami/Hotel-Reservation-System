package hotel.data;

public class Amenity {
// 1. DATA FIELDS
    private String name;
    private double price;

// 2. CONSTRUCTOR
    public Amenity(String name, double price){
        setName(name);
        setPrice(price);
    }

// 3. GETTERS
    public String getName(){
        return name;
    }
    public double getPrice() {
            return price;
    }

// 4. SETTERS

    // A setter method with validation to ensure the price is not negative.
    public void setPrice(double price){
            if(price<0){
                System.out.println("invalid please try again"); // there should be an exception here
            }
            else {
                this.price= price;
            }
        }

    // setter method with validation to ensure the name is not empty or null.
    public void setName(String name){
        if(name==null||name.isEmpty()){
            System.out.println("Name cannot be empty!"); // there should be some sort of exception here
            }
        else {
            this.name=name;
        }
    }

    // 5. OVERRIDES

    @Override
    public String toString() {
        return "Amenity: " + name + " - price: " + price;
    }
}