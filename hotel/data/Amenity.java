package hotel.data;

public class Amenity {
    private String name;
    private double price;
    public Amenity(String name){
        this(name, 0);
    }

    public Amenity(String name, double price){
        setName(name);
        setPrice(price);
    }
}
public String getName(){
    return name;
}

public void setName(String name){
    if(name == null || name.isEmpty()){
        throw new IllegalArgumentException("Invalid name");
    }
    this.name = name;
}

public double getPrice(){
    return price;
}

public void setPrice(double price){
    if(price < 0){
        throw new IllegalArgumentException("Invalid price");
    }
    this.price = price;
}

@Override
public String toString() {
    return "Amenity: " + name + ", price: " + price;
}
