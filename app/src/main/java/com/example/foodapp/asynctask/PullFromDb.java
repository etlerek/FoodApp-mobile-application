package com.example.foodapp.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.example.foodapp.daohandlers.IngredientDAO;
import com.example.foodapp.daohandlers.IngredientsToRecipeDAO;
import com.example.foodapp.daohandlers.RecipeDAO;
import com.example.foodapp.daohandlers.StepsToRecipeDAO;
import com.example.foodapp.entity.Ingredient;
import com.example.foodapp.entity.Recipe;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PullFromDb extends AsyncTask<Void, Void, Void> {

    IngredientDAO ingredientDAO;
    IngredientsToRecipeDAO ingredientsToRecipeDAO;
    StepsToRecipeDAO stepsToRecipeDAO;
    RecipeDAO recipeDAO;

    public PullFromDb(Context context) {
        ingredientDAO = new IngredientDAO(context);
        ingredientsToRecipeDAO = new IngredientsToRecipeDAO(context);
        stepsToRecipeDAO = new StepsToRecipeDAO(context);
        recipeDAO = new RecipeDAO(context);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        ingredientDAO.getAll().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Ingredient> ingredients = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Ingredient ingredient = data.getValue(Ingredient.class);
                    ingredients.add(ingredient);
                }

                ingredientDAO.addProductsList(ingredients);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        recipeDAO.getAll().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Recipe> recipes = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Recipe recipe = data.getValue(Recipe.class);
                    recipes.add(recipe);
                }
                recipes.forEach(System.out::println);
                ingredientsToRecipeDAO.eraseAllDatabaseData();
                stepsToRecipeDAO.eraseAllDatabaseData();
                recipeDAO.addRecipesList(recipes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return null;
    }

}
