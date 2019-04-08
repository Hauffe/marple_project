package com.pucpr.alexandre.crudmarple.Model;

import android.content.Context;

import com.pucpr.alexandre.crudmarple.DAO.IngredientDAO;

import java.util.ArrayList;
import java.util.List;

public class IngredientDTO {
    private static IngredientDTO instance = null;

    private List<Ingredient> ingredients;
    private IngredientDAO dao;
    private Context context;

    protected IngredientDTO() {
        dao = new IngredientDAO();
    }

    public static IngredientDTO sharedInstance() {

        if (instance == null)
            instance = new IngredientDTO();

        return instance;
    }

    public void setContext(Context context) {

        this.context = context;
        ingredients = dao.loadData(context);

    }

    public void addIngredient(Ingredient ingredient) {

        ingredients.add(ingredient);
        dao.saveData(context, ingredients);
    }

    public void removeIngredient(int position) {

        ingredients.remove(position);
        dao.saveData(context, ingredients);
    }

    public void editIngredient(Ingredient ingredient, int position) {

        ingredients.set(position, ingredient);
        dao.saveData(context, ingredients);
    }

    public void clearIngredient() {

        ingredients.clear();
        dao.saveData(context, ingredients);
    }

    public List<Ingredient> getIngredients() {

        return ingredients;
    }

    public Ingredient getIngredient(int position) {

        return ingredients.get(position);
    }
}
