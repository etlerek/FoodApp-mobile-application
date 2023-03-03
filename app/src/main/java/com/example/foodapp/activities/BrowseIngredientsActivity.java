package com.example.foodapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.R;
import com.example.foodapp.adapters.IngredientsAdapter;
import com.example.foodapp.daohandlers.IngredientDAO;
import com.example.foodapp.entity.Ingredient;

import java.util.ArrayList;
import java.util.Locale;
import java.util.stream.Collectors;

public class BrowseIngredientsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private IngredientsAdapter adapter;
    private IngredientDAO ingredientDAO;
    private ArrayList<Ingredient> ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_ingredients);

        EditText editText = findViewById(R.id.find_ingredient);

        Intent intent = getIntent();

        recyclerView = findViewById(R.id.ingredients_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ingredientDAO = new IngredientDAO(this);
        ingredients = ingredientDAO.getProductsList();

        adapter = new IngredientsAdapter(
                BrowseIngredientsActivity.this,
                new ArrayList<>(),
                intent.getStringExtra("previousActivity"),
                intent.getStringExtra("ingredientType"));
        recyclerView.setAdapter(adapter);

        adapter.setItems(ingredients);
        adapter.notifyDataSetChanged();

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.setItems(ingredients.stream().filter(ingredient -> ingredient.getName().toLowerCase(Locale.ROOT).contains(editText.getText().toString().toLowerCase(Locale.ROOT))).collect(Collectors.toList()));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}