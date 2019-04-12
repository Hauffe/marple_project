package com.pucpr.alexandre.crudmarple.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.pucpr.alexandre.crudmarple.Model.IngredientDTO;
import com.pucpr.alexandre.crudmarple.Model.Ingredient;
import com.pucpr.alexandre.crudmarple.R;

public class AddEditIngredient extends AppCompatActivity {

    private int POSITION = -1;
    private TextView txtTitle;
    private EditText txtIngredient, txtPriority;
    private Ingredient new_ingredient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_ingredient);

        txtTitle = findViewById(R.id.txtTitle);
        txtIngredient = findViewById(R.id.txtIngredient);
        txtPriority = findViewById(R.id.txtPriority);

        POSITION = getIntent().getIntExtra("position", -1);

        if (POSITION == -1) {
            txtTitle.setText("Novo Ingrediente");
            new_ingredient = new Ingredient("", 0);
        } else {
            txtTitle.setText("Editar Ingrediente");
            new_ingredient = IngredientDTO.sharedInstance().getIngredients().get(POSITION);
            txtIngredient.setText(new_ingredient.getName());
            txtPriority.setText(String.valueOf(new_ingredient.getPriority()));
        }
    }

    public void btnCancelOnClick(View v) {

        setResult(RESULT_CANCELED);
        finish();
    }

    public void btnSaveOnClick(View v) {

        new_ingredient = new Ingredient(txtIngredient.getText().toString(), Integer.parseInt(txtPriority.getText().toString()));
        if (POSITION == -1) {
            IngredientDTO.sharedInstance().addIngredient(new_ingredient);
        } else {
            new_ingredient.setId(IngredientDTO.sharedInstance().getIngredients().get(POSITION).getId());
            IngredientDTO.sharedInstance().editIngredient(new_ingredient, POSITION);

        }

        //setResult(RESULT_OK);
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        broadcastManager.sendBroadcast(new Intent("updateData"));
        finish();
    }
}
