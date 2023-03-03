package com.example.foodapp.databasehandlers;

import com.example.foodapp.entity.Table;

public class RecipeDatabaseHandler implements LocallyStored{

    public static final String TABLE_NAME = "recipe";

    public static final String RECIPE_ID = "id_recipe";
    public static final String CATEGORY = "category";
    public static final String DIFFICULTY = "difficulty";
    public static final String TIME = "time";
    public static final String SUB_CATEGORIES = "subCategories";
    public static final String NAME = "name";
    public static final String GENERAL_DESCRIPTION = "generalDescription";
    public static final String TOTAL_KCAL = "totalKcal";
    public static final String AUTHOR = "author";
    public static final String CREATION_DATE = "creationDate";
    public static final String PORTION = "portion";
    public static final String STATUS = "status";

    public String CREATE_RECIPE_TABLE = "CREATE TABLE " + TABLE_NAME
            + " (" + RECIPE_ID + " INTEGER PRIMARY KEY, "
            + CATEGORY + " TEXT, " + DIFFICULTY + " TEXT, " + TIME + " NUMBER, " + SUB_CATEGORIES + " TEXT, "
            + NAME + " TEXT, " + GENERAL_DESCRIPTION + " TEXT, "
            + TOTAL_KCAL + " NUMBER, " + AUTHOR + " TEXT, " + CREATION_DATE + " DATE, " + PORTION + " NUMBER, " + STATUS + " TEXT)";
    @Override
    public Table getTable() {
        Table table = new Table();
        table.setName(TABLE_NAME);
        table.setTableOnCreate(CREATE_RECIPE_TABLE);
        return table;
    }
}
