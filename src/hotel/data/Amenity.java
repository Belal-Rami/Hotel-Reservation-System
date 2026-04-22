package hotel.data;

public class Amenity {
    // Data fields.
    private String name;
    private double price;

    // Parameterized Constructor to initialize data.
    public Amenity(String name,double price){
    this.name=name;
    this.price=price;
}

// Getter and Setter methods.
public double getPrice() {
        return price;
}
public void setprice(double p){
        while(p<0){
            System.out.println("invalid please try again");
        }
        this.price=p;
    }

public String getName(){
    return name;
}
public void setName(){
    if(name==null||name.isEmpty()){
        System.out.println("Name cannot be empty!");
        }
    }
}