package com.example.foodapp.databasehandlers;

import com.example.foodapp.entity.Table;

public class ShoppingListDatabaseHandler implements LocallyStored {

    public static final String TABLE_NAME = "shopping_list";

    public static final String ITEM_ID = "item_id";
    public static final String INGREDIENT_ID = "id_ingredient";
    public static final String QUANTITY = "quantity";

    public String CREATE_SHOPPING_LIST_TABLE = "CREATE TABLE " + TABLE_NAME
            + " (" + ITEM_ID + " INTEGER PRIMARY KEY, " + INGREDIENT_ID + " NUMBER, " + QUANTITY + " NUMBER)";

    @Override
    public Table getTable() {
        Table table = new Table();
        table.setName(TABLE_NAME);
        table.setTableOnCreate(CREATE_SHOPPING_LIST_TABLE);
        return table;
    }
}
