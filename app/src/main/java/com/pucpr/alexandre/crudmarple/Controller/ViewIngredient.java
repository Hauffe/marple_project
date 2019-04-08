package com.pucpr.alexandre.crudmarple.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.pucpr.alexandre.crudmarple.Model.IngredientDTO;
import com.pucpr.alexandre.crudmarple.Model.Ingredient;
import com.pucpr.alexandre.crudmarple.R;

public class ViewIngredient extends AppCompatActivity {

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_ingredients);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        position = getIntent().getIntExtra("position", -1);
        Ingredient ingredient = IngredientDTO.sharedInstance().getIngredient(position);

        TextView txtViewIngredient = findViewById(R.id.txtViewIngredient);
        TextView txtViewPriority = findViewById(R.id.txtViewPriority);

        txtViewIngredient.setText("Ingrente: "+ingredient.getName());
        txtViewPriority.setText("Prioridade: "+ingredient.getPriority());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                setResult(RESULT_CANCELED);
                finish();
                break;
        }

        return true;
    }

    public void btnEditOnClick(View v) {

        Intent intent = new Intent(this, AddEditIngredient.class);
        intent.putExtra("position", position);
        //startActivityForResult(intent, 4);
        startActivity(intent);
        finish();
    }

    public void btnDelOnClick(View v) {

        IngredientDTO.sharedInstance().removeIngredient(position);
        //setResult(RESULT_OK);
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        broadcastManager.sendBroadcast(new Intent("updateData"));
        finish();
    }
}
