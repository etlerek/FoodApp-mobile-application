package com.example.foodapp.daohandlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.foodapp.databasehandlers.IngredientDatabaseHandler;
import com.example.foodapp.databasehandlers.IngredientsToRecipeDatabaseHandler;
import com.example.foodapp.databasehandlers.MainDatabaseHandler;
import com.example.foodapp.databasehandlers.RecipeDatabaseHandler;
import com.example.foodapp.databasehandlers.StepsToRecipeDatabaseHandler;
import com.example.foodapp.entity.FridgeIngredient;
import com.example.foodapp.entity.Ingredient;
import com.example.foodapp.entity.Recipe;
import com.example.foodapp.entity.Step;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.Query;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RecipeDAO extends MainHandlerDAO{

    private final MainDatabaseHandler mainDatabaseHandler;
    private Context context;
    private IngredientsToRecipeDAO ingredientsToRecipeDAO;
    private StepsToRecipeDAO stepsToRecipeDAO;

    public RecipeDAO(Context context){
        this.context = context;
        databaseReference = db.getReference(Recipe.class.getSimpleName());
        mainDatabaseHandler = MainDatabaseHandler.getInstance(context);
        stepsToRecipeDAO = new StepsToRecipeDAO(context);
        ingredientsToRecipeDAO = new IngredientsToRecipeDAO(context);
    }

    public Query getAll(){
        return databaseReference.orderByKey();
    }

    public void startCreatingRecipe(){
        ContentValues values = new ContentValues();

        values.put(RecipeDatabaseHandler.STATUS, "IP");
        mainDatabaseHandler.getWritableDatabase().insert(RecipeDatabaseHandler.TABLE_NAME, null, values);
        mainDatabaseHandler.getWritableDatabase().close();
    }

    public Recipe getStartedRecipe(){
        String query = "SELECT * FROM " + RecipeDatabaseHandler.TABLE_NAME + " WHERE " + RecipeDatabaseHandler.STATUS + " = 'IP'";
        Cursor cursor = mainDatabaseHandler.getWritableDatabase().rawQuery(query, null);

        Recipe recipe = new Recipe();
        if (cursor.moveToFirst()) {
            recipe.setId(cursor.getInt(cursor.getColumnIndexOrThrow(RecipeDatabaseHandler.RECIPE_ID)));
            recipe.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(RecipeDatabaseHandler.CATEGORY)));
            recipe.setDifficulty(cursor.getString(cursor.getColumnIndexOrThrow(RecipeDatabaseHandler.DIFFICULTY)));
            recipe.setTime(cursor.getString(cursor.getColumnIndexOrThrow(RecipeDatabaseHandler.TIME)));
            recipe.setSubCategories(new ArrayList<>());
            recipe.setName(cursor.getString(cursor.getColumnIndexOrThrow(RecipeDatabaseHandler.NAME)));
            recipe.setGeneralDescription(cursor.getString(cursor.getColumnIndexOrThrow(RecipeDatabaseHandler.GENERAL_DESCRIPTION)));
            recipe.setMainIngredients(new ArrayList<>());
            recipe.setSubIngredients(new ArrayList<>());
            recipe.setSteps(new ArrayList<>());
            recipe.setTotalKcal(cursor.getInt(cursor.getColumnIndexOrThrow(RecipeDatabaseHandler.TOTAL_KCAL)));
            recipe.setAuthor(cursor.getString(cursor.getColumnIndexOrThrow(RecipeDatabaseHandler.AUTHOR)));
            recipe.setCreationDate(LocalDateTime.now().toString());
            recipe.setStatus(cursor.getString(cursor.getColumnIndexOrThrow(RecipeDatabaseHandler.STATUS)));
            recipe.setPortion(cursor.getDouble(cursor.getColumnIndexOrThrow(RecipeDatabaseHandler.PORTION)));
        }
        cursor.close();
        return recipe;
    }

    public List<Recipe> getRecipesList(){
        String query = "SELECT * FROM " + RecipeDatabaseHandler.TABLE_NAME + " WHERE status is null";
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

    public Recipe getRecipe(int id){
        String query = "SELECT * FROM " + RecipeDatabaseHandler.TABLE_NAME + " WHERE " + RecipeDatabaseHandler.RECIPE_ID + " = ?";
        String[] args = {String.valueOf(id)};
        Cursor cursor = mainDatabaseHandler.getWritableDatabase().rawQuery(query, args);

        Recipe recipe = new Recipe();

        if(cursor.moveToFirst()){
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
        }
        cursor.close();
        return recipe;
    }

    public void updateRecipe(Recipe recipe){
        ContentValues values = new ContentValues();

        values.put(RecipeDatabaseHandler.CATEGORY, recipe.getCategory());
        values.put(RecipeDatabaseHandler.DIFFICULTY, recipe.getDifficulty());
        values.put(RecipeDatabaseHandler.TIME, recipe.getTime());
        values.put(RecipeDatabaseHandler.NAME, recipe.getName());
        values.put(RecipeDatabaseHandler.GENERAL_DESCRIPTION, recipe.getGeneralDescription());
        values.put(RecipeDatabaseHandler.CREATION_DATE, LocalDateTime.now().toString());
        values.put(RecipeDatabaseHandler.PORTION, recipe.getPortion());
        //TODO: dodac wiecej jak trzeba

        String[] args = {};

        mainDatabaseHandler.getWritableDatabase().update(RecipeDatabaseHandler.TABLE_NAME, values, "status='IP'", args);
        mainDatabaseHandler.getWritableDatabase().close();
    }

    @Override
    public Task<Void> globalAdd(Object obj){

        Recipe recipeToSend = (Recipe)obj;
        StepsToRecipeDAO stepsToRecipeDAO = new StepsToRecipeDAO(context);
        IngredientsToRecipeDAO ingredientsToRecipeDAO = new IngredientsToRecipeDAO(context);

        List<Step> steps = stepsToRecipeDAO.getStepsList(recipeToSend);
        List<FridgeIngredient> ingredientsMain = ingredientsToRecipeDAO.getIngredientsList(recipeToSend, "main");
        List<FridgeIngredient> ingredientsSub = ingredientsToRecipeDAO.getIngredientsList(recipeToSend, "sub");

        recipeToSend.setMainIngredients(ingredientsMain);
        recipeToSend.setSubIngredients(ingredientsSub);
        recipeToSend.setSteps(steps);

        String sql = "UPDATE " + RecipeDatabaseHandler.TABLE_NAME +
                " SET " + RecipeDatabaseHandler.STATUS + " = ? WHERE " + RecipeDatabaseHandler.STATUS + " = ?";
        SQLiteStatement statement = mainDatabaseHandler.getWritableDatabase().compileStatement(sql);
        statement.bindString(1, "");
        statement.bindString(2, "IP");
        statement.executeUpdateDelete();

        mainDatabaseHandler.getWritableDatabase().close();
        return databaseReference.push().setValue(obj);
    }

    public boolean isRecipeInCreationExist() {
        String[] args = {"IP"};
        Cursor cursor = mainDatabaseHandler.getWritableDatabase().query(
                RecipeDatabaseHandler.TABLE_NAME,
                null,
                RecipeDatabaseHandler.STATUS + " = ?",
                args,
                null,
                null,
                null);

        return cursor.getCount() > 0;
    }

    public void addIngredientToRecipe(Ingredient ingredient, Integer quantity, String type){
        ContentValues values = new ContentValues();

        values.put(IngredientsToRecipeDatabaseHandler.INGREDIENT_ID, ingredient.getId());
        values.put(IngredientsToRecipeDatabaseHandler.RECIPE_ID, getStartedRecipe().getId());
        values.put(IngredientsToRecipeDatabaseHandler.QUANTITY, quantity);
        values.put(IngredientsToRecipeDatabaseHandler.TYPE, type);
        values.put(IngredientsToRecipeDatabaseHandler.STATUS, "IP");

        mainDatabaseHandler.getWritableDatabase().insert(IngredientsToRecipeDatabaseHandler.TABLE_NAME, null, values);
        mainDatabaseHandler.getWritableDatabase().close();
    }

    public void addStepToRecipe(Step step){
        ContentValues values = new ContentValues();

        values.put(StepsToRecipeDatabaseHandler.RECIPE_ID, getStartedRecipe().getId());
        values.put(StepsToRecipeDatabaseHandler.STEP_NUMBER, step.getStepNumber());
        values.put(StepsToRecipeDatabaseHandler.TITLE, step.getTitle());
        values.put(StepsToRecipeDatabaseHandler.CONTENT, step.getContent());
        values.put(StepsToRecipeDatabaseHandler.STATUS, "IP");

        mainDatabaseHandler.getWritableDatabase().insert(StepsToRecipeDatabaseHandler.TABLE_NAME, null, values);
        mainDatabaseHandler.getWritableDatabase().close();
    }

    public void deleteIngredient(Ingredient ingredient){

        Recipe recipe = getStartedRecipe();
        String[] args = {String.valueOf(ingredient.getId()), String.valueOf(recipe.getId())};
        mainDatabaseHandler.getWritableDatabase().delete(
                IngredientsToRecipeDatabaseHandler.TABLE_NAME,
                IngredientsToRecipeDatabaseHandler.INGREDIENT_ID + "=? AND " + IngredientsToRecipeDatabaseHandler.RECIPE_ID + "=?",
                args);
        mainDatabaseHandler.getWritableDatabase().close();
    }

    public void addRecipesList(List<Recipe> recipes){
        SQLiteDatabase database = mainDatabaseHandler.getWritableDatabase();
        ContentValues values = new ContentValues();
        database.beginTransaction();
        database.delete(RecipeDatabaseHandler.TABLE_NAME, "1=1", null);
        int counter = 0;
        try{
            for (Recipe recipe : recipes) {

                values.put(RecipeDatabaseHandler.RECIPE_ID, counter);
                values.put(RecipeDatabaseHandler.CATEGORY, recipe.getCategory());
                values.put(RecipeDatabaseHandler.DIFFICULTY, recipe.getDifficulty());
                values.put(RecipeDatabaseHandler.TIME, recipe.getTime());
                values.put(RecipeDatabaseHandler.SUB_CATEGORIES, recipe.getSubCategories().get(0));
                values.put(RecipeDatabaseHandler.NAME, recipe.getName());
                values.put(RecipeDatabaseHandler.GENERAL_DESCRIPTION, recipe.getGeneralDescription());
                values.put(RecipeDatabaseHandler.AUTHOR, recipe.getAuthor());
                values.put(RecipeDatabaseHandler.CREATION_DATE, LocalDateTime.now().toString());
                values.put(RecipeDatabaseHandler.PORTION, recipe.getPortion());
                if(recipe.getMainIngredients() != null){
                    ingredientsToRecipeDAO.addIngredientsToRecipeList(recipe.getMainIngredients(), counter, "main");
                }
                if(recipe.getSubIngredients() != null){
                    ingredientsToRecipeDAO.addIngredientsToRecipeList(recipe.getSubIngredients(), counter, "sub");
                }
                if(recipe.getSteps() != null){
                    stepsToRecipeDAO.addStepsToRecipeList(recipe.getSteps(), counter);
                }

                database.insert(RecipeDatabaseHandler.TABLE_NAME, null, values);
                counter++;
            }
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }
}
