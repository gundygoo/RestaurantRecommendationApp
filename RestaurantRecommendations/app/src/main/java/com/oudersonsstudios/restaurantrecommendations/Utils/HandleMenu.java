package com.oudersonsstudios.restaurantrecommendations.Utils;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.Toast;

import com.oudersonsstudios.restaurantrecommendations.Activities.CameraActivity;
import com.oudersonsstudios.restaurantrecommendations.Activities.FoodDetailActivity;
import com.oudersonsstudios.restaurantrecommendations.Activities.RestaurantDetailActivity;
import com.oudersonsstudios.restaurantrecommendations.Activities.RestaurantListActivity;
import com.oudersonsstudios.restaurantrecommendations.MainActivity;
import com.oudersonsstudios.restaurantrecommendations.R;

/**
 * Created by Erik on 3/20/2017.
 */

public class HandleMenu {
    public static boolean onOMenuItemSelected(MenuItem item, Activity act){
        int id = item.getItemId();
        Intent intent = null;

        switch(id){
            case R.id.action_camActivity:
                if (act.getClass() != CameraActivity.class){
                    intent = new Intent(act, CameraActivity.class);
                    act.startActivity(intent);
                    return true;
                }
                Toast.makeText(act.getBaseContext(), "Clicked on Current Activity", Toast.LENGTH_SHORT).show();
                return false;

            case R.id.action_foodDetail:
                if (act.getClass() != FoodDetailActivity.class){
                    intent = new Intent(act, FoodDetailActivity.class);
                    act.startActivity(intent);
                    return true;
                }
                Toast.makeText(act.getBaseContext(), "Clicked on Current Activity", Toast.LENGTH_SHORT).show();
                return false;

            case R.id.action_restDetailActivity:
                if (act.getClass() != RestaurantDetailActivity.class){
                    intent = new Intent(act, RestaurantDetailActivity.class);
                    act.startActivity(intent);
                    return true;
                }
                Toast.makeText(act.getBaseContext(), "Clicked on Current Activity", Toast.LENGTH_SHORT).show();
                return false;

            case R.id.action_restListActivity:
                if (act.getClass() != RestaurantListActivity.class){
                    intent = new Intent(act, RestaurantListActivity.class);
                    act.startActivity(intent);
                    return true;
                }
                Toast.makeText(act.getBaseContext(), "Clicked on Current Activity", Toast.LENGTH_SHORT).show();
                return false;

            case R.id.action_mainActivity:
                if (act.getClass() != MainActivity.class){
                    intent = new Intent(act, MainActivity.class);
                    act.startActivity(intent);
                    return true;
                }
                Toast.makeText(act.getBaseContext(), "Clicked on Current Activity", Toast.LENGTH_SHORT).show();
                return false;
        }
        return false;
    }
}
