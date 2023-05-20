package com.example.librarymanagementsystem.Actitvies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.librarymanagementsystem.DataBase.DatabaseSqlite;
import com.example.librarymanagementsystem.R;
import com.example.librarymanagementsystem.models.Trip;

import java.util.ArrayList;

public class AddTrip extends AppCompatActivity {
    Button btnADDNew;
    TextView txtTime;
    TextView txttripNames;
    TextView txtchairCount;
    TextView txtTiketPRice;
    DatabaseSqlite db;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        inflate();
        db = new DatabaseSqlite(this);
        btnADDNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionbtn();
            }
        });
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getInt("idTrip") != 0) {
                btnADDNew.setText("Update");
                id = bundle.getInt("idTrip");
                ArrayList<Trip> trips = db.select_Spac_Trip(id);
                for (Trip t : trips
                ) {
                    txtTime.setText(t.getTime());
                    txttripNames.setText(t.getName());
                    txtTiketPRice.setText(t.getTicketPrice() + "");
                    txtchairCount.setText(t.getChairnumber() + "");
                }
            }
        }

    }

    private void actionbtn() {
        //first get data

        //array list to collect the info
        ArrayList<String> info = new ArrayList<>();
        if (txtTime.getText().toString().isEmpty()) {
            info.add("Should Enter time");
        }
        if (txtchairCount.getText().toString().isEmpty()) {
            info.add("Should Enter Chair Count");
        }
        if (txtchairCount.getText().toString().isEmpty()) {
            info.add("Should Enter Tiket Count");
        }
        if (txttripNames.getText().toString().isEmpty()) {
            info.add("Should Enter Trip name");
        }
        if (info.size() > 0) {
            Toast.makeText(AddTrip.this, "Should Enter all info", Toast.LENGTH_SHORT).show();
        } else {
            String time = txtTime.getText().toString();
            String name = txttripNames.getText().toString();
            int chairCount = Integer.valueOf(txtchairCount.getText().toString());
            double tiketCount = Double.valueOf(txtchairCount.getText().toString());
            if (btnADDNew.getText().toString().equals("Add New")) {
                boolean inserted = db.insert_Trip(new Trip(time, chairCount, tiketCount, name));
                if (inserted) {
                    Toast.makeText(AddTrip.this, "Succfully added", Toast.LENGTH_SHORT).show();
                    finish();
                }
            } else if (btnADDNew.getText().toString().equals("Update")) {
                boolean update = db.update_Trip(new Trip(id, time, chairCount, tiketCount, name));
                if (update) {
                    Toast.makeText(AddTrip.this, "Succfully Updated", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(AddTrip.this,HomeActivicty.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(AddTrip.this, " not", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "noti", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void inflate() {
        btnADDNew = findViewById(R.id.btnNewTrip);
        txtTime = findViewById(R.id.txttime);
        txtTiketPRice = findViewById(R.id.txticketPrice);
        txtchairCount = findViewById(R.id.txtcountchair);
        txttripNames = findViewById(R.id.txttripname);
    }
}