package com.example.foodapp.databasehandlers;

import com.example.foodapp.entity.Table;

public class SavedRecipeDatabaseHandler implements LocallyStored{

    public static final String TABLE_NAME = "saved_recipe";

    public static final String RECORD_ID = "id_record";
    public static final String RECIPE_NAME = "recipe_name";

    public String CREATE_RECIPE_TABLE = "CREATE TABLE " + TABLE_NAME
            + " (" + RECORD_ID + " INTEGER PRIMARY KEY, "
            + RECIPE_NAME + " TEXT)";
    @Override
    public Table getTable() {
        Table table = new Table();
        table.setName(TABLE_NAME);
        table.setTableOnCreate(CREATE_RECIPE_TABLE);
        return table;
    }
}
