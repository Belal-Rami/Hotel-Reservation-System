package hotel.data;

public class RoomType {
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
    public String getname(){
        return name;
    }
    public double getprice(){
        return price;
    }
}
