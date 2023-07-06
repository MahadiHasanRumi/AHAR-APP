package com.example.aahar;

public class ModelMyPost {
    private String pictureLink,quantity,location,key,booked;

    public ModelMyPost(String pictureLink, String quantity, String location, String key, String booked) {
        this.pictureLink = pictureLink;
        this.quantity = quantity;
        this.location = location;
        this.key = key;
        this.booked = booked;
    }

    public ModelMyPost() {
    }

    public String getPictureLink() {
        return pictureLink;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getLocation() {
        return location;
    }

    public String getKey() {
        return key;
    }

    public String getBooked() {
        return booked;
    }
}
