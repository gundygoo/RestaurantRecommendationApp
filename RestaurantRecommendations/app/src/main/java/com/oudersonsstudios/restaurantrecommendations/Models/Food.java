package com.oudersonsstudios.restaurantrecommendations.Models;

/**
 * Created by Erik on 2/26/2017.
 */

public class Food {
    private String name;
    private Picture picture;
    private String price;
    private String description;
    private String restaurant;

    public Food(String name, Picture picture, String price, String description, String restaurant) {
        this.name = name;
        this.picture = picture;
        this.price = price;
        this.description = description;
        this.restaurant = restaurant;
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

    public String getRestaurant() {
        return restaurant;
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

    public void updateRestaurant(String newRestaurant) {
        restaurant = newRestaurant;
    }
}
