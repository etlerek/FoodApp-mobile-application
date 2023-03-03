package com.example.foodapp.databasehandlers;

import com.example.foodapp.entity.Table;

public class FridgeDatabaseHandler implements LocallyStored{

    public static final String TABLE_NAME = "fridge";

    public static final String PRODUCT_ID = "id_ingredient";
    public static final String NAME = "name";
    public static final String QUANTITY = "quantity";
    public static final String KCAL = "kcal";
    public static final String PROTEIN = "protein";
    public static final String SUGAR = "sugar";
    public static final String FAT = "fat";
    public static final String TYPE = "type";

    public String CREATE_FRIDGE_TABLE = "CREATE TABLE " + TABLE_NAME
            + " (" + PRODUCT_ID + " INTEGER PRIMARY KEY, "
            + NAME + " TEXT, " + QUANTITY + " NUMBER, " + TYPE + " TEXT, " + KCAL + " NUMBER, "
            + PROTEIN + " NUMBER, " + SUGAR + " NUMBER, " + FAT + " NUMBER)";

    @Override
    public Table getTable() {
        Table table = new Table();
        table.setName(TABLE_NAME);
        table.setTableOnCreate(CREATE_FRIDGE_TABLE);
        return table;
    }
}
