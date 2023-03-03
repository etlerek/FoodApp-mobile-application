package com.example.foodapp.databasehandlers;

import com.example.foodapp.entity.Table;

public class IngredientDatabaseHandler implements LocallyStored {

    public static final String TABLE_NAME = "ingredient";

    public static final String INGREDIENT_ID = "id_ingredient";
    public static final String NAME = "name";
    public static final String KCAL = "kcal";
    public static final String PROTEIN = "protein";
    public static final String SUGAR = "sugar";
    public static final String FAT = "fat";

    public String CREATE_INGREDIENT_TABLE = "CREATE TABLE " + TABLE_NAME
            + " (" + INGREDIENT_ID + " NUMBER, "
            + NAME + " TEXT, " + KCAL + " NUMBER, "
            + PROTEIN + " NUMBER, " + SUGAR + " NUMBER, " + FAT + " NUMBER)";

    @Override
    public Table getTable() {
        Table table = new Table();
        table.setName(TABLE_NAME);
        table.setTableOnCreate(CREATE_INGREDIENT_TABLE);
        return table;
    }
}
