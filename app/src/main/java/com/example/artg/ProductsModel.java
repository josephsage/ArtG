package com.example.artg;

import java.io.Serializable;

public class ProductsModel implements Serializable {

    private String Title;
    private String ArtImage;
    private String Description;
    private String Artist;
    private String Phone;
    private String Price;
    private String ArtistName;

    private ProductsModel(){

    }

    ProductsModel(String Title,String ArtImage,String Description,
                  String Artist, String Phone, String Price, String ArtistName) {

        this.Title = Title;
        this.ArtImage = ArtImage;
        this.Description = Description;
        this.Artist = Artist;
        this.Phone = Phone;
        this.Price = Price;
        this.ArtistName = ArtistName;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getArtImage() {
        return ArtImage;
    }

    public void setArtImage(String artImage) {
        this.ArtImage = artImage;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getArtist() {
        return Artist;
    }

    public void setArtist(String artist) {
        Artist = artist;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getArtistName() {
        return ArtistName;
    }

    public void setArtistName(String artistName) {
        ArtistName = artistName;
    }

    // public String getDescription(){return Description;}

   // public void getDescription(String Description) {this.Description = Description;}
}
