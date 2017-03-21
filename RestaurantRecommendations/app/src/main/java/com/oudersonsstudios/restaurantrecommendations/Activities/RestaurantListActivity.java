package com.oudersonsstudios.restaurantrecommendations.Activities;

import android.os.Bundle;
import android.support.v4.view.ActionProvider;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.oudersonsstudios.restaurantrecommendations.R;
import com.oudersonsstudios.restaurantrecommendations.Utils.HandleMenu;

public class RestaurantListActivity extends AppCompatActivity {
    private ActionProvider mActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        ListView restaurantList = (ListView) findViewById(R.id.restaurantListView);

        restaurantList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //Whatever we do when we click on the list goes here. Expand the clicked on bit.
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
}
