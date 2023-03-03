package com.example.foodapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.R;
import com.example.foodapp.adapters.RecipeIngredientsAdapter;
import com.example.foodapp.adapters.StepsAdapter;
import com.example.foodapp.daohandlers.RecipeDAO;
import com.example.foodapp.daohandlers.SavedRecipesDAO;
import com.example.foodapp.entity.Recipe;

public class RecipeActivity extends AppCompatActivity {

    private TextView categorySpinner;
    private TextView nameEditText;
    private TextView difficultySpinner;
    private TextView timeEditText;
    private TextView generalDescEditText;
    private TextView portionsEditText;
    private TextView creationDate;
    private RecyclerView mainIngredientsRecyclerView;
    private RecyclerView optionalIngredientsRecyclerView;
    private RecyclerView stepsRecyclerView;
    private Button addToFavouritesButton;
    private SavedRecipesDAO savedRecipesDAO;
    private Recipe recipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        RecipeDAO recipeDAO = new RecipeDAO(this);
        savedRecipesDAO = new SavedRecipesDAO(this);
        Intent intent = getIntent();
        recipe = recipeDAO.getRecipe(intent.getIntExtra("id", 0));

        categorySpinner = findViewById(R.id.category_spinner);
        nameEditText = findViewById(R.id.name_edit_text);
        difficultySpinner = findViewById(R.id.difficulty_spinner);
        timeEditText = findViewById(R.id.time_edit_text);
        generalDescEditText = findViewById(R.id.general_desc_edit_text);
        portionsEditText = findViewById(R.id.portions_edit_text);
        mainIngredientsRecyclerView = findViewById(R.id.main_ingredients_recycler_view);
        optionalIngredientsRecyclerView = findViewById(R.id.optional_ingredients_recycler_view);
        stepsRecyclerView = findViewById(R.id.steps_recycler_view);
        addToFavouritesButton = findViewById(R.id.add_to_favourites);
        creationDate = findViewById(R.id.creation_date_id);


        if (intent.getStringExtra("previousActivity").equals("BrowseRecipesActivity")) {
            addToFavouritesButton.setOnClickListener(new AddToSavedClickListener());
            addToFavouritesButton.setText(R.string.favourites_plus);
        } else if (intent.getStringExtra("previousActivity").equals("SavedRecipesActivity")) {
            addToFavouritesButton.setOnClickListener(new DeleteFromSavedClickListener());
            addToFavouritesButton.setText(R.string.favourites_minus);
        } else {
            addToFavouritesButton.setVisibility(View.GONE);
        }

        categorySpinner.setText(recipe.getCategory());
        nameEditText.setText(recipe.getName());
        difficultySpinner.setText(getString(R.string.diff) + " " + recipe.getDifficulty());
        if(difficultySpinner.getText().equals("")){
            difficultySpinner.setVisibility(View.GONE);
        }
        timeEditText.setText(getString(R.string.time) + " " + recipe.getTime() + getString(R.string.min));
        generalDescEditText.setText(recipe.getGeneralDescription());
        portionsEditText.setText(getString(R.string.portions) + " " + recipe.getPortion());
        RecipeIngredientsAdapter mainIngredientsAdapter = new RecipeIngredientsAdapter(this, recipe.getMainIngredients());
        mainIngredientsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mainIngredientsRecyclerView.setAdapter(mainIngredientsAdapter);
        RecipeIngredientsAdapter optionalIngredientsAdapter = new RecipeIngredientsAdapter(this, recipe.getSubIngredients());
        optionalIngredientsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        optionalIngredientsRecyclerView.setAdapter(optionalIngredientsAdapter);
        StepsAdapter stepsAdapter = new StepsAdapter(this, recipe.getSteps());
        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        stepsRecyclerView.setAdapter(stepsAdapter);
        creationDate.setText("Przepis dodano " + recipe.getCreationDate());

    }

    class AddToSavedClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            savedRecipesDAO.addSavedRecipe(recipe.getName());
            Toast.makeText(RecipeActivity.this, R.string.added_to_favourites, Toast.LENGTH_SHORT).show();
        }
    }

    class DeleteFromSavedClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            savedRecipesDAO.deleteSavedRecipe(recipe.getName());
            finish();
        }
    }
}