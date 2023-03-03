package com.example.foodapp.daohandlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.foodapp.databasehandlers.MainDatabaseHandler;
import com.example.foodapp.databasehandlers.RecipeDatabaseHandler;
import com.example.foodapp.databasehandlers.SavedRecipeDatabaseHandler;
import com.example.foodapp.entity.FridgeIngredient;
import com.example.foodapp.entity.Recipe;
import com.example.foodapp.entity.Step;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SavedRecipesDAO {

    private final MainDatabaseHandler mainDatabaseHandler;
    private final IngredientsToRecipeDAO ingredientsToRecipeDAO;
    private final StepsToRecipeDAO stepsToRecipeDAO;

    public SavedRecipesDAO(Context context){
        mainDatabaseHandler = MainDatabaseHandler.getInstance(context);
        stepsToRecipeDAO = new StepsToRecipeDAO(context);
        ingredientsToRecipeDAO = new IngredientsToRecipeDAO(context);
    }

    public void addSavedRecipe(String name){
        ContentValues values = new ContentValues();

        deleteSavedRecipe(name);
        values.put(SavedRecipeDatabaseHandler.RECIPE_NAME, name);

        mainDatabaseHandler.getWritableDatabase().insert(SavedRecipeDatabaseHandler.TABLE_NAME, null, values);
        mainDatabaseHandler.getWritableDatabase().close();
    }

    public void deleteSavedRecipe(String name){

        String[] args = {name};
        mainDatabaseHandler.getWritableDatabase().delete(SavedRecipeDatabaseHandler.TABLE_NAME, "recipe_name = ?", args);
        mainDatabaseHandler.getWritableDatabase().close();
    }

    public List<Recipe> getSavedRecipesList(){
        String query = "SELECT * FROM " + RecipeDatabaseHandler.TABLE_NAME
                + " NATURAL JOIN " + SavedRecipeDatabaseHandler.TABLE_NAME
                + " WHERE " + RecipeDatabaseHandler.NAME + " = " + SavedRecipeDatabaseHandler.RECIPE_NAME;
        Cursor cursor = mainDatabaseHandler.getWritableDatabase().rawQuery(query, null);
        List<Recipe> recipes = new ArrayList<>();

        if(cursor.moveToFirst()){
            do {
                Recipe recipe = new Recipe();
                recipe.setId(cursor.getInt(cursor.getColumnIndexOrThrow(RecipeDatabaseHandler.RECIPE_ID)));
                recipe.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(RecipeDatabaseHandler.CATEGORY)));
                recipe.setDifficulty(cursor.getString(cursor.getColumnIndexOrThrow(RecipeDatabaseHandler.DIFFICULTY)));
                recipe.setTime(cursor.getString(cursor.getColumnIndexOrThrow(RecipeDatabaseHandler.TIME)));
                recipe.setSubCategories(new ArrayList<>());
                recipe.setName(cursor.getString(cursor.getColumnIndexOrThrow(RecipeDatabaseHandler.NAME)));
                recipe.setGeneralDescription(cursor.getString(cursor.getColumnIndexOrThrow(RecipeDatabaseHandler.GENERAL_DESCRIPTION)));
                recipe.setTotalKcal(cursor.getInt(cursor.getColumnIndexOrThrow(RecipeDatabaseHandler.TOTAL_KCAL)));
                recipe.setAuthor(cursor.getString(cursor.getColumnIndexOrThrow(RecipeDatabaseHandler.AUTHOR)));
                recipe.setCreationDate(LocalDateTime.now().toString());
                recipe.setStatus(cursor.getString(cursor.getColumnIndexOrThrow(RecipeDatabaseHandler.STATUS)));
                recipe.setPortion(cursor.getDouble(cursor.getColumnIndexOrThrow(RecipeDatabaseHandler.PORTION)));

                List<FridgeIngredient> ingredientsMain = ingredientsToRecipeDAO.getIngredientsList(recipe, "main");
                List<FridgeIngredient> ingredientsSub = ingredientsToRecipeDAO.getIngredientsList(recipe, "sub");
                List<Step> steps = stepsToRecipeDAO.getStepsList(recipe);

                recipe.setMainIngredients(ingredientsMain);
                recipe.setSubIngredients(ingredientsSub);
                recipe.setSteps(steps);

                recipes.add(recipe);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return recipes;
    }
}
