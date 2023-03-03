package com.example.foodapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.R;
import com.example.foodapp.activities.RecipeActivity;
import com.example.foodapp.algorithmhandler.RecipeWeightHandler;

import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private List<RecipeWeightHandler> recipes;

    public RecipesAdapter(Context context, List<RecipeWeightHandler> recipes){
        this.context = context;
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_item, parent, false);
        return new RecipesAdapter.RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RecipesAdapter.RecipeViewHolder recipeVH = (RecipesAdapter.RecipeViewHolder) holder;
        RecipeWeightHandler recipeWeightHandler = recipes.get(position);

        recipeVH.tvName.setText(recipeWeightHandler.getRecipe().getName());
        recipeVH.tvContent.setText(recipeWeightHandler.getRecipe().getGeneralDescription());
        recipeVH.tvMainNeeded.setText(context.getString(R.string.main) + ": " + recipeWeightHandler.getMainIngredientsNumber() + "/" + recipeWeightHandler.getRecipe().getMainIngredients().size());
        recipeVH.tvSubNeeded.setText(context.getString(R.string.sub) + ": " + recipeWeightHandler.getSubIngredientsNumber() + "/" + recipeWeightHandler.getRecipe().getSubIngredients().size());
        recipeVH.tvDifficulty.setText(context.getString(R.string.diff) + ": " + recipeWeightHandler.getRecipe().getDifficulty());
        recipeVH.tvTime.setText(context.getString(R.string.time) + ": " + recipeWeightHandler.getRecipe().getTime());
        recipeVH.tvPortion.setText(context.getString(R.string.portions) + ": " + recipeWeightHandler.getPortionNumber() + "/" + recipeWeightHandler.getRecipe().getPortion());
        recipeVH.tvMatch.setText(context.getString(R.string.compatibility) + ": " + recipeWeightHandler.getRecipeWeight());
        recipeVH.tvMatch.setVisibility(View.GONE);

        recipeVH.llItem.setOnClickListener(v -> {
            Intent intent = new Intent(context, RecipeActivity.class);
            intent.putExtra("previousActivity", context.getClass().getSimpleName());
            intent.putExtra("id", recipeWeightHandler.getRecipe().getId());
            context.startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder{

        LinearLayout llItem;
        TextView tvName;
        TextView tvContent;
        TextView tvMainNeeded;
        TextView tvSubNeeded;
        TextView tvDifficulty;
        TextView tvTime;
        TextView tvPortion;
        TextView tvMatch;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            llItem = itemView.findViewById(R.id.recipe_item_id);
            tvName = itemView.findViewById(R.id.recipe_title);
            tvContent = itemView.findViewById(R.id.recipe_content);
            tvMainNeeded = itemView.findViewById(R.id.main_ingredients);
            tvSubNeeded = itemView.findViewById(R.id.sub_ingredients);
            tvDifficulty = itemView.findViewById(R.id.difficulty);
            tvTime = itemView.findViewById(R.id.time);
            tvPortion = itemView.findViewById(R.id.recipe_portion);
            tvMatch = itemView.findViewById(R.id.weight);
        }
    }

    public void setItems(List<RecipeWeightHandler> recipes){
        this.recipes = recipes;
    }
}
