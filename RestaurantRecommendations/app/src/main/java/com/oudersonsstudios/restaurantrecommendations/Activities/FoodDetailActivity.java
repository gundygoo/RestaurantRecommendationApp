package com.oudersonsstudios.restaurantrecommendations.Activities;

import android.app.Activity;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.oudersonsstudios.restaurantrecommendations.Models.Food;
import com.oudersonsstudios.restaurantrecommendations.Models.Picture;
import com.oudersonsstudios.restaurantrecommendations.R;
import com.oudersonsstudios.restaurantrecommendations.Utils.Constants;
import com.oudersonsstudios.restaurantrecommendations.Utils.HandleMenu;
import com.oudersonsstudios.restaurantrecommendations.Utils.InternalStorage;

import java.io.IOException;

public class FoodDetailActivity extends AppCompatActivity {
    private ActionProvider mActionProvider;
    private Context mContext;

    private Picture foodPicture = new Picture("", "");
    private ImageButton imageButton;
    private EditText foodName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        mContext = this;

        foodName = (EditText) findViewById(R.id.foodNameEditText);
        Button saveButton = (Button) findViewById(R.id.saveButton);
        imageButton = (ImageButton) findViewById(R.id.foodImageButton);

        if (getIntent().hasExtra("showing_details") && getIntent().getBooleanExtra("showing_details", false)){
            foodName.setText(getIntent().getStringExtra("food_name"));
            foodPicture = new Picture("FOODPICTURE", getIntent().getStringExtra("food_picture_path"));
            imageButton.setImageBitmap(BitmapFactory.decodeFile(foodPicture.getImagePath()));

            EditText foodPrice = (EditText) findViewById(R.id.foodPriceEditText);
            foodPrice.setText(getIntent().getStringExtra("food_price"));

            EditText foodDescription = (EditText) findViewById(R.id.foodDescriptionEditText);
            foodDescription.setText(getIntent().getStringExtra("food_description"));

            saveButton.setVisibility(View.INVISIBLE);

            return;
        }

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CameraActivity.class);
                startActivityForResult(intent, 2);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText foodDescription = (EditText) findViewById(R.id.foodDescriptionEditText);
                EditText foodPrice = (EditText) findViewById(R.id.foodPriceEditText);

                Food newFood = new Food(foodName.getText().toString(),
                        foodPicture,
                        foodPrice.getText().toString(),
                        foodDescription.getText().toString(),
                        getIntent().getStringExtra(Constants.RESTAURANT_NAME));

                try{
                    InternalStorage.writeNewFood(newFood, mContext);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, "Failed to write to Internal Storage", Toast.LENGTH_SHORT).show();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == 2){
            if (resultCode == RESULT_OK){
                foodPicture = new Picture(foodName.getText().toString()+"Image", data.getStringExtra("imagePath"));
                imageButton.setImageBitmap(BitmapFactory.decodeFile(data.getStringExtra("imagePath")));
            }
        }
    }
}
