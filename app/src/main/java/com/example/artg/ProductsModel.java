package com.example.artg;

import java.io.Serializable;

public class ProductsModel implements Serializable {

    private String Title;
    private String ArtImage;
    private String Description;
    private String Artist;
    private String Phone;

    private ProductsModel(){

    }

    ProductsModel(String Title,String ArtImage,String Description,
                  String Artist, String Phone) {

        this.Title = Title;
        this.ArtImage = ArtImage;
        this.Description = Description;
        this.Artist = Artist;
        this.Phone = Phone;
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

    // public String getDescription(){return Description;}

   // public void getDescription(String Description) {this.Description = Description;}
}
