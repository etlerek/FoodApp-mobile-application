package com.example.foodapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodapp.R;
import com.example.foodapp.asynctask.PullFromDb;
import com.example.foodapp.databasehandlers.FridgeDatabaseHandler;
import com.example.foodapp.databasehandlers.IngredientDatabaseHandler;
import com.example.foodapp.databasehandlers.IngredientsToRecipeDatabaseHandler;
import com.example.foodapp.databasehandlers.MainDatabaseHandler;
import com.example.foodapp.databasehandlers.RecipeDatabaseHandler;
import com.example.foodapp.databasehandlers.SavedRecipeDatabaseHandler;
import com.example.foodapp.databasehandlers.ShoppingListDatabaseHandler;
import com.example.foodapp.databasehandlers.StepsToRecipeDatabaseHandler;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        MainDatabaseHandler.addTable(new IngredientDatabaseHandler().getTable());
        MainDatabaseHandler.addTable(new FridgeDatabaseHandler().getTable());
        MainDatabaseHandler.addTable(new RecipeDatabaseHandler().getTable());
        MainDatabaseHandler.addTable(new IngredientsToRecipeDatabaseHandler().getTable());
        MainDatabaseHandler.addTable(new StepsToRecipeDatabaseHandler().getTable());
        MainDatabaseHandler.addTable(new ShoppingListDatabaseHandler().getTable());
        MainDatabaseHandler.addTable(new SavedRecipeDatabaseHandler().getTable());

        MainDatabaseHandler dbHandler = MainDatabaseHandler.getInstance(this);
        dbHandler.getReadableDatabase();

        //new ScrapIngredients(this).execute(null,null,null);
        new PullFromDb(this).execute(null, null, null);

        ImageButton goToFridgeActivity = findViewById(R.id.to_fridge_activ);
        ImageButton goToAddRecipeActivity = findViewById(R.id.to_add_recipe_activity);
        Button goToIngredientsActivity = findViewById(R.id.to_browse_ingredients_activity);
        ImageButton goToBrowseRecipesActivity = findViewById(R.id.to_browse_recipes_activity);
        ImageButton goToShoppingListActivity = findViewById(R.id.to_shopping_list_activity);
        ImageButton goToSavedRecipesActivity = findViewById(R.id.to_saved_recipes_activity);

        goToIngredientsActivity.setOnClickListener(new GoToIngredientsActivityClickListener());
        goToFridgeActivity.setOnClickListener(new GoToFridgeActivityClickListener());
        goToAddRecipeActivity.setOnClickListener(new GoToAddRecipeActivityClickListener());
        goToBrowseRecipesActivity.setOnClickListener(new GoToBrowseRecipesActivityClickListener());
        goToShoppingListActivity.setOnClickListener(new GoToShoppingListActivity());
        goToSavedRecipesActivity.setOnClickListener(new GoToSavedRecipesActivity());
    }

    class GoToIngredientsActivityClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MenuActivity.this, BrowseIngredientsActivity.class);
            startActivity(intent);
        }
    }

    class GoToFridgeActivityClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MenuActivity.this, FridgeActivity.class);
            startActivity(intent);
        }
    }

    class GoToAddRecipeActivityClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MenuActivity.this, AddRecipeActivity.class);
            startActivity(intent);
        }
    }

    class GoToBrowseRecipesActivityClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MenuActivity.this, BrowseRecipesActivity.class);
            startActivity(intent);
        }
    }

    class GoToShoppingListActivity implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MenuActivity.this, ShoppingListActivity.class);
            startActivity(intent);
        }
    }

    class GoToSavedRecipesActivity implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MenuActivity.this, SavedRecipesActivity.class);
            startActivity(intent);
        }
    }
}
