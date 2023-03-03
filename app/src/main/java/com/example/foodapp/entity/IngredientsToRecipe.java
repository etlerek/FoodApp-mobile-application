package com.example.foodapp.entity;

import java.util.Objects;

public class IngredientsToRecipe {

    private int idRecord;
    private int idRecipe;
    private int idIngredient;
    private String type;
    private String quantity;

    public int getIdRecord() {
        return idRecord;
    }

    public void setIdRecord(int idRecord) {
        this.idRecord = idRecord;
    }

    public int getIdRecipe() {
        return idRecipe;
    }

    public void setIdRecipe(int idRecipe) {
        this.idRecipe = idRecipe;
    }

    public int getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(int idIngredient) {
        this.idIngredient = idIngredient;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "IngredientsToRecipe{" +
                "idRecord='" + idRecord + '\'' +
                ", idRecipe='" + idRecipe + '\'' +
                ", idIngredient='" + idIngredient + '\'' +
                ", type='" + type + '\'' +
                ", quantity='" + quantity + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IngredientsToRecipe that = (IngredientsToRecipe) o;
        return Objects.equals(idRecord, that.idRecord) && Objects.equals(idRecipe, that.idRecipe) && Objects.equals(idIngredient, that.idIngredient) && Objects.equals(type, that.type) && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRecord, idRecipe, idIngredient, type, quantity);
    }
}
