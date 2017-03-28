package com.oudersonsstudios.restaurantrecommendations.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.ActionProvider;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.oudersonsstudios.restaurantrecommendations.Models.Food;
import com.oudersonsstudios.restaurantrecommendations.Models.Picture;
import com.oudersonsstudios.restaurantrecommendations.Models.Restaurant;
import com.oudersonsstudios.restaurantrecommendations.R;
import com.oudersonsstudios.restaurantrecommendations.Utils.HandleMenu;
import com.oudersonsstudios.restaurantrecommendations.Utils.InternalStorage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RestaurantListActivity extends AppCompatActivity {
    private ActionProvider mActionProvider;

    private Context mContext;

    private List<Restaurant> restaurantsList = new ArrayList<>();
    private List<Food> foodList = new ArrayList<>();
    private ListView restaurantList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        mContext = this;

        restaurantList = (ListView) findViewById(R.id.restaurantListView);
        buildRestaurantList();


        restaurantList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //Whatever we do when we click on the list goes here. Expand the clicked on bit.
            }
        });

        Button newRestaurantButton = (Button) findViewById(R.id.newRestaurantButton);
        newRestaurantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RestaurantDetailActivity.class);
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
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "Could not read data from text file", Toast.LENGTH_SHORT).show();
        }

        restaurantsList = (List<Restaurant>)lists[1];
        foodList = (List<Food>) lists[0];
        List<String> restaurantNamesList = new ArrayList<>();
        for (Restaurant restaurant : restaurantsList){
            restaurantNamesList.add(restaurant.getName());
        }
        ArrayAdapter adapter = new ArrayAdapter(mContext, R.layout.activity_restaurant_list_item, restaurantNamesList);
        restaurantList.setAdapter(adapter);
    }
}
