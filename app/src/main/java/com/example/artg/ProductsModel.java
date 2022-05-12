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
    private String Mtn_number;
    private String Email;
    private String Fullname;
    private String Transaction_ID;
    private String BuyerEmail;
    private String BuyerName;

    private ProductsModel() {

    }

    ProductsModel(String Title, String ArtImage, String Description,
                  String Artist, String Phone, String Price, String ArtistName, String Mtn_number,
                  String Email, String Fullname,
                  String Transaction_ID, String BuyerEmail, String BuyerName) {

        this.Title = Title;
        this.ArtImage = ArtImage;
        this.Description = Description;
        this.Artist = Artist;
        this.Phone = Phone;
        this.Price = Price;
        this.ArtistName = ArtistName;
        this.Mtn_number = Mtn_number;
        this.Transaction_ID = Transaction_ID;
        this.Email = Email;
        this.Fullname = Fullname;
        this.BuyerName = BuyerName;
        this.BuyerEmail = BuyerEmail;
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

    public String getMtn_number() {
        return Mtn_number;
    }

    public void setMtn_number(String mtn_number) {
        Mtn_number = mtn_number;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getBuyerEmail() {
        return BuyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        BuyerEmail = buyerEmail;
    }

    public String getBuyerName() {
        return BuyerName;
    }

    public void setBuyerName(String buyerName) {
        BuyerName = buyerName;
    }

    public String getTransaction_ID() {
        return Transaction_ID;
    }

    public void setTransaction_ID(String transaction_ID) {
        Transaction_ID = transaction_ID;
    }
}