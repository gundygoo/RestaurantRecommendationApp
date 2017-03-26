package com.oudersonsstudios.restaurantrecommendations.Utils;

import android.content.Context;

import com.oudersonsstudios.restaurantrecommendations.Models.Food;
import com.oudersonsstudios.restaurantrecommendations.Models.Picture;
import com.oudersonsstudios.restaurantrecommendations.Models.Restaurant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Erik on 3/26/2017.
 */

public class InternalStorage {

    public static File getFile(Context context){
        File internalFile = new File(context.getFilesDir(), Constants.STORAGE_FILENAME);

        return internalFile;
    }

    public static void writeNewFood(Food writeFood, Context context) throws IOException{
        FileOutputStream fos = context.openFileOutput(Constants.STORAGE_FILENAME, Context.MODE_APPEND);

        String writeString =  Constants.FOOD_STRING + ","
                + writeFood.getName() + ","
                + writeFood.getPicture().getName() + ","
                + writeFood.getPicture().getImagePath() + ","
                + writeFood.getPrice() + ","
                + writeFood.getDescription() + ","
                + writeFood.getRestaurant() + "\n";

        fos.write(writeString.getBytes());
        fos.close();
    }

    public static void writeNewRestaurant(Restaurant writeRestaurant, Context context) throws IOException{
        FileOutputStream fos = context.openFileOutput(Constants.STORAGE_FILENAME, Context.MODE_APPEND);

        String writeString =  Constants.RESTAURANT_STRING + ","
                + writeRestaurant.getName() + ","
                + writeRestaurant.getDescription() + ","
                + writeRestaurant.getAddress() + ","
                + writeRestaurant.getFoodType() + ","
                + writeRestaurant.getPicture().getName() + ","
                + writeRestaurant.getPicture().getImagePath() + "\n";

        fos.write(writeString.getBytes());
        fos.close();
    }

    public static Object[] buildDataFromStorage(Context context) throws IOException{
        Object[] returnArray = new Object[2];
        List<Food> foodList = new ArrayList<>();
        List<Restaurant> restaurantList = new ArrayList<>();

        FileInputStream fis = context.openFileInput(Constants.STORAGE_FILENAME);
        BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

        String currentLine = reader.readLine();
        while(currentLine != null){
            String[] readValues = currentLine.split(",");
            if (readValues[0].equals(Constants.FOOD_STRING)){
                Food newFood = new Food(readValues[1],
                        new Picture(readValues[2], readValues[3]),
                        readValues[4],
                        readValues[5],
                        readValues[6]);

                foodList.add(newFood);
            }
            else if (readValues[0].equals(Constants.RESTAURANT_STRING)){
                Restaurant newRestaurant = new Restaurant(new ArrayList<Food>(),
                        readValues[1],
                        readValues[2],
                        readValues[3],
                        readValues[4],
                        new Picture(readValues[5], readValues[6]));

                restaurantList.add(newRestaurant);
            }
            currentLine = reader.readLine();
        }

        returnArray[0] = foodList;
        returnArray[1] = restaurantList;
        return returnArray;
    }
}
