package com.example.aahar;

public class ModelDonation {
    private String pictureLink,name,phone,quantity,location,locationLink,booked;

    public ModelDonation() {
    }

    public ModelDonation(String pictureLink, String name, String phone, String quantity, String location, String locationLink, String booked) {
        this.pictureLink = pictureLink;
        this.name = name;
        this.phone = phone;
        this.quantity = quantity;
        this.location = location;
        this.locationLink = locationLink;
        this.booked = booked;
    }

    public String getPictureLink() {
        return pictureLink;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getLocation() {
        return location;
    }

    public String getLocationLink() {
        return locationLink;
    }

    public String getBooked() {
        return booked;
    }
}
