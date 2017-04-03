package com.oudersonsstudios.restaurantrecommendations.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ActionProvider;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.oudersonsstudios.restaurantrecommendations.Models.Food;
import com.oudersonsstudios.restaurantrecommendations.Models.Restaurant;
import com.oudersonsstudios.restaurantrecommendations.R;
import com.oudersonsstudios.restaurantrecommendations.Utils.Constants;
import com.oudersonsstudios.restaurantrecommendations.Utils.ExpandableListAdapter;
import com.oudersonsstudios.restaurantrecommendations.Utils.HandleMenu;
import com.oudersonsstudios.restaurantrecommendations.Utils.InternalStorage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RestaurantListActivity extends AppCompatActivity {
    private ActionProvider mActionProvider;

    private Context mContext;

    private List<Restaurant> restaurantsList = new ArrayList<>();
    private List<Food> foodList = new ArrayList<>();
    private ExpandableListView restaurantList;

    private HashMap<String, List<Food>> restaurantFoodItemsMapped;

    private Button newFoodButton;
    private Button newRestaurantButton;

    private boolean aRestaurantIsExpanded;
    private Restaurant expandedRestaurant;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        mContext = this;

        restaurantList = (ExpandableListView) findViewById(R.id.restaurantListView);
        buildRestaurantList();


        restaurantList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                Food clickedFood = restaurantsList.get(groupPosition).getFoodEntries().get(childPosition);

                Intent intent = new Intent(mContext, FoodDetailActivity.class);
                intent.putExtra("showing_details", true);
                intent.putExtra("food_name", clickedFood.getName());
                intent.putExtra("food_picture_path", clickedFood.getPicture().getImagePath());
                intent.putExtra("food_price", clickedFood.getPrice());
                intent.putExtra("food_description", clickedFood.getDescription());

                startActivity(intent);
                return true;
            }
        });

        restaurantList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (aRestaurantIsExpanded && !expandedRestaurant.getName().equals(restaurantsList.get(groupPosition).getName())){
                    restaurantList.collapseGroup(restaurantsList.indexOf(expandedRestaurant));
                }
                aRestaurantIsExpanded = true;
                expandedRestaurant = restaurantsList.get(groupPosition);

                newRestaurantButton.setText("View Restaurant Details");
                newRestaurantButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Restaurant clickedRestaurant = restaurantsList.get(restaurantsList.indexOf(expandedRestaurant));

                        Intent intent = new Intent(mContext, RestaurantDetailActivity.class);
                        intent.putExtra("showing_details", true);
                        intent.putExtra("restaurant_name", clickedRestaurant.getName());
                        intent.putExtra("restaurant_picture_path", clickedRestaurant.getPicture().getImagePath());
                        intent.putExtra("restaurant_address", clickedRestaurant.getAddress());
                        intent.putExtra("restaurant_description", clickedRestaurant.getDescription());
                        intent.putExtra("restaurant_genre", clickedRestaurant.getFoodType());

                        startActivity(intent);
                    }
                });

                newFoodButton.setVisibility(View.VISIBLE);
            }
        });

        restaurantList.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                aRestaurantIsExpanded = false;
                expandedRestaurant = null;

                newRestaurantButton.setText("New Restaurant");
                newRestaurantButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, RestaurantDetailActivity.class);
                        startActivityForResult(intent, 1);
                    }
                });

                newFoodButton.setVisibility(View.INVISIBLE);
            }
        });

        newRestaurantButton = (Button) findViewById(R.id.newRestaurantButton);
        newRestaurantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RestaurantDetailActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        newFoodButton = (Button) findViewById(R.id.newFoodButton);
        newFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, FoodDetailActivity.class);
                intent.putExtra(Constants.RESTAURANT_NAME, expandedRestaurant.getName());
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_toolbar_menu, menu);

        MenuItem shareItem = menu.findItem(R.menu.main_toolbar_menu);

        // To retrieve the Action Provider
        mActionProvider = (ShareActionProvider)
                MenuItemCompat.getActionProvider(shareItem);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (!HandleMenu.onOMenuItemSelected(item, this)) {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user yook s picture.
                // The Intent's data Uri identifies which contact was selected.
                buildRestaurantList();
                // Do something with the contact here (bigger example below)
            }
        }
    }

    private void buildRestaurantList(){
        Object[] lists = new Object[2];
        try {
            lists = InternalStorage.buildDataFromStorage(mContext);
            restaurantsList = (List<Restaurant>) lists[1];
            foodList = (List<Food>) lists[0];
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "Could not read data from text file", Toast.LENGTH_SHORT).show();
            restaurantsList = new ArrayList<>();
            foodList = new ArrayList<>();
        }

        List<String> restaurantNamesList = new ArrayList<>();
        restaurantFoodItemsMapped = new HashMap<String, List<Food>>();

        for (Restaurant restaurant : restaurantsList){
            restaurantNamesList.add(restaurant.getName());
            List<Food> foodForRestaurant = new ArrayList<>();
            for (Food food : foodList){
                if (food.getRestaurant().equals(restaurant.getName())){
                    foodForRestaurant.add(food);
                    restaurant.addNewFood(food);
                }
            }
            restaurantFoodItemsMapped.put(restaurant.getName(), foodForRestaurant);
        }

        ExpandableListAdapter adapter = new ExpandableListAdapter(mContext, restaurantNamesList, restaurantFoodItemsMapped);
        restaurantList.setAdapter(adapter);
    }
}
