package com.example.foodapp.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.R;
import com.example.foodapp.adapters.RecipesAdapter;
import com.example.foodapp.algorithmhandler.MatchingAlgorithm;
import com.example.foodapp.daohandlers.RecipeDAO;
import com.example.foodapp.entity.Recipe;

import java.util.ArrayList;
import java.util.List;

public class BrowseRecipesActivity extends AppCompatActivity {

    private MatchingAlgorithm matchingAlgorithm;
    private LinearLayout linearLayoutFiltersContent;
    private RecipesAdapter recipesAdapter;
    private RecyclerView recyclerView;
    private List<Recipe> recipes;
    private RecipeDAO recipeDAO;
    private Button filtersButton;
    private Spinner filterCategorySpinner;
    private Spinner filterDifficultySpinner;
    private EditText filterTimeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_recipes);

        matchingAlgorithm = new MatchingAlgorithm(this);
        recipeDAO = new RecipeDAO(this);
        recipes = recipeDAO.getRecipesList();

        filterCategorySpinner = findViewById(R.id.category_spinner);
        filterDifficultySpinner = findViewById(R.id.difficulty_spinner);
        filterTimeEditText = findViewById(R.id.time_edit_text);

        linearLayoutFiltersContent = findViewById(R.id.ll_filters_content);
        linearLayoutFiltersContent.setVisibility(View.GONE);
        LinearLayout helperLinearLayout = findViewById(R.id.test);
        helperLinearLayout.setVisibility(View.GONE);
        filtersButton = findViewById(R.id.filters_button);
        filtersButton.setOnClickListener(v -> {
            if (linearLayoutFiltersContent.getVisibility() == View.VISIBLE) {
                linearLayoutFiltersContent.setVisibility(View.GONE);
                helperLinearLayout.setVisibility(View.GONE);
            } else {
                linearLayoutFiltersContent.setVisibility(View.VISIBLE);
                helperLinearLayout.setVisibility(View.VISIBLE);
            }
        });

        recyclerView = findViewById(R.id.recipes_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recipesAdapter = new RecipesAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(recipesAdapter);

        recipesAdapter.setItems(matchingAlgorithm.matchRecipeToFridgeIngredients(recipes));
        recipesAdapter.notifyDataSetChanged();
        matchingAlgorithm.showFridgeIngredients();

        filterCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                applyFilters();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        filterDifficultySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                applyFilters();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        filterTimeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                applyFilters();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    private void applyFilters() {
        String time;
        if (filterTimeEditText.getText() == null) {
            time = "0";
        } else {
            time = filterTimeEditText.getText().toString();
        }
        List<Recipe> filteredList = matchingAlgorithm.filterRecipes(
                recipes,
                (String) filterCategorySpinner.getSelectedItem(),
                (String) filterDifficultySpinner.getSelectedItem(),
                time
        );
        recipesAdapter.setItems(matchingAlgorithm.matchRecipeToFridgeIngredients(filteredList));
        recipesAdapter.notifyDataSetChanged();
    }
}