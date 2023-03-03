package com.example.foodapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.R;
import com.example.foodapp.adapters.RecipeIngredientsAdapter;
import com.example.foodapp.adapters.StepsAdapter;
import com.example.foodapp.daohandlers.IngredientsToRecipeDAO;
import com.example.foodapp.daohandlers.RecipeDAO;
import com.example.foodapp.daohandlers.StepsToRecipeDAO;
import com.example.foodapp.entity.FridgeIngredient;
import com.example.foodapp.entity.Recipe;
import com.example.foodapp.entity.Step;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AddRecipeActivity extends AppCompatActivity {

    Spinner categorySpinner;
    EditText nameEditText;
    Button addSubcategoryButton;
    Spinner difficultySpinner;
    EditText timeEditText;
    EditText generalDescEditText;
    RecyclerView mainIngredientsRecyclerView;
    Button addMainIngredientButton;
    RecyclerView optionalIngredientsRecyclerView;
    Button addSubIngredientsIngredientButton;
    Button addStepIngredientButton;
    Button addRecipeIngredientButton;
    RecyclerView stepsRecyclerView;
    EditText portionsEditText;
    private RecipeIngredientsAdapter adapterMain;
    private RecipeIngredientsAdapter adapterSub;
    private StepsAdapter stepsAdapter;
    private RecipeDAO recipeDAO;
    private StepsToRecipeDAO stepsToRecipeDAO;
    private IngredientsToRecipeDAO ingredientsToRecipeDAO;
    private ArrayList<FridgeIngredient> mainIngredients;
    private ArrayList<FridgeIngredient> subIngredients;
    private ArrayList<Step> steps;
    private Recipe actualRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        categorySpinner = findViewById(R.id.category);
        nameEditText = findViewById(R.id.name);
        addSubcategoryButton = findViewById(R.id.subcategory);
        difficultySpinner = findViewById(R.id.difficulty);
        timeEditText = findViewById(R.id.time);
        generalDescEditText = findViewById(R.id.general_desc);
        mainIngredientsRecyclerView = findViewById(R.id.ingredients_items_rv);
        addMainIngredientButton = findViewById(R.id.main_ingredient);
        optionalIngredientsRecyclerView = findViewById(R.id.optional_ingredients_items_rv);
        addSubIngredientsIngredientButton = findViewById(R.id.subIngredients);
        addStepIngredientButton = findViewById(R.id.step);
        addRecipeIngredientButton = findViewById(R.id.add_recipe);
        stepsRecyclerView = findViewById(R.id.steps_rv);
        portionsEditText = findViewById(R.id.portion);

        mainIngredientsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        optionalIngredientsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        recipeDAO = new RecipeDAO(this);
        stepsToRecipeDAO = new StepsToRecipeDAO(this);
        ingredientsToRecipeDAO = new IngredientsToRecipeDAO(this);

        if (!recipeDAO.isRecipeInCreationExist()) {
            recipeDAO.startCreatingRecipe();
        }
        actualRecipe = recipeDAO.getStartedRecipe();
        initData();

        mainIngredients = ingredientsToRecipeDAO.getIngredientsList(actualRecipe, "main");
        subIngredients = ingredientsToRecipeDAO.getIngredientsList(actualRecipe, "sub");
        steps = stepsToRecipeDAO.getStepsList(actualRecipe);

        adapterMain = new RecipeIngredientsAdapter(this, new ArrayList<>());
        mainIngredientsRecyclerView.setAdapter(adapterMain);

        adapterSub = new RecipeIngredientsAdapter(this, new ArrayList<>());
        optionalIngredientsRecyclerView.setAdapter(adapterSub);

        stepsAdapter = new StepsAdapter(this, new ArrayList<>());
        stepsRecyclerView.setAdapter(stepsAdapter);

        adapterMain.setItems(mainIngredients);
        adapterMain.notifyDataSetChanged();

        adapterSub.setItems(subIngredients);
        adapterSub.notifyDataSetChanged();

        stepsAdapter.setItems(steps);
        stepsAdapter.notifyDataSetChanged();

        addMainIngredientButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, BrowseIngredientsActivity.class);
            intent.putExtra("previousActivity", "AddRecipeActivity");
            intent.putExtra("ingredientType", "main");
            startActivity(intent);
        });

        addSubIngredientsIngredientButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, BrowseIngredientsActivity.class);
            intent.putExtra("previousActivity", "AddRecipeActivity");
            intent.putExtra("ingredientType", "sub");
            startActivity(intent);
        });

        addStepIngredientButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddStepActivity.class);
            intent.putExtra("stepNr", String.valueOf(steps.size() + 1));
            startActivity(intent);
        });

        addRecipeIngredientButton.setOnClickListener(v -> {
            Recipe recipeToDb = createRecipe();
            addRecipeToDb(recipeToDb);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainIngredients = ingredientsToRecipeDAO.getIngredientsList(actualRecipe, "main");
        subIngredients = ingredientsToRecipeDAO.getIngredientsList(actualRecipe, "sub");
        steps = stepsToRecipeDAO.getStepsList(actualRecipe);

        adapterMain.setItems(mainIngredients);
        adapterMain.notifyDataSetChanged();
        adapterSub.setItems(subIngredients);
        adapterSub.notifyDataSetChanged();
        stepsAdapter.setItems(steps);
        stepsAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        createRecipe();
    }

    @Override
    protected void onStop() {
        super.onStop();
        createRecipe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        createRecipe();
    }

    private void initData() {
        if (actualRecipe.getCategory() != null) {
            for (int i = 0; i < categorySpinner.getAdapter().getCount(); i++) {
                if (actualRecipe.getCategory().equals(categorySpinner.getItemAtPosition(i).toString())) {
                    categorySpinner.setSelection(i);
                    break;
                } else {
                    categorySpinner.setSelection(0);
                }
            }
        }
        if (actualRecipe.getName() != null) {
            nameEditText.setText(actualRecipe.getName());
        }
        if (actualRecipe.getDifficulty() != null) {
            for (int i = 0; i < difficultySpinner.getAdapter().getCount(); i++) {
                if (actualRecipe.getDifficulty().equals(difficultySpinner.getItemAtPosition(i).toString())) {
                    difficultySpinner.setSelection(i);
                    break;
                } else {
                    difficultySpinner.setSelection(0);
                }
            }
        }
        if (actualRecipe.getTime() != null) {
            timeEditText.setText(actualRecipe.getTime());

        }
        if (actualRecipe.getGeneralDescription() != null) {
            generalDescEditText.setText(actualRecipe.getGeneralDescription());
        }
        if (actualRecipe.getPortion() != null && actualRecipe.getPortion() != 0.0) {
            portionsEditText.setText(actualRecipe.getPortion().toString());
        }
    }

    private Recipe createRecipe() {
        Recipe recipe = new Recipe();
        recipe.setId(actualRecipe.getId());
        recipe.setCategory(categorySpinner.getSelectedItem().toString());
        recipe.setName(nameEditText.getText().toString());
        recipe.setSubCategories(List.of("TODO"));
        recipe.setDifficulty(difficultySpinner.getSelectedItem().toString());
        recipe.setTime(timeEditText.getText().toString());
        List<FridgeIngredient> mainIngredientsList = new ArrayList<>();
        recipe.setMainIngredients(mainIngredientsList);
        List<FridgeIngredient> optionalIngredientsList = new ArrayList<>();
        recipe.setSubIngredients(optionalIngredientsList);
        recipe.setGeneralDescription(generalDescEditText.getText().toString());
        List<Step> steps = new ArrayList<>();
        recipe.setSteps(steps);
        recipe.setAuthor("TODOAUTHOR");
        recipe.setCreationDate(LocalDateTime.now().toString());
        try {
            recipe.setPortion(Double.parseDouble(portionsEditText.getText().toString()));
        } catch (Exception e) {
            recipe.setPortion(0.0);
        }
        recipeDAO.updateRecipe(recipe);
        Log.d("Recipe", recipe.toString());

        return recipe;
    }

    private void addRecipeToDb(Recipe recipe) {
        if (recipe.getName().equals("")) {
            Toast.makeText(this, R.string.fill_name, Toast.LENGTH_SHORT).show();
            return;
        }
        if (recipe.getDifficulty().equals("")) {
            Toast.makeText(this, R.string.fill_diff, Toast.LENGTH_SHORT).show();
            return;
        }
        if (recipe.getTime().equals("")) {
            Toast.makeText(this, R.string.fill_time, Toast.LENGTH_SHORT).show();
            return;
        }
        recipeDAO.globalAdd(recipe);
        finish();
    }
}