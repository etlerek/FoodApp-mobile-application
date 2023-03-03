package com.example.foodapp.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.R;
import com.example.foodapp.entity.Ingredient;
import com.example.foodapp.ui.DialogMenager;

import java.util.List;


public class IngredientsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private final Context context;
    private List<Ingredient> ingredients;
    private final String state;
    private final String type;

    public IngredientsAdapter(Context context, List<Ingredient> ingredients, String state, String type){
        super();
        this.context = context;
        this.ingredients = ingredients;
        this.state = state;
        this.type = type;
    }

    public void setItems(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ingredient_item, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        IngredientViewHolder ingredientVH = (IngredientViewHolder) holder;
        Ingredient ingredient = ingredients.get(position);

        ingredientVH.tvName.setText(ingredient.getName());
        ingredientVH.tvKcal.setText(context.getString(R.string.kcal) + " " + ingredient.getKcal());
        ingredientVH.tvProtein.setText(context.getString(R.string.protein_short_colon) + " " + ingredient.getProtein());
        ingredientVH.tvSugar.setText(context.getString(R.string.sugar_short_colon) + " " + ingredient.getSugar());
        ingredientVH.tvFat.setText(context.getString(R.string.fat_short_colon) + " " + ingredient.getFat());
        ingredientVH.btAddToFridge.setText("+");

        ingredientVH.btAddToFridge.setOnClickListener(v -> {
            DialogMenager dialogMenager = new DialogMenager(context, ingredient);
            AlertDialog dialog = (AlertDialog) dialogMenager.showQuantityDialog("add", state, type);
            dialog.show();
            dialog.getButton(dialog.BUTTON_NEGATIVE).setTextColor(context.getColor(R.color.green));
            dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(context.getColor(R.color.green));
        });
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder{

        TextView tvName;
        TextView tvKcal;
        TextView tvProtein;
        TextView tvSugar;
        TextView tvFat;
        Button btAddToFridge;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.ingredient_name);
            tvKcal = itemView.findViewById(R.id.kcal);
            tvProtein = itemView.findViewById(R.id.protein);
            tvSugar = itemView.findViewById(R.id.sugar);
            tvFat = itemView.findViewById(R.id.fat);
            btAddToFridge = itemView.findViewById(R.id.add_to_fridge_db_button);
        }
    }
}

