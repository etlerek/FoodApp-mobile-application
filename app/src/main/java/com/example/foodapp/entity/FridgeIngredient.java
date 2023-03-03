package com.example.foodapp.entity;

public class FridgeIngredient extends Ingredient {
    Double quantity;
    String type;

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "FridgeIngredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", type='" + type + '\'' +
                ", kcal=" + kcal +
                ", protein=" + protein +
                ", sugar=" + sugar +
                ", fat=" + fat +
                '}';
    }
}
