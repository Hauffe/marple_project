package com.pucpr.alexandre.crudmarple.Model;

import android.content.Context;
import android.widget.Toast;

import com.pucpr.alexandre.crudmarple.DAO.IngredientDAO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class IngredientDTO {
    private static IngredientDTO instance = null;

    private List<Ingredient> ingredients;
    private IngredientDAO dao;
    private Context context;

    protected IngredientDTO() {

    }

    public static IngredientDTO sharedInstance() {

        if (instance == null)
            instance = new IngredientDTO();

        return instance;
    }

    public void setContext(Context context) {

        this.context = context;
        dao = new IngredientDAO(context);
        ingredients = dao.getAll();

    }

    public void addIngredient(Ingredient ingredient) {

        if (dao.insert(ingredient)) {
            ingredients.add(ingredient);
            sortUsers();
        }
        else
            Toast.makeText(
                    context,
                    "Problemas ao tentar inserir o ingrediente",
                    Toast.LENGTH_SHORT).show();
    }

    public void removeIngredient(int position) {
        Ingredient ingredient = ingredients.get(position);
        if (dao.remove(ingredient) > 0) {
            ingredients.remove(ingredient);
            Toast.makeText(context,
                    "ingrediente removido com sucesso!!!",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context,
                    "Não foi possível remover o ingrediente.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void editIngredient(Ingredient new_ingredient, int position) {
        if (dao.update(new_ingredient) > 0) {
            ingredients.set(position, new_ingredient);
            Toast.makeText(context,
                    "ingrediente modificado com sucesso!!!",
                    Toast.LENGTH_SHORT).show();
            sortUsers();
        } else {
            Toast.makeText(context,
                    "Problemas ao tentar modificar o ingrediente.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void clearIngredient() {

        ingredients.clear();
    }

    public List<Ingredient> getIngredients() {

        return ingredients;
    }

    public Ingredient getIngredient(int position) {

        return ingredients.get(position);
    }

    public void sortUsers() {
        Collections.sort(ingredients, new Comparator<Ingredient>() {
            @Override
            public int compare(Ingredient ing1, Ingredient ing2) {

                return ing1.getName().compareToIgnoreCase(ing2.getName());
            }
        });
    }
}
