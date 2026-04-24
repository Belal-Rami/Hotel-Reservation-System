package hotel.services;

import java.util.ArrayList;

import hotel.data.Amenity;

public class AmenityManager {
        // DATA FEILDS
        private ArrayList<Amenity> hotelAmenities;

        // CONSTRUCTOR
        public AmenityManager(ArrayList<Amenity> hotelAmenities) {
                this.hotelAmenities=hotelAmenities;
        }


        // CRUD METHODS
        public void createAmenity(String name, double price){
                hotelAmenities.add(new Amenity(name, price));
        }

        public void deleteAmenity(Amenity amenity, RoomManager roomManager) {
                // Remove the amenity from the hotel's list of amenities
                boolean isRemoved = hotelAmenities.remove(amenity);
                
                // Remove the amenity from all rooms that have it
                // only runs the loop if there is an amenity to be removed
                if (isRemoved) {
                        roomManager.deleteAmenity(amenity);

                }             
        }
        public void updateAmenityPrice(Amenity amenity, double newprice){
                amenity.setPrice(newprice);
        }

    }
