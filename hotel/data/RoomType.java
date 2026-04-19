package hotel.data;

public class RoomType {
    private String name;
    private double price;
    public RoomType(String name){
        this(name,0);
    }
    public RoomType(String name,double price){
        setName(name);
        setPrice(price);
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        if(name==null||name.isEmpty()){
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name=name;
    }
    public double getPrice(){
        return price;
    }
    public void setPrice(double price){
        if(price<0){
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price=price;
    }
    @Override
    public String toString() {
        return "RoomType: " + name + " - price: " + price;
    }
    public void singleroom(Room s){
      s.setroomtype("single");
      s.setroomprice(1000.0);
    }
    public void doubleroom(Room s){
        s.setroomtype("Double");
        s.setroomprice(2000.0);
    }

    public void suitroom(Room s){
        s.setroomtype("Suit");
        s.setroomprice(4000.0);
    }
}
