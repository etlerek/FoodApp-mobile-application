package com.example.foodapp.algorithmhandler;

import android.content.Context;

import com.example.foodapp.R;
import com.example.foodapp.daohandlers.FridgeDAO;
import com.example.foodapp.entity.FridgeIngredient;
import com.example.foodapp.entity.Recipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MatchingAlgorithm {

    private final Context context;
    private final List<FridgeIngredient> fridgeIngredients;


    public MatchingAlgorithm(Context context) {
        this.context = context;
        this.fridgeIngredients = new FridgeDAO(context).getProductsList();
    }

    public void showFridgeIngredients() {
        fridgeIngredients.forEach(System.out::println);
    }

    public List<RecipeWeightHandler> matchRecipeToFridgeIngredients(List<Recipe> recipes) {

        List<RecipeWeightHandler> recipeWeightHandlerList = recipeWeightListFactory(recipes);
        for (RecipeWeightHandler recipeWeightHandler : recipeWeightHandlerList) {
            recipeWeightHandler.setPortionNumber(recipeWeightHandler.getRecipe().getPortion());
            calculateMainIngredientWeight(recipeWeightHandler);
            calculateSubIngredientWeight(recipeWeightHandler);
            recipeWeightHandler.calculateWeight();
        }

        Collections.sort(recipeWeightHandlerList, new RecipeWeightHandler());

        return recipeWeightHandlerList;
    }

    private void calculateMainIngredientWeight(RecipeWeightHandler recipeWeightHandler) {
        for (FridgeIngredient recipeIngredient : recipeWeightHandler.getRecipe().getMainIngredients()) {
            for (FridgeIngredient fridgeIngredient : fridgeIngredients) {
                if (recipeIngredient.getName().equals(fridgeIngredient.getName())) {
                    recipeWeightHandler.incrementMainIngredientsWeight();
                    calculatePortionWeight(recipeWeightHandler, recipeIngredient, fridgeIngredient);
                }
            }
        }
    }

    private void calculateSubIngredientWeight(RecipeWeightHandler recipeWeightHandler) {
        for (FridgeIngredient recipeIngredient : recipeWeightHandler.getRecipe().getSubIngredients()) {
            for (FridgeIngredient fridgeIngredient : fridgeIngredients) {
                if (recipeIngredient.getName().equals(fridgeIngredient.getName())) {
                    recipeWeightHandler.incrementSubIngredientsWeight();
                }
            }
        }
    }

    private void calculatePortionWeight(RecipeWeightHandler recipeWeightHandler, FridgeIngredient recipeIngredient, FridgeIngredient fridgeIngredient) {
        if (recipeIngredient.getQuantity() != null && fridgeIngredient.getQuantity()
                != null && fridgeIngredient.getQuantity() != 0 && recipeIngredient.getQuantity() > fridgeIngredient.getQuantity()) {
            double portion = recipeWeightHandler.getRecipe().getPortion()
                    * (fridgeIngredient.getQuantity() / recipeIngredient.getQuantity());
            if (recipeWeightHandler.getPortionNumber() > portion) {
                recipeWeightHandler.setPortionNumber(portion);
            }
        }
    }



    private List<RecipeWeightHandler> recipeWeightListFactory(List<Recipe> recipes) {

        List<RecipeWeightHandler> recipesWithWeight = new ArrayList<>();
        for (Recipe recipe : recipes) {
            RecipeWeightHandler recipeWeightHandler = new RecipeWeightHandler();
            recipeWeightHandler.setRecipe(recipe);
            recipeWeightHandler.setMainIngredientsNumber(0);
            recipeWeightHandler.setSubIngredientsNumber(0);
            recipeWeightHandler.setPortionNumber(recipe.getPortion());
            recipesWithWeight.add(recipeWeightHandler);
        }

        return recipesWithWeight;
    }

    public List<Recipe> filterRecipes(List<Recipe> recipesTmp, String category, String difficulty, String timeArg) {

        int time;
        try {
            time = Integer.parseInt(timeArg);
        } catch (NumberFormatException e) {
            time = 0;
        }

        int finalTime = time;
        return recipesTmp.stream().filter(r -> {
            if (category.equals(context.getString(R.string.choose_category))) {
                return true;
            }
            return r.getCategory().equals(category);
        }).filter(r -> {
            if (difficulty.equals(context.getString(R.string.choose_diff))) {
                return true;
            }
            return r.getDifficulty().equals(difficulty);
        }).filter(r -> {
            if (finalTime <= 0) {
                return true;
            }
            return Double.parseDouble(r.getTime()) <= finalTime;
        }).collect(Collectors.toList());

    }
}
