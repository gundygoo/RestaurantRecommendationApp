package com.oudersonsstudios.restaurantrecommendations.Activities;

import android.os.Bundle;
import android.support.v4.view.ActionProvider;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;

import com.oudersonsstudios.restaurantrecommendations.R;
import com.oudersonsstudios.restaurantrecommendations.Utils.HandleMenu;

public class FoodDetailActivity extends AppCompatActivity {
    private ActionProvider mActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
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
}
