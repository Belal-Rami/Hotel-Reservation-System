package hotel.data;

public class RoomType {
    // Data fields.
    String name;
    double price;

    public void Single(){
        this.name="single";
        this.price=1000.0;
    }

    public void Double(){
        this.name="double";
        this.price=2000.0;
    }

    public void Suit(){
        this.name="suit";
        this.price=4000.0;
    }

    // Getter and Setter methods.
    public String getname(){
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public double getprice(){
        return price;
    }
    public void setprice(double price) {
        this.price = price;
    }
}
