package com.example.foodapp.daohandlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.foodapp.databasehandlers.IngredientDatabaseHandler;
import com.example.foodapp.databasehandlers.MainDatabaseHandler;
import com.example.foodapp.databasehandlers.ShoppingListDatabaseHandler;
import com.example.foodapp.entity.FridgeIngredient;

import java.util.ArrayList;

public class ShoppingListDAO {

    private final MainDatabaseHandler mainDatabaseHandler;

    public ShoppingListDAO(Context context){
        mainDatabaseHandler = MainDatabaseHandler.getInstance(context);
    }

    public void addProduct(int id, Integer quantity){
        ContentValues values = new ContentValues();

        deleteProduct(id);
        values.put(ShoppingListDatabaseHandler.INGREDIENT_ID, id);
        if(quantity != null){
            values.put(ShoppingListDatabaseHandler.QUANTITY, quantity);
        } else{
            values.put(ShoppingListDatabaseHandler.QUANTITY, (String) null);
        }

        mainDatabaseHandler.getWritableDatabase().insert(ShoppingListDatabaseHandler.TABLE_NAME, null, values);
        mainDatabaseHandler.getWritableDatabase().close();
    }

    public void deleteProduct(int id){

        String[] args = {String.valueOf(id)};
        mainDatabaseHandler.getWritableDatabase().delete(ShoppingListDatabaseHandler.TABLE_NAME, "id_ingredient = ?", args);
        mainDatabaseHandler.getWritableDatabase().close();
    }

    public ArrayList<FridgeIngredient> getShoppingList(){
        String query = "SELECT * FROM " + IngredientDatabaseHandler.TABLE_NAME
                + " NATURAL JOIN " + ShoppingListDatabaseHandler.TABLE_NAME
                + " WHERE " + IngredientDatabaseHandler.INGREDIENT_ID + " = " + ShoppingListDatabaseHandler.INGREDIENT_ID;
        Cursor cursor = mainDatabaseHandler.getWritableDatabase().rawQuery(query, null);
        ArrayList<FridgeIngredient> ingredientsList = new ArrayList<>();

        if(cursor.moveToFirst()){
            do {
                FridgeIngredient ingredient = new FridgeIngredient();
                ingredient.setId(cursor.getInt(cursor.getColumnIndexOrThrow(IngredientDatabaseHandler.INGREDIENT_ID)));
                ingredient.setName(cursor.getString(cursor.getColumnIndexOrThrow(IngredientDatabaseHandler.NAME)));
                ingredient.setKcal(cursor.getDouble(cursor.getColumnIndexOrThrow(IngredientDatabaseHandler.KCAL)));
                ingredient.setProtein(cursor.getDouble(cursor.getColumnIndexOrThrow(IngredientDatabaseHandler.PROTEIN)));
                ingredient.setFat(cursor.getDouble(cursor.getColumnIndexOrThrow(IngredientDatabaseHandler.FAT)));
                ingredient.setSugar(cursor.getDouble(cursor.getColumnIndexOrThrow(IngredientDatabaseHandler.SUGAR)));
                ingredient.setQuantity(cursor.getDouble(cursor.getColumnIndexOrThrow(ShoppingListDatabaseHandler.QUANTITY)));
                ingredient.setType("g");

                ingredientsList.add(ingredient);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return ingredientsList;
    }
}
