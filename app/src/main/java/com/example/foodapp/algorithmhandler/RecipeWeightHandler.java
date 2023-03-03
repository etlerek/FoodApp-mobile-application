package com.example.foodapp.algorithmhandler;

import com.example.foodapp.entity.Recipe;

import java.util.Comparator;

public class RecipeWeightHandler implements Comparator<RecipeWeightHandler> {
    private Recipe recipe;
    private int mainIngredientsNumber;
    private int subIngredientsNumber;
    private double portionNumber;
    private int mainIngredientsMissing;
    private int subIngredientsMissing;
    private double portionMissing;
    private double recipeWeight;

    public void incrementMainIngredientsWeight(){
        mainIngredientsNumber++;
    }

    public void incrementSubIngredientsWeight(){
        subIngredientsNumber++;
    }

    public void calculateWeight(){
        calculateMainIngredientsMissing();
        calculateSubIngredientsMissing();
        calculatePortionMissing();
        calculateRecipeWeight();
    }

    private void calculateMainIngredientsMissing(){
        mainIngredientsMissing = mainIngredientsNumber - recipe.getMainIngredients().size();
//        mainIngredientsWeight = - mainIngredientsNumber + recipe.getMainIngredients().size();

    }

    private void calculateSubIngredientsMissing(){
        subIngredientsMissing = subIngredientsNumber - recipe.getSubIngredients().size();
//        subIngredientsWeight = - subIngredientsNumber + recipe.getSubIngredients().size();

    }

    private void calculatePortionMissing(){
        portionMissing = portionNumber -  recipe.getPortion();
//        portionWeight = - portionNumber +  recipe.getPortion();
    }

    private void calculateRecipeWeight(){
        recipeWeight = (mainIngredientsNumber * 3
                         + mainIngredientsMissing
                         + subIngredientsNumber
                         + subIngredientsMissing
                         + portionNumber
                         + portionMissing) /
                (recipe.getMainIngredients().size() + recipe.getSubIngredients().size() + recipe.getPortion());
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public int getMainIngredientsNumber() {
        return mainIngredientsNumber;
    }

    public void setMainIngredientsNumber(int mainIngredientsNumber) {
        this.mainIngredientsNumber = mainIngredientsNumber;
    }

    public int getSubIngredientsNumber() {
        return subIngredientsNumber;
    }

    public void setSubIngredientsNumber(int subIngredientsNumber) {
        this.subIngredientsNumber = subIngredientsNumber;
    }

    public double getPortionNumber() {
        return portionNumber;
    }

    public void setPortionNumber(double portionNumber) {
        this.portionNumber = portionNumber;
    }

    public int getMainIngredientsMissing() {
        return mainIngredientsMissing;
    }

    public int getSubIngredientsMissing() {
        return subIngredientsMissing;
    }

    public double getPortionMissing() {
        return portionMissing;
    }

    public double getRecipeWeight() {
        return recipeWeight;
    }

    @Override
    public String toString() {
        return "RecipeWeightHandler{" +
                //"recipe=" + recipe +
                ", mainIngredientsNumber=" + mainIngredientsNumber +
                ", subIngredientsNumber=" + subIngredientsNumber +
                ", portionNumber=" + portionNumber +
                ", mainIngredientsWeight=" + mainIngredientsMissing +
                ", subIngredientsWeight=" + subIngredientsMissing +
                ", portionWeight=" + portionMissing +
                ", percentageWeight=" + recipeWeight +
                '}';
    }

    @Override
    public int compare(RecipeWeightHandler o1, RecipeWeightHandler o2) {
        if(o1.getMainIngredientsMissing() != o2.getMainIngredientsMissing()){
            return Integer.compare(o2.getMainIngredientsMissing(), o1.getMainIngredientsMissing());
        }
        return Double.compare(o2.getRecipeWeight(), o1.getRecipeWeight());
    }
}
