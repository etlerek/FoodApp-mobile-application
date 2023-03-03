package com.example.foodapp.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.foodapp.R;
import com.example.foodapp.activities.AddRecipeActivity;
import com.example.foodapp.activities.FridgeActivity;
import com.example.foodapp.activities.ShoppingListActivity;
import com.example.foodapp.daohandlers.FridgeDAO;
import com.example.foodapp.daohandlers.RecipeDAO;
import com.example.foodapp.daohandlers.ShoppingListDAO;
import com.example.foodapp.entity.Ingredient;

public class DialogMenager {

    private FridgeDAO fridgeDAO;
    private RecipeDAO recipeDAO;
    private ShoppingListDAO shoppingListDAO;
    private Ingredient ingredient;
    private Context context;

    public DialogMenager(Context context, Ingredient ingredient){
        this.ingredient = ingredient;
        this.fridgeDAO = new FridgeDAO(context);
        this.recipeDAO = new RecipeDAO(context);
        this.shoppingListDAO = new ShoppingListDAO(context);
        this.context = context;
    }

    public Dialog showQuantityDialog(String mode, String previousActivity, String type){

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.dialog_quantity, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.FoodAppAlertDialog);
        builder.setView(view);
        builder.setMessage(R.string.quantity_not_required);
        builder.setTitle(context.getString(R.string.how_much_you_have) + " " + ingredient.getName() + "?");

        EditText editText = view.findViewById(R.id.quantity_id);
        Spinner spinner = view.findViewById(R.id.type_id);

        builder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                try{
                    if (previousActivity.equals(AddRecipeActivity.class.getSimpleName()) && type.equals("main")){
                        recipeDAO.addIngredientToRecipe(ingredient, Integer.parseInt(String.valueOf(editText.getText())), type);
                    } else if (previousActivity.equals(AddRecipeActivity.class.getSimpleName()) && type.equals("sub")){
                        recipeDAO.addIngredientToRecipe(ingredient, Integer.parseInt(String.valueOf(editText.getText())), type);
                    } else if (previousActivity.equals(FridgeActivity.class.getSimpleName())){
                        if(mode.equals("add")){
                            fridgeDAO.addProduct(ingredient, Integer.parseInt(String.valueOf(editText.getText())), spinner.getSelectedItem().toString());
                        } else if(mode.equals("edit")) {
                            fridgeDAO.editProduct(ingredient, Integer.parseInt(String.valueOf(editText.getText())), spinner.getSelectedItem().toString());
                        }
                    } else if (previousActivity.equals(ShoppingListActivity.class.getSimpleName())){
                        shoppingListDAO.addProduct(ingredient.getId(), Integer.parseInt(String.valueOf(editText.getText())));
                    }
                } catch (NumberFormatException e){
                    if(type != null && previousActivity.equals(AddRecipeActivity.class.getSimpleName())){
                        recipeDAO.addIngredientToRecipe(ingredient, null, type);
                    } else if (previousActivity.equals(FridgeActivity.class.getSimpleName())){
                        fridgeDAO.addProduct(ingredient, null, null);
                    } else if (previousActivity.equals(ShoppingListActivity.class.getSimpleName())) {
                        shoppingListDAO.addProduct(ingredient.getId(), null);
                    }
//                    if(type.equals("main") || type.equals("sub")){
//                        recipeDAO.addIngredientToRecipe(ingredient, null, type);
//                    } else {
//                        fridgeDAO.addProduct(ingredient, null, null);
//                    }
                }
                if (mode.equals("add")){
                    ((Activity) context).finish();
                }
            }
        })
        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        return builder.create();
    }
}
