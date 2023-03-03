package com.example.foodapp.databasehandlers;

import com.example.foodapp.entity.Table;

public class IngredientsToRecipeDatabaseHandler implements LocallyStored{

    public static final String TABLE_NAME = "ingredients_to_recipe";

    public static final String RECORD_ID = "id_record";
    public static final String RECIPE_ID = "id_recipe";
    public static final String INGREDIENT_ID = "id_ingredient";
    public static final String TYPE = "type";
    public static final String QUANTITY = "quantity";
    public static final String STATUS = "status";

    public String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
            + " (" + RECORD_ID + " INTEGER PRIMARY KEY, " +  RECIPE_ID + " NUMBER, " + INGREDIENT_ID + " NUMBER, "
            + TYPE + " TEXT, " + QUANTITY + " NUMBER, " + STATUS + " TEXT)";

    @Override
    public Table getTable() {
        Table table = new Table();
        table.setName(TABLE_NAME);
        table.setTableOnCreate(CREATE_TABLE);
        return table;
    }
}
