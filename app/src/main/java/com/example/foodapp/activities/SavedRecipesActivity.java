package com.example.foodapp.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.R;
import com.example.foodapp.adapters.RecipesAdapter;
import com.example.foodapp.algorithmhandler.MatchingAlgorithm;
import com.example.foodapp.daohandlers.SavedRecipesDAO;
import com.example.foodapp.entity.Recipe;

import java.util.ArrayList;
import java.util.List;

public class SavedRecipesActivity extends AppCompatActivity {

    private MatchingAlgorithm matchingAlgorithm;
    private RecipesAdapter recipesAdapter;
    private RecyclerView recyclerView;
    private List<Recipe> recipes;
    private SavedRecipesDAO savedRecipesDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_favourite_recipes);

        matchingAlgorithm = new MatchingAlgorithm(this);
        savedRecipesDAO = new SavedRecipesDAO(this);
        recipes = savedRecipesDAO.getSavedRecipesList();

        recyclerView = findViewById(R.id.recipes_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recipesAdapter = new RecipesAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(recipesAdapter);

        recipesAdapter.setItems(matchingAlgorithm.matchRecipeToFridgeIngredients(recipes));
        recipesAdapter.notifyDataSetChanged();
        matchingAlgorithm.showFridgeIngredients();
    }

    @Override
    protected void onResume() {
        super.onResume();
        recipes = savedRecipesDAO.getSavedRecipesList();
        recipesAdapter.setItems(matchingAlgorithm.matchRecipeToFridgeIngredients(recipes));
        recipesAdapter.notifyDataSetChanged();
    }
}