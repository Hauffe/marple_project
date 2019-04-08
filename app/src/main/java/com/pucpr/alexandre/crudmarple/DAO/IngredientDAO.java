package com.pucpr.alexandre.crudmarple.DAO;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import com.pucpr.alexandre.crudmarple.Model.Ingredient;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class IngredientDAO {

    public List<Ingredient> loadDataFromAppFolder(Context context) {

        List<Ingredient> ingredients = new ArrayList<>();
        try {
            String line;
            InputStream inputStream = context.openFileInput("db.txt");
            InputStreamReader streamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(streamReader);

            while ((line = reader.readLine()) != null) {
                String attr[] = line.split(";", 0);
                ingredients.add(new Ingredient(attr[0], Integer.parseInt(attr[1])));
            }
            reader.close();
            streamReader.close();
            inputStream.close();
            return ingredients;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ingredients;
    }


    public List<Ingredient> loadDataFromAssets(Context context) {
        List<Ingredient> ingredients = new ArrayList<>();
        try {
            String line;
            InputStream inputStream = context.getAssets().open("db.txt");
            InputStreamReader streamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(streamReader);

            while ((line = reader.readLine()) != null) {

                String attr[] = line.split(";", 0);
                ingredients.add(new Ingredient(attr[0], Integer.parseInt(attr[1])));
            }
            saveData(context, ingredients);
            reader.close();
            streamReader.close();
            inputStream.close();
            return ingredients;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ingredients;
    }

    public boolean saveData(Context context, List<Ingredient> ingredients) {
        String str = "";
        try {
            OutputStream outputStream = context.openFileOutput("db.txt", Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(outputStream);
            for(Ingredient ingredient : ingredients){
                str += ingredient.getName() + ";" + ingredient.getPriority() + "\n";
            }
            writer.write(str);
            writer.flush();
            writer.close();
            outputStream.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Ingredient> loadData(Context context) {
        List<Ingredient> ingredients = loadDataFromAppFolder(context);
        if (!ingredients.isEmpty()) {
            Toast.makeText(
                    context,
                    "Usando pasta raíz",
                    Toast.LENGTH_LONG).show();
            return loadDataFromAppFolder(context);
        } else {
            ingredients = loadDataFromAssets(context);
            if (!ingredients.isEmpty()) {
                Toast.makeText(
                        context,
                        "Usando assets",
                        Toast.LENGTH_LONG).show();
                return loadDataFromAssets(context);
            } else {
                Toast.makeText(
                        context,
                        "Dados não encontrados",
                        Toast.LENGTH_LONG).show();
                return null;
            }
        }
    }

}