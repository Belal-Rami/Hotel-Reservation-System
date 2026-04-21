package hotel.services;

import hotel.data.Amenity;
import hotel.data.Room;

import java.util.ArrayList;

public class AmenityManager {
        private ArrayList<Amenity> HotelAmenities = new ArrayList<>();
        private RoomManager roommanager;
        Boolean found=false;
        public AmenityManager(RoomManager roommanager){
                this.roommanager=roommanager;
        }
        public void addamenitytohotel(String name,double price){
                HotelAmenities.add(new Amenity(name,price));
        }
        public void addamenitytoroom(Amenity amenity, Room room){
                room.getamenities().add(amenity);
        }
        public void removeamenityroom(Amenity amenity,Room room){
                for(Room r:roommanager.getrooms()) {
                        for (int i=0;i<r.getamenities().size();i++) {
                                if (r.getamenities().get(i).getName().equalsIgnoreCase(amenity.getName())) {
                                        r.getamenities().remove(i);
                                        System.out.println("The amenity is removed successfully!");
                                }
                        }
                }
        }
        public void removeAminityfromHotel(Amenity amenity) {
                for(int i=0;i<HotelAmenities.size();i++){
                        if(HotelAmenities.get(i).getName().equalsIgnoreCase(amenity.getName())){
                                found=true;
                        }else{
                                System.out.println("Amenity not found!");
                        }
                }
                while (found=true) {
                        for (int i = 0; i < HotelAmenities.size(); i++) {
                                if (amenity.getName() == HotelAmenities.get(i).getName()) {
                                        HotelAmenities.remove(i);
                                }
                        }
                        for (Room r : roommanager.getrooms()) {
                                for (int i = 0; i < r.getamenities().size(); i++) {
                                        if (r.getamenities().get(i).getName().equalsIgnoreCase(amenity.getName())) {
                                                r.getamenities().remove(i);
                                        }
                                }
                        }
                        System.out.println("Succesfully removed!");
                }
        }
        public void updateamenityprice(Amenity amenity,double newprice){
                amenity.setPrice(newprice);
                System.out.println("The price is updated!");
        }
    
}
