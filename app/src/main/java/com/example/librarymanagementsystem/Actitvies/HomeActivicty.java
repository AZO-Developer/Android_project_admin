package com.example.librarymanagementsystem.Actitvies;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.librarymanagementsystem.Adaptors.TripAdaptors;
import com.example.librarymanagementsystem.DataBase.DatabaseSqlite;
import com.example.librarymanagementsystem.Interfaces.OnRecyclerViewItemClickLestenr;
import com.example.librarymanagementsystem.R;
import com.example.librarymanagementsystem.models.Trip;

import java.util.ArrayList;

public class HomeActivicty extends AppCompatActivity {
    Button btnAdd;
    RecyclerView recyclerView;
    DatabaseSqlite db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_activicty);
        //inflate for add button
        btnAdd = (Button) findViewById(R.id.btnAdd);
        recyclerView = findViewById(R.id.rcTrip);
        db = new DatabaseSqlite(getApplicationContext());
        //action for btn add
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //this it action
                Intent intent = new Intent(getApplicationContext(), AddTrip.class);
                startActivityForResult(intent, 1);
            }
        });
        getAlltrip();

    }

    private void getAlltrip() {
        try {
            ArrayList<Trip> trips = db.select_All_Trip();
            TripAdaptors recyclerViewAdaptor = new TripAdaptors(getApplicationContext(), trips, R.layout.show_all_trip, new OnRecyclerViewItemClickLestenr() {
                @Override
                public void onItemClick(int idtrip, int idRc) {
                    Toast.makeText(HomeActivicty.this, idtrip+"", Toast.LENGTH_SHORT).show();
                }
            });

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            RecyclerView.LayoutManager gridlayout = new GridLayoutManager(getApplicationContext(), 2);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(recyclerViewAdaptor);
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) getAlltrip();
        else getAlltrip();
    }
}