package com.example.foodapp.entity;

public class Table {
    private String name;
    private String tableOnCreate;
    private String tableOnDestroy;
    private String tableOnUpdate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTableOnCreate() {
        return tableOnCreate;
    }

    public void setTableOnCreate(String tableOnCreate) {
        this.tableOnCreate = tableOnCreate;
    }

    public String getTableOnDestroy() {
        return tableOnDestroy;
    }

    public void setTableOnDestroy(String tableOnDestroy) {
        this.tableOnDestroy = tableOnDestroy;
    }

    public String getTableOnUpdate() {
        return tableOnUpdate;
    }

    public void setTableOnUpdate(String tableOnUpdate) {
        this.tableOnUpdate = tableOnUpdate;
    }
}
