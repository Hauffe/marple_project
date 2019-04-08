package com.pucpr.alexandre.crudmarple.View;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pucpr.alexandre.crudmarple.Model.IngredientDTO;
import com.pucpr.alexandre.crudmarple.Model.Ingredient;
import com.pucpr.alexandre.crudmarple.R;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientHolder> {

    private List<Ingredient> ingredients = IngredientDTO.sharedInstance().getIngredients();

    @NonNull
    @Override
    public IngredientHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recyclerview_ingredients, viewGroup, false);

        return new IngredientHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientHolder ingredientHolder, int position) {
        Ingredient ingredient = ingredients.get(position);

        ingredientHolder.txtIngredient.setText(ingredient.getName());
        ingredientHolder.txtPriority.setText(String.valueOf(ingredient.getPriority()));
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class IngredientHolder extends RecyclerView.ViewHolder {

        TextView txtIngredient;
        TextView txtPriority;

        public IngredientHolder(@NonNull View itemView) {
            super(itemView);

            txtIngredient = itemView.findViewById(R.id.txtIngredient);
            txtPriority = itemView.findViewById(R.id.txtPriority);
        }
    }
}
