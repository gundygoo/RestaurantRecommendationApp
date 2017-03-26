package com.oudersonsstudios.restaurantrecommendations.Models;

import java.util.List;

/**
 * Created by Erik on 2/26/2017.
 */
public class Restaurant {
    private List<Food> foodEntries;
    private String name;
    private String description;
    private String address;
    private String foodType;
    private Picture picture;

    public Restaurant(List<Food> foodEntries, String name, String description, String address, String foodType, Picture picture) {
        this.foodEntries = foodEntries;
        this.name = name;
        this.description = description;
        this.address = address;
        this.foodType = foodType;
        this.picture = picture;
    }

    public List<Food> getFoodEntries() {
        return foodEntries;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public String getFoodType() {
        return foodType;
    }

    public Picture getPicture() {
        return picture;
    }

    public void updateFoodEntryList(List<Food> newFoodEntries){
        foodEntries = newFoodEntries;
    }

    public void updateName(String newName) {
        name = newName;
    }

    public void updateDescription(String newDescription) {
        description = newDescription;
    }

    public void updateAddress(String newAddress) {
        address = newAddress;
    }

    public void updateFoodType(String newFoodType) {
        foodType = newFoodType;
    }

    public void updatePicture(Picture newPicture) {
        picture = newPicture;
    }

    public void addNewFood(Food newFoodItem){
        foodEntries.add(newFoodItem);
    }

    public void removeFood(Food foodItem){
        foodEntries.remove(foodItem);
    }
}
