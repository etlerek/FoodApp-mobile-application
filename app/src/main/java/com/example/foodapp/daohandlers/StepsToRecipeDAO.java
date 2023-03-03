package com.example.foodapp.daohandlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.foodapp.databasehandlers.MainDatabaseHandler;
import com.example.foodapp.databasehandlers.StepsToRecipeDatabaseHandler;
import com.example.foodapp.entity.Recipe;
import com.example.foodapp.entity.Step;
import com.example.foodapp.entity.StepsToRecipe;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class StepsToRecipeDAO extends MainHandlerDAO{

    private final MainDatabaseHandler mainDatabaseHandler;

    public StepsToRecipeDAO(Context context) {
        databaseReference = db.getReference(StepsToRecipeDatabaseHandler.class.getSimpleName());
        mainDatabaseHandler = MainDatabaseHandler.getInstance(context);
    }

    public Query getAll(){
        return databaseReference.orderByKey();
    }

    @Override
    public Task<Void> globalAdd(Object obj){

        Recipe recipe = (Recipe)obj;
        String query = "SELECT * FROM " + StepsToRecipeDatabaseHandler.TABLE_NAME
                + " WHERE " + StepsToRecipeDatabaseHandler.RECIPE_ID + " = " + recipe.getId();
        Cursor cursor = mainDatabaseHandler.getWritableDatabase().rawQuery(query, null);

        ArrayList<StepsToRecipe> stepsToRecipesList = new ArrayList<>();
        if(cursor.moveToFirst()){
            do {
                StepsToRecipe stepsToRecipe = new StepsToRecipe();
                stepsToRecipe.setIdRecord(cursor.getInt(cursor.getColumnIndexOrThrow(StepsToRecipeDatabaseHandler.RECORD_ID)));
                stepsToRecipe.setIdRecipe(cursor.getInt(cursor.getColumnIndexOrThrow(StepsToRecipeDatabaseHandler.RECIPE_ID)));
                stepsToRecipe.setStepNumber(cursor.getInt(cursor.getColumnIndexOrThrow(StepsToRecipeDatabaseHandler.STEP_NUMBER)));
                stepsToRecipe.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(StepsToRecipeDatabaseHandler.TITLE)));
                stepsToRecipe.setContent(cursor.getString(cursor.getColumnIndexOrThrow(StepsToRecipeDatabaseHandler.CONTENT)));

                stepsToRecipesList.add(stepsToRecipe);
            }while (cursor.moveToNext());
        }
        cursor.close();

        for (StepsToRecipe stepsToRecipe: stepsToRecipesList) {
            databaseReference.push().setValue(stepsToRecipe);
        }

        String sql = "UPDATE " + StepsToRecipeDatabaseHandler.TABLE_NAME +
                " SET " + StepsToRecipeDatabaseHandler.STATUS + " = ? WHERE " + StepsToRecipeDatabaseHandler.STATUS + " = ?";
        SQLiteStatement statement = mainDatabaseHandler.getWritableDatabase().compileStatement(sql);
        statement.bindString(1, "");
        statement.bindString(2, "IP");
        statement.executeUpdateDelete();
        mainDatabaseHandler.getWritableDatabase().close();

        return null;
    }

    public ArrayList<Step> getStepsList(Recipe recipe){
        String query = "SELECT * FROM " + StepsToRecipeDatabaseHandler.TABLE_NAME
                + " WHERE " + StepsToRecipeDatabaseHandler.RECIPE_ID + " = " + recipe.getId();
        Cursor cursor = mainDatabaseHandler.getWritableDatabase().rawQuery(query, null);
        ArrayList<Step> steps = new ArrayList<>();

        if(cursor.moveToFirst()){
            do {
                Step step = new Step();
                step.setStepNumber(cursor.getInt(cursor.getColumnIndexOrThrow(StepsToRecipeDatabaseHandler.STEP_NUMBER)));
                step.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(StepsToRecipeDatabaseHandler.TITLE)));
                step.setContent(cursor.getString(cursor.getColumnIndexOrThrow(StepsToRecipeDatabaseHandler.CONTENT)));

                steps.add(step);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return steps;
    }

    public void eraseAllDatabaseData() {
        SQLiteDatabase database = mainDatabaseHandler.getWritableDatabase();
        database.beginTransaction();
        database.delete(StepsToRecipeDatabaseHandler.TABLE_NAME, "1=1", null);
        try{
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }

    public void addStepsToRecipeList(List<Step> stepsToRecipes, int recipeId) {
        SQLiteDatabase database = mainDatabaseHandler.getWritableDatabase();
        ContentValues values = new ContentValues();
        database.beginTransaction();
//        database.delete(StepsToRecipeDatabaseHandler.TABLE_NAME, "1=1", null);

        try {
            for (Step stepsToRecipe : stepsToRecipes) {
                values.put(StepsToRecipeDatabaseHandler.RECIPE_ID, recipeId);
                values.put(StepsToRecipeDatabaseHandler.STEP_NUMBER, stepsToRecipe.getStepNumber());
                values.put(StepsToRecipeDatabaseHandler.TITLE, stepsToRecipe.getTitle());
                values.put(StepsToRecipeDatabaseHandler.CONTENT, stepsToRecipe.getContent());

                database.insert(StepsToRecipeDatabaseHandler.TABLE_NAME, null, values);
            }
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }

}
