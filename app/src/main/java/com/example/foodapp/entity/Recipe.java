package com.example.foodapp.entity;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Recipe {
    private int id;
    private String category;
    private String difficulty;
    private String time;
    private List<String> subCategories;
    private String name;
    private String generalDescription;
    private List<FridgeIngredient> mainIngredients;
    private List<FridgeIngredient> subIngredients;
    private List<Step> steps;
    private Integer totalKcal;
    private String author;
    private String creationDate;
    private String status;
    private Double portion;

    public Recipe(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<String> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<String> subCategories) {
        this.subCategories = subCategories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGeneralDescription() {
        return generalDescription;
    }

    public void setGeneralDescription(String generalDescription) {
        this.generalDescription = generalDescription;
    }

    public List<FridgeIngredient> getMainIngredients() {
        return mainIngredients;
    }

    public void setMainIngredients(List<FridgeIngredient> mainIngredients) {
        this.mainIngredients = mainIngredients;
    }

    public List<FridgeIngredient> getSubIngredients() {
        return subIngredients;
    }

    public void setSubIngredients(List<FridgeIngredient> subIngredients) {
        this.subIngredients = subIngredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public Integer getTotalKcal() {
        return totalKcal;
    }

    public void setTotalKcal(Integer totalKcal) {
        this.totalKcal = totalKcal;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getStatus() {
        return status;
    }

    public Double getPortion() {
        return portion;
    }

    public void setPortion(Double portion) {
        this.portion = portion;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", time='" + time + '\'' +
                ", subCategories=" + subCategories +
                ", name='" + name + '\'' +
                ", generalDescription='" + generalDescription + '\'' +
                ", mainIngredients=" + mainIngredients +
                ", subIngredients=" + subIngredients +
                ", steps=" + steps +
                ", totalKcal=" + totalKcal +
                ", author='" + author + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return id == recipe.id && totalKcal == recipe.totalKcal && Objects.equals(category, recipe.category) && Objects.equals(difficulty, recipe.difficulty) && Objects.equals(time, recipe.time) && Objects.equals(subCategories, recipe.subCategories) && Objects.equals(name, recipe.name) && Objects.equals(generalDescription, recipe.generalDescription) && Objects.equals(mainIngredients, recipe.mainIngredients) && Objects.equals(subIngredients, recipe.subIngredients) && Objects.equals(steps, recipe.steps) && Objects.equals(author, recipe.author) && Objects.equals(creationDate, recipe.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category, difficulty, time, subCategories, name, generalDescription, mainIngredients, subIngredients, steps, totalKcal, author, creationDate);
    }
}
