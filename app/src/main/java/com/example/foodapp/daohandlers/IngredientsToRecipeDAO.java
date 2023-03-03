package com.example.foodapp.daohandlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.foodapp.databasehandlers.FridgeDatabaseHandler;
import com.example.foodapp.databasehandlers.IngredientDatabaseHandler;
import com.example.foodapp.databasehandlers.IngredientsToRecipeDatabaseHandler;
import com.example.foodapp.databasehandlers.MainDatabaseHandler;
import com.example.foodapp.entity.FridgeIngredient;
import com.example.foodapp.entity.IngredientsToRecipe;
import com.example.foodapp.entity.Recipe;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class IngredientsToRecipeDAO extends MainHandlerDAO{

    private final MainDatabaseHandler mainDatabaseHandler;

    public IngredientsToRecipeDAO(Context context) {
        databaseReference = db.getReference(IngredientsToRecipe.class.getSimpleName());
        mainDatabaseHandler = MainDatabaseHandler.getInstance(context);
    }

    public Query getAll(){
        return databaseReference.orderByKey();
    }

    @Override
    public Task<Void> globalAdd(Object obj){

        Recipe recipe = (Recipe)obj;
        String query = "SELECT * FROM " + IngredientsToRecipeDatabaseHandler.TABLE_NAME
                + " WHERE " + IngredientsToRecipeDatabaseHandler.RECIPE_ID + " = " + recipe.getId();
        Cursor cursor = mainDatabaseHandler.getWritableDatabase().rawQuery(query, null);

        ArrayList<IngredientsToRecipe> ingredientsToRecipesList = new ArrayList<>();
        if(cursor.moveToFirst()){
            do {
                IngredientsToRecipe ingredientsToRecipe = new IngredientsToRecipe();
                //ingredientsToRecipe.setIdRecord(cursor.getInt(cursor.getColumnIndexOrThrow(IngredientsToRecipeDatabaseHandler.RECORD_ID)));
                ingredientsToRecipe.setIdRecipe(cursor.getInt(cursor.getColumnIndexOrThrow(IngredientsToRecipeDatabaseHandler.RECIPE_ID)));
                ingredientsToRecipe.setIdIngredient(cursor.getInt(cursor.getColumnIndexOrThrow(IngredientsToRecipeDatabaseHandler.INGREDIENT_ID)));
                ingredientsToRecipe.setType(cursor.getString(cursor.getColumnIndexOrThrow(IngredientsToRecipeDatabaseHandler.TYPE)));
                ingredientsToRecipe.setQuantity(cursor.getString(cursor.getColumnIndexOrThrow(IngredientsToRecipeDatabaseHandler.QUANTITY)));

                ingredientsToRecipesList.add(ingredientsToRecipe);
            }while (cursor.moveToNext());
        }
        cursor.close();

        for (IngredientsToRecipe ingredientToRecipe: ingredientsToRecipesList) {
            databaseReference.push().setValue(ingredientToRecipe);
        }

        String sql = "UPDATE " + IngredientsToRecipeDatabaseHandler.TABLE_NAME +
                " SET " + IngredientsToRecipeDatabaseHandler.STATUS + " = ? WHERE " + IngredientsToRecipeDatabaseHandler.STATUS + " = ?";
        SQLiteStatement statement = mainDatabaseHandler.getWritableDatabase().compileStatement(sql);
        statement.bindString(1, "");
        statement.bindString(2, "IP");
        statement.executeUpdateDelete();
        mainDatabaseHandler.getWritableDatabase().close();

        return null;
    }

    public ArrayList<FridgeIngredient> getIngredientsList(Recipe recipe, String type){
        String query = "SELECT * FROM " + IngredientDatabaseHandler.TABLE_NAME
                + " NATURAL JOIN " + IngredientsToRecipeDatabaseHandler.TABLE_NAME
                + " WHERE " + IngredientsToRecipeDatabaseHandler.RECIPE_ID + " = " + recipe.getId()
                + " AND " + IngredientDatabaseHandler.INGREDIENT_ID + " = " + IngredientsToRecipeDatabaseHandler.INGREDIENT_ID
                + " AND " + IngredientsToRecipeDatabaseHandler.TYPE + " = '" + type + "'";
        Log.d(type + "Ingredient querry", query);
        Cursor cursor = mainDatabaseHandler.getWritableDatabase().rawQuery(query, null);
        ArrayList<FridgeIngredient> ingredientsList = new ArrayList<>();
        Log.d(type + "List of items ", ingredientsList.toString());

        if(cursor.moveToFirst()){
            do {
                FridgeIngredient ingredient = new FridgeIngredient();
                ingredient.setId(cursor.getInt(cursor.getColumnIndexOrThrow(FridgeDatabaseHandler.PRODUCT_ID)));
                ingredient.setName(cursor.getString(cursor.getColumnIndexOrThrow(FridgeDatabaseHandler.NAME)));
                ingredient.setKcal(cursor.getDouble(cursor.getColumnIndexOrThrow(FridgeDatabaseHandler.KCAL)));
                ingredient.setProtein(cursor.getDouble(cursor.getColumnIndexOrThrow(FridgeDatabaseHandler.PROTEIN)));
                ingredient.setFat(cursor.getDouble(cursor.getColumnIndexOrThrow(FridgeDatabaseHandler.FAT)));
                ingredient.setSugar(cursor.getDouble(cursor.getColumnIndexOrThrow(FridgeDatabaseHandler.SUGAR)));
                ingredient.setQuantity(cursor.getDouble(cursor.getColumnIndexOrThrow(FridgeDatabaseHandler.QUANTITY)));
                ingredient.setType(cursor.getString(cursor.getColumnIndexOrThrow(FridgeDatabaseHandler.TYPE)));

                ingredientsList.add(ingredient);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return ingredientsList;
    }

    public void eraseAllDatabaseData() {
        SQLiteDatabase database = mainDatabaseHandler.getWritableDatabase();
        database.beginTransaction();
        database.delete(IngredientsToRecipeDatabaseHandler.TABLE_NAME, "1=1", null);
        try{
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }

    public void addIngredientsToRecipeList(List<FridgeIngredient> ingredientsToRecipes,int recipeId, String type) {
        SQLiteDatabase database = mainDatabaseHandler.getWritableDatabase();
        ContentValues values = new ContentValues();
        database.beginTransaction();
//        String args[] = {type, String.valueOf(recipeId)};
//        database.delete(IngredientsToRecipeDatabaseHandler.TABLE_NAME,
//                IngredientsToRecipeDatabaseHandler.TYPE + " = ? AND " + IngredientsToRecipeDatabaseHandler.RECIPE_ID + " = ?", args);

        try {
            for (FridgeIngredient ingredientsToRecipe : ingredientsToRecipes) {
                values.put(IngredientsToRecipeDatabaseHandler.RECIPE_ID, recipeId);
                values.put(IngredientsToRecipeDatabaseHandler.INGREDIENT_ID, ingredientsToRecipe.getId());
                values.put(IngredientsToRecipeDatabaseHandler.TYPE, ingredientsToRecipe.getType());
                values.put(IngredientsToRecipeDatabaseHandler.QUANTITY, ingredientsToRecipe.getQuantity());

                database.insert(IngredientsToRecipeDatabaseHandler.TABLE_NAME, null, values);
            }
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }

}
