package com.example.foodapp.activities;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.R;
import com.example.foodapp.adapters.FridgeIngredientsAdapter;
import com.example.foodapp.daohandlers.ShoppingListDAO;
import com.example.foodapp.entity.FridgeIngredient;

import java.util.ArrayList;

public class ShoppingListActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private Button addIngredientButton;
    private FridgeIngredientsAdapter adapter;
    private ShoppingListDAO shoppingListDAO;
    private ArrayList<FridgeIngredient> ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        addIngredientButton = findViewById(R.id.add_ingredient_to_fridge_button);
        addIngredientButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, BrowseIngredientsActivity.class);
            intent.putExtra("previousActivity", this.getClass().getSimpleName());
            startActivity(intent);
        });
        recyclerView = findViewById(R.id.ingredients_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL) {
            @Override
            public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
                Drawable shape = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ingredient_items_devider);
                int left = parent.getPaddingLeft();
                int right = parent.getWidth() - parent.getPaddingRight();

                int childCount = parent.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View child = parent.getChildAt(i);

                    RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                    int top = child.getBottom() + params.bottomMargin;
                    int bottom = top + shape.getIntrinsicHeight();

                    shape.setBounds(left, top, right, bottom);
                    shape.draw(canvas);
                }
            }
        });

        shoppingListDAO = new ShoppingListDAO(this);
        ingredients = shoppingListDAO.getShoppingList();

        adapter = new FridgeIngredientsAdapter(this, new ArrayList<>(), this.getClass().getSimpleName());
        recyclerView.setAdapter(adapter);

        adapter.setItems(ingredients);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ingredients = shoppingListDAO.getShoppingList();
        adapter.setItems(ingredients);
        adapter.notifyDataSetChanged();
    }
}