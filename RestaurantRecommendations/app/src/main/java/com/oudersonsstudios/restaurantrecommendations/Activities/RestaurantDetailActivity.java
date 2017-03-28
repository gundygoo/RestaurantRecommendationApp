package com.oudersonsstudios.restaurantrecommendations.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.ActionProvider;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.oudersonsstudios.restaurantrecommendations.Models.Food;
import com.oudersonsstudios.restaurantrecommendations.Models.Picture;
import com.oudersonsstudios.restaurantrecommendations.Models.Restaurant;
import com.oudersonsstudios.restaurantrecommendations.R;
import com.oudersonsstudios.restaurantrecommendations.Utils.HandleMenu;
import com.oudersonsstudios.restaurantrecommendations.Utils.InternalStorage;

import java.io.IOException;
import java.util.ArrayList;

public class RestaurantDetailActivity extends AppCompatActivity {
    private ActionProvider mActionProvider;

    private Context mContext;

    private Picture restaurantPicture = new Picture("","");
    private ImageButton imageButton;
    private EditText restaurantName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);

        mContext = this;
        restaurantName = (EditText) findViewById(R.id.restaurantNameEditText);

        Button saveButton = (Button) findViewById(R.id.saveButton);
        imageButton = (ImageButton) findViewById(R.id.restaurantImageButton);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CameraActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Handle Image
                EditText restaurantDescription = (EditText) findViewById(R.id.restaurantDescriptionEditText);
                EditText restaurantAddress = (EditText) findViewById(R.id.restaurantAddressEditText);
                EditText restaurantGenre = (EditText) findViewById(R.id.restaurantGenreEditText);

                Restaurant newRestaurant = new Restaurant(new ArrayList<Food>(),
                        restaurantName.getText().toString(),
                        restaurantDescription.getText().toString(),
                        restaurantAddress.getText().toString(),
                        restaurantGenre.getText().toString(),
                        restaurantPicture);
                try {
                    InternalStorage.writeNewRestaurant(newRestaurant, mContext);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, "Failed to write to Internal Storage.", Toast.LENGTH_SHORT).show();
                }

                Intent resultIntent = new Intent();
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
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
        if (requestCode == 0) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user yook s picture.
                // The Intent's data Uri identifies which contact was selected.
                restaurantPicture = new Picture(restaurantName.getText().toString()+"Image", data.getStringExtra("imagePath"));
                imageButton.setImageBitmap(BitmapFactory.decodeFile(data.getStringExtra("imagePath")));
                // Do something with the contact here (bigger example below)
            }
        }
    }
}
