package com.pucpr.alexandre.crudmarple.Controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.pucpr.alexandre.crudmarple.Model.IngredientDTO;
import com.pucpr.alexandre.crudmarple.R;
import com.pucpr.alexandre.crudmarple.View.IngredientAdapter;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GestureDetector gestureDetector;

    private IngredientAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IngredientDTO.sharedInstance().setContext(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Novo Ingrediente");

        recyclerView = findViewById(R.id.lstIngredients);
        adapter = new IngredientAdapter();
        recyclerView.setAdapter(adapter);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);

        gestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener(){
            @Override
            public void onLongPress(MotionEvent e) {

                View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
                int position = recyclerView.getChildAdapterPosition(view);
                IngredientDTO.sharedInstance().removeIngredient(position);
                adapter.notifyDataSetChanged();
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {

                View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
                int position = recyclerView.getChildAdapterPosition(view);

                Intent intent = new Intent(MainActivity.this, ViewIngredient.class);
                intent.putExtra("position", position);
                startActivityForResult(intent, 3);

                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {

                View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
                int position = recyclerView.getChildAdapterPosition(view);

                Intent intent = new Intent(MainActivity.this, AddEditIngredient.class);
                intent.putExtra("position", position);
                startActivityForResult(intent, 2);

                return true;
            }
        });


        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener(){

            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
                View view = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                return (view != null && gestureDetector.onTouchEvent(motionEvent));
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {

            }
        });

        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        broadcastManager.registerReceiver(updateData, new IntentFilter("updateData"));

    }

    @Override
    protected void onPause() {
        super.onPause();

        if (isFinishing()) {
            LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
            broadcastManager.unregisterReceiver(updateData);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 || requestCode == 2) {
            if (resultCode == RESULT_OK) {
                adapter.notifyDataSetChanged();
            }
        }

        if (requestCode == 3) {
            if (resultCode == RESULT_OK) {
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.mnuAddCity:
                Intent intent = new Intent(MainActivity.this, AddEditIngredient.class);
                intent.putExtra("position", -1);
                startActivityForResult(intent, 1);
                break;

            case R.id.mnuClear:
                AlertDialog.Builder message = new AlertDialog.Builder(this);
                message.setTitle("Limpar?");
                message.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        IngredientDTO.sharedInstance().clearIngredient();
                        adapter.notifyDataSetChanged();
                    }
                });
                message.setNegativeButton("Não", null);
                message.show();
                break;
        }

        return true;
    }

    private BroadcastReceiver updateData = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            adapter.notifyDataSetChanged();
        }
    };
}
