package com.example.foodapp.adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.R;
import com.example.foodapp.activities.FridgeActivity;
import com.example.foodapp.activities.ShoppingListActivity;
import com.example.foodapp.daohandlers.FridgeDAO;
import com.example.foodapp.daohandlers.ShoppingListDAO;
import com.example.foodapp.entity.FridgeIngredient;
import com.example.foodapp.ui.DialogMenager;

import java.util.List;


public class FridgeIngredientsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final private Context context;
    private final FridgeDAO fridgeDAO;
    private final ShoppingListDAO shoppingListDAO;
    private final String state;
    private List<FridgeIngredient> ingredients;

    public FridgeIngredientsAdapter(Context context, List<FridgeIngredient> ingredients, String state) {
        super();
        this.context = context;
        this.ingredients = ingredients;
        this.shoppingListDAO = new ShoppingListDAO(context);
        this.state = state;
        this.fridgeDAO = new FridgeDAO(context);
    }

    public void setItems(List<FridgeIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ingredient_fridge_item, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        IngredientViewHolder ingredientVH = (IngredientViewHolder) holder;
        FridgeIngredient ingredient = ingredients.get(position);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ingredient.getName());
        if (ingredient.getQuantity() != (0.0) && ingredient.getQuantity() != null) {
            stringBuilder.append(" " + ingredient.getQuantity() + " " + ingredient.getType());
        }

        ingredientVH.tvName.setText(stringBuilder.toString());
        ingredientVH.tvKcal.setText(context.getString(R.string.kcal) + " " + ingredient.getKcal());
        ingredientVH.tvProtein.setText(context.getString(R.string.protein_short_colon) + " " + ingredient.getProtein());
        ingredientVH.tvSugar.setText(context.getString(R.string.sugar_short_colon) + " " + ingredient.getSugar());
        ingredientVH.tvFat.setText(context.getString(R.string.fat_short_colon) + " " + ingredient.getFat());
        ingredientVH.moreOptionsButton.setText("\u22EE");

        PopupMenu popup = new PopupMenu(context, ingredientVH.moreOptionsButton);

        if (state.equals(FridgeActivity.class.getSimpleName())) {
            ingredientVH.moreOptionsButton.setOnClickListener(v -> {
                popup.inflate(R.menu.fridge_ingredient_menu);
                popup.setOnMenuItemClickListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.delete_item:
                            fridgeDAO.deleteProduct(ingredient);
                            ingredients.remove(ingredient);
                            this.notifyDataSetChanged();
                            break;
                        case R.id.edit_item:
                            DialogMenager dialogMenager = new DialogMenager(context, ingredient);
                            Dialog dialog = dialogMenager.showQuantityDialog("edit", state, "TODO");
                            dialog.setOnDismissListener(dialog1 -> {
                                ingredients = fridgeDAO.getProductsList();
                                this.notifyDataSetChanged();
                            });
                            dialog.show();
                            break;
                    }
                    return false;
                });
                popup.show();
            });
        } else if (state.equals(ShoppingListActivity.class.getSimpleName())) {
            ingredientVH.moreOptionsButton.setOnClickListener(v -> {
                popup.inflate(R.menu.shopping_ingredient_menu);

                popup.setOnMenuItemClickListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.delete_item:
                            shoppingListDAO.deleteProduct(ingredient.getId());
                            ingredients.remove(ingredient);
                            this.notifyDataSetChanged();
                            break;
                        case R.id.add_item_to_fridge:
                            if (ingredient.getQuantity() != (0.0) && ingredient.getQuantity() != null) {
                                fridgeDAO.addProduct(ingredient, ingredient.getQuantity().intValue(), "g");
                            } else {
                                fridgeDAO.addProduct(ingredient, null, null);
                            }
                            shoppingListDAO.deleteProduct(ingredient.getId());
                            ingredients.remove(ingredient);
                            this.notifyDataSetChanged();
                            break;
                    }
                    return false;
                });
                popup.show();
            });
        }

    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvKcal;
        TextView tvProtein;
        TextView tvSugar;
        TextView tvFat;
        Button moreOptionsButton;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.ingredient_name);
            tvKcal = itemView.findViewById(R.id.kcal);
            tvProtein = itemView.findViewById(R.id.protein);
            tvSugar = itemView.findViewById(R.id.sugar);
            tvFat = itemView.findViewById(R.id.fat);
            moreOptionsButton = itemView.findViewById(R.id.more_options_button);
        }
    }
}

