package com.example.foodapp.databasehandlers;

import com.example.foodapp.entity.Table;

public class StepsToRecipeDatabaseHandler implements LocallyStored{

    public static final String TABLE_NAME = "steps_to_recipe";

    public static final String RECORD_ID = "id_record";
    public static final String RECIPE_ID = "id_recipe";
    public static final String STEP_NUMBER = "step_number";
    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String STATUS = "status";

    public String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
            + " (" + RECORD_ID + " INTEGER PRIMARY KEY, " + RECIPE_ID + " NUMBER, " + STEP_NUMBER + " NUMBER, " + TITLE + " TEXT, " + CONTENT + " TEXT, " + STATUS + " STATUS)";

    @Override
    public Table getTable() {
        Table table = new Table();
        table.setName(TABLE_NAME);
        table.setTableOnCreate(CREATE_TABLE);
        return table;
    }
}
