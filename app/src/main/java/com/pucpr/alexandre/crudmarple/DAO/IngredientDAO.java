package com.pucpr.alexandre.crudmarple.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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

public class IngredientDAO extends SQLiteOpenHelper {

    private static final String DB_NAME = "ingredients.sqlite";
    private static final int DB_VERSION = 1;

    private static final String DB_TABLE = "tblIngredient";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_PRIORITY = "priority";

    private Context context;

    public IngredientDAO(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE IF NOT EXISTS " + DB_TABLE + "(" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " +
                COL_PRIORITY + " INTEGER);";
        db.execSQL(sql);
        Toast.makeText(context, sql, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insert(Ingredient ingredient) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_NAME, ingredient.getName());
        values.put(COL_PRIORITY, ingredient.getPriority());

        long id = db.insert(DB_TABLE, "", values);
        db.close();

        if (id > 0) {
            ingredient.setId(id);
            return true;
        }

        return false;
    }

    public int remove(Ingredient ingredient) {

        SQLiteDatabase db = getWritableDatabase();

        String id = String.valueOf(ingredient.getId());
        int count = db.delete(DB_TABLE, "id=?", new String[]{id});
        db.close();

        return count;
    }

    public int update(Ingredient ingredient) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_NAME, ingredient.getName());
        values.put(COL_PRIORITY, ingredient.getPriority());

        String id = String.valueOf(ingredient.getId());

        int count = db.update(DB_TABLE, values, "id=?", new String[]{id});
        db.close();

        return count;
    }

    public List<Ingredient> getAll() {

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(
                DB_TABLE,
                null,
                null,
                null,
                null,
                null,
                COL_NAME + " COLLATE NOCASE");
        List<Ingredient> ingredients = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Ingredient ingredient = new Ingredient(
                        cursor.getString(cursor.getColumnIndex(COL_NAME)),
                        cursor.getInt(cursor.getColumnIndex(COL_PRIORITY))
                );
                ingredient.setId(cursor.getInt(cursor.getColumnIndex(COL_ID)));
                ingredients.add(ingredient);
            } while (cursor.moveToNext());
        }

        db.close();

        return ingredients;
    }

}