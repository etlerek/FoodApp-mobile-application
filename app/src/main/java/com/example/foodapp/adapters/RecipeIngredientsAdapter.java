package com.example.foodapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.R;
import com.example.foodapp.activities.AddRecipeActivity;
import com.example.foodapp.activities.RecipeActivity;
import com.example.foodapp.daohandlers.FridgeDAO;
import com.example.foodapp.daohandlers.RecipeDAO;
import com.example.foodapp.daohandlers.ShoppingListDAO;
import com.example.foodapp.entity.FridgeIngredient;

import java.util.List;


public class RecipeIngredientsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final private Context context;
    private List<FridgeIngredient> ingredients;
    private final RecipeDAO recipeDAO;
    private final FridgeDAO fridgeDAO;

    public RecipeIngredientsAdapter(Context context, List<FridgeIngredient> ingredients) {
        super();
        this.context = context;
        this.ingredients = ingredients;
        this.recipeDAO = new RecipeDAO(context);
        this.fridgeDAO = new FridgeDAO(context);
    }

    public void setItems(List<FridgeIngredient> ingredients) {
        this.ingredients.clear();
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ingredient_recipe_item, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        IngredientViewHolder ingredientVH = (IngredientViewHolder) holder;
        FridgeIngredient ingredient = ingredients.get(position);

        ingredientVH.tvName.setText(ingredient.getName());
        if (ingredient.getQuantity() == 0.0) {
            ingredientVH.tvQuantity.setVisibility(View.GONE);
        } else {
            ingredientVH.tvQuantity.setText(" : " + ingredient.getQuantity().toString() + context.getString(R.string.g));
        }
        if (RecipeActivity.class.isAssignableFrom(context.getClass())) {
            ingredientVH.btnRemoveItem.setVisibility(View.GONE);
            List<FridgeIngredient> fridgeIngredients = fridgeDAO.getProductsList();
            for (FridgeIngredient fridgeIngredient : fridgeIngredients) {
                if (ingredient.getName().equals(fridgeIngredient.getName())) {
                    ingredientVH.tvName.setTextColor(Color.GREEN);
                    ingredientVH.tvQuantity.setTextColor(Color.GREEN);
                    ingredientVH.btnSimilarIngredients.setVisibility(View.INVISIBLE);
                    ingredientVH.btnSimilarIngredients.setEnabled(false);
                    ingredientVH.btnSimilarIngredients.setText("\u22EE");
                    break;
                }
                ingredientVH.tvName.setTextColor(Color.RED);
                ingredientVH.tvQuantity.setTextColor(Color.RED);
            }
        }
        if (AddRecipeActivity.class.isAssignableFrom(context.getClass())) {
            ingredientVH.btnSimilarIngredients.setVisibility(View.GONE);
        }
        ingredientVH.btnSimilarIngredients.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(context, ingredientVH.btnSimilarIngredients);
            popup.inflate(R.menu.recipe_ingredient);
            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.add_to_shopping_list:
                        ShoppingListDAO shoppingListDAO = new ShoppingListDAO(context);
                        shoppingListDAO.addProduct(ingredient.getId(), (ingredient.getQuantity()).intValue());
                        break;
                    case R.id.show_similar_button:
                        break;
                }
                return false;
            });
            popup.show();
        });

        ingredientVH.btnRemoveItem.setOnClickListener(v -> {
            recipeDAO.deleteIngredient(ingredient);
            ingredients.remove(ingredient);
            this.notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvQuantity;
        Button btnRemoveItem;
        Button btnSimilarIngredients;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.ingredient_name);
            tvQuantity = itemView.findViewById(R.id.ingredient_quantity);
            btnRemoveItem = itemView.findViewById(R.id.remove_ingredient_recipe_db_button);
            btnSimilarIngredients = itemView.findViewById(R.id.show_similar_button);

        }
    }
}

