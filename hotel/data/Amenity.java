package hotel.data;

public class Amenity {
    private String name;
    private double price;
public Amenity(String name,double price){
   this.name=name;
   this.price=price;
}
public double getprice() {
    return price;
}
public String getname(){
    return name;
}
public void setprice(double p){
    while(p<0){
        System.out.println("invalid please try again");
    }
    this.price=p;
}
public void setname(){
    if(name==null||name.isEmpty()){
        System.out.println("Name cannot be empty!");
    }else{

    }
}
}