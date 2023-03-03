package com.example.foodapp.databasehandlers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.foodapp.entity.Table;

import java.util.ArrayList;

public class MainDatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "inz.foodapp";
    private static final int DATABASE_VERSION = 20;

    private static MainDatabaseHandler mainDatabaseHandler = null;

    private MainDatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized MainDatabaseHandler getInstance(Context context){
        if (mainDatabaseHandler == null) {
            mainDatabaseHandler = new MainDatabaseHandler(context);
        }
        return  mainDatabaseHandler;
    }

    private static final ArrayList<Table> dbTables = new ArrayList<>();

    @Override
    public void onCreate(SQLiteDatabase db) {
        dbTables.forEach(table -> db.execSQL(table.getTableOnCreate()));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dbTables.forEach(table -> db.execSQL("DROP TABLE IF EXISTS " + table.getName()));
        onCreate(db);
    }

    public static void addTable(Table table){
        dbTables.add(table);
    }
}
