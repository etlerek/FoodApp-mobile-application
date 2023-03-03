package com.example.foodapp.daohandlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.foodapp.databasehandlers.FridgeDatabaseHandler;
import com.example.foodapp.databasehandlers.MainDatabaseHandler;
import com.example.foodapp.entity.FridgeIngredient;
import com.example.foodapp.entity.Ingredient;

import java.util.ArrayList;

public class FridgeDAO {

    private final MainDatabaseHandler mainDatabaseHandler;

    public FridgeDAO(Context context) {
        mainDatabaseHandler = MainDatabaseHandler.getInstance(context);
    }

    public void addProduct(Ingredient ingredient, Integer quantity, String type) {
        ContentValues values = new ContentValues();

        values.put(FridgeDatabaseHandler.NAME, ingredient.getName());
        values.put(FridgeDatabaseHandler.QUANTITY, quantity);
        values.put(FridgeDatabaseHandler.KCAL, ingredient.getKcal());
        values.put(FridgeDatabaseHandler.PROTEIN, ingredient.getProtein());
        values.put(FridgeDatabaseHandler.SUGAR, ingredient.getSugar());
        values.put(FridgeDatabaseHandler.FAT, ingredient.getFat());
        if (quantity != null) {
            values.put(FridgeDatabaseHandler.TYPE, type);
        } else {
            values.put(FridgeDatabaseHandler.TYPE, (String) null);
        }

        mainDatabaseHandler.getWritableDatabase().insert(FridgeDatabaseHandler.TABLE_NAME, null, values);
        mainDatabaseHandler.getWritableDatabase().close();
    }

    public void deleteProduct(Ingredient ingredient) {

        String[] args = {ingredient.getName()};
        mainDatabaseHandler.getWritableDatabase().delete(FridgeDatabaseHandler.TABLE_NAME, "name = ?", args);
        mainDatabaseHandler.getWritableDatabase().close();
    }

    public void editProduct(Ingredient ingredient, Integer quantity, String type) {

        ContentValues values = new ContentValues();
        values.put(FridgeDatabaseHandler.NAME, ingredient.getName());
        values.put(FridgeDatabaseHandler.QUANTITY, quantity);
        values.put(FridgeDatabaseHandler.KCAL, ingredient.getKcal());
        values.put(FridgeDatabaseHandler.PROTEIN, ingredient.getProtein());
        values.put(FridgeDatabaseHandler.SUGAR, ingredient.getSugar());
        values.put(FridgeDatabaseHandler.FAT, ingredient.getFat());
        if (quantity != null) {
            values.put(FridgeDatabaseHandler.TYPE, type);
        } else {
            values.put(FridgeDatabaseHandler.TYPE, (String) null);
        }

        String[] args = {ingredient.getName()};

        mainDatabaseHandler.getWritableDatabase().update(FridgeDatabaseHandler.TABLE_NAME, values, "name=?", args);
        mainDatabaseHandler.getWritableDatabase().close();
    }

    public ArrayList<FridgeIngredient> getProductsList() {
        String query = "SELECT * FROM " + FridgeDatabaseHandler.TABLE_NAME;
        Cursor cursor = mainDatabaseHandler.getWritableDatabase().rawQuery(query, null);
        ArrayList<FridgeIngredient> ingredientsList = new ArrayList<>();

        if (cursor.moveToFirst()) {
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
            } while (cursor.moveToNext());
        }
        cursor.close();
        return ingredientsList;
    }
}
