package com.example.user;

public class model {
    String address;
    static String image;

    public model(String address, String image) {
        this.address = address;
        this.image = image;
    }

    public model() {

    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
