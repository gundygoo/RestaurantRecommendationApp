package com.oudersonsstudios.restaurantrecommendations.Models;

/**
 * Created by Erik on 2/26/2017.
 */

public class Picture {
    private String name;
    private String imagePath;

    public Picture(String name, String imagePath) {
        this.name = name;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public String getImagePath() {
        return imagePath;
    }
}
