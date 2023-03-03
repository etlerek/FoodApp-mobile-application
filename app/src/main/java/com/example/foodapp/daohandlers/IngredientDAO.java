package com.example.foodapp.daohandlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.foodapp.databasehandlers.IngredientDatabaseHandler;
import com.example.foodapp.databasehandlers.MainDatabaseHandler;
import com.example.foodapp.entity.Ingredient;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class IngredientDAO extends MainHandlerDAO {

    private final MainDatabaseHandler mainDatabaseHandler;

    public IngredientDAO(Context context) {
        databaseReference = db.getReference(Ingredient.class.getSimpleName());
        mainDatabaseHandler = MainDatabaseHandler.getInstance(context);
    }

    @Override
    public Task<Void> globalAdd(Object obj) {
        databaseReference.child(((Ingredient) obj).getName()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Log.d("Recipe Global Add", obj + " istnieje w bazie danych");
                } else {
                    databaseReference.child(((Ingredient) obj).getName()).setValue(obj);
                    Log.d("Recipe Global Add", obj + " dodany");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println("Błąd podczas pobierania danych: " + error.getMessage());
            }
        });
        return null;
    }

    public Query getAll() {
        return databaseReference.orderByKey();
    }

    public void addProduct(Ingredient ingredient) {
        SQLiteDatabase database = mainDatabaseHandler.getWritableDatabase();
        String[] args = {ingredient.getName()};
        database.delete(IngredientDatabaseHandler.TABLE_NAME, "NAME = ?", args);
        ContentValues values = new ContentValues();

        values.put(IngredientDatabaseHandler.INGREDIENT_ID, ingredient.getId());
        values.put(IngredientDatabaseHandler.NAME, ingredient.getName());
        values.put(IngredientDatabaseHandler.KCAL, ingredient.getKcal());
        values.put(IngredientDatabaseHandler.PROTEIN, ingredient.getProtein());
        values.put(IngredientDatabaseHandler.SUGAR, ingredient.getSugar());
        values.put(IngredientDatabaseHandler.FAT, ingredient.getFat());

        database.insert(IngredientDatabaseHandler.TABLE_NAME, null, values);
        database.close();
    }

    public void addProductsList(List<Ingredient> ingredients) {
        SQLiteDatabase database = mainDatabaseHandler.getWritableDatabase();
        ContentValues values = new ContentValues();
        database.beginTransaction();
        database.delete(IngredientDatabaseHandler.TABLE_NAME, "1=1", null);

        try {
            for (Ingredient ingredient : ingredients) {

                values.put(IngredientDatabaseHandler.INGREDIENT_ID, ingredient.getId());
                values.put(IngredientDatabaseHandler.NAME, ingredient.getName());
                values.put(IngredientDatabaseHandler.KCAL, ingredient.getKcal());
                values.put(IngredientDatabaseHandler.PROTEIN, ingredient.getProtein());
                values.put(IngredientDatabaseHandler.SUGAR, ingredient.getSugar());
                values.put(IngredientDatabaseHandler.FAT, ingredient.getFat());
                database.insert(IngredientDatabaseHandler.TABLE_NAME, null, values);
            }
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }

    public ArrayList<Ingredient> getProductsList() {

        String query = "SELECT * FROM " + IngredientDatabaseHandler.TABLE_NAME;
        Cursor cursor = mainDatabaseHandler.getWritableDatabase().rawQuery(query, null);
        ArrayList<Ingredient> ingredientsList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Ingredient ingredient = new Ingredient();
                ingredient.setId(cursor.getInt(cursor.getColumnIndexOrThrow(IngredientDatabaseHandler.INGREDIENT_ID)));
                ingredient.setName(cursor.getString(cursor.getColumnIndexOrThrow(IngredientDatabaseHandler.NAME)));
                ingredient.setKcal(cursor.getDouble(cursor.getColumnIndexOrThrow(IngredientDatabaseHandler.KCAL)));
                ingredient.setProtein(cursor.getDouble(cursor.getColumnIndexOrThrow(IngredientDatabaseHandler.PROTEIN)));
                ingredient.setFat(cursor.getDouble(cursor.getColumnIndexOrThrow(IngredientDatabaseHandler.FAT)));
                ingredient.setSugar(cursor.getDouble(cursor.getColumnIndexOrThrow(IngredientDatabaseHandler.SUGAR)));

                ingredientsList.add(ingredient);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return ingredientsList;
    }
}
