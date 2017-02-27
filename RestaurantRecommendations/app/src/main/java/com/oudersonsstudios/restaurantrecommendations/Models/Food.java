package com.oudersonsstudios.restaurantrecommendations.Models;

/**
 * Created by Erik on 2/26/2017.
 */

public class Food {
    private String name;
    private Picture picture;
    private String price;
    private String description;

    public Food(String name, Picture picture, String description, String price) {

        this.name = name;
        this.picture = picture;
        this.description = description;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Picture getPicture() {
        return picture;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public void updatePrice(String newPrice){
        price = newPrice;
    }

    public void updateName(String newName) {
        name = newName;
    }

    public void updateDescription(String newDescription) {
        description = newDescription;
    }

    public void updatePicture(Picture newPicture){
        picture = newPicture;
    }
}
