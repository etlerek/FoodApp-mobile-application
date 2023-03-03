package com.example.foodapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodapp.R;
import com.example.foodapp.daohandlers.RecipeDAO;
import com.example.foodapp.entity.Step;

public class AddStepActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_step);

        EditText titleEditText = findViewById(R.id.title_et);
        EditText contentEditText = findViewById(R.id.content_et);
        Button addStepButton = findViewById(R.id.add_step_btn);

        addStepButton.setOnClickListener(view -> {
            String title = titleEditText.getText().toString();
            String content = contentEditText.getText().toString();
            if (title.isEmpty() || content.isEmpty()) {
                Toast.makeText(this, R.string.fill_all_fields, Toast.LENGTH_SHORT).show();
            } else {
                RecipeDAO recipeDAO = new RecipeDAO(this);
                Intent intent = getIntent();
                Step step = new Step();
                step.setStepNumber(Integer.parseInt(intent.getStringExtra("stepNr")));
                step.setTitle(title);
                step.setContent(content);
                recipeDAO.addStepToRecipe(step);
                finish();
            }
        });
    }
}