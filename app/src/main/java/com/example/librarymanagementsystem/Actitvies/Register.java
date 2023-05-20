package com.example.librarymanagementsystem.Actitvies;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.librarymanagementsystem.R;
import com.example.librarymanagementsystem.DataBase.database;
import com.example.librarymanagementsystem.DataBase.DatabaseSqlite;

public class Register extends AppCompatActivity {

    EditText t1, t2, t3, t4;
    DatabaseSqlite obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        obj = database.get_dp();
        findViewById(R.id.registerButton).setOnClickListener(new View.OnClickListener() {
            AlertDialog.Builder alertDialog;

            @Override
            public void onClick(View v) {
                // Intent intent = new Intent();
                t1 = (EditText) findViewById(R.id.registerName);
                t2 = (EditText) findViewById(R.id.registerEmail);
                t3 = (EditText) findViewById(R.id.registerPassword);
                t4 = (EditText) findViewById(R.id.registerPhone);
                //validate inputs
                alertDialog = new AlertDialog.Builder(Register.this);
                alertDialog.setTitle("Validation Error");
                if (t1.getText().toString().trim().length() == 0 || t1.getText().toString().matches(".*\\d.*")) {
                    alertDialog.setMessage("please enter valid name");
                    alertDialog.setPositiveButton("Ok", (dialog, which) -> dialog.cancel());
                    alertDialog.create();
                    alertDialog.show();
                    return;
                }
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(t2.getText().toString().trim()).matches()) {
                    alertDialog.setMessage("please enter valid email");
                    alertDialog.setPositiveButton("Ok", (dialog, which) -> dialog.cancel());
                    alertDialog.create();
                    alertDialog.show();
                    return;
                }
                if (t3.getText().toString().trim().length() == 0) {
                    alertDialog.setMessage("please enter valid password");
                    alertDialog.setPositiveButton("Ok", (dialog, which) -> dialog.cancel());
                    alertDialog.create();
                    alertDialog.show();
                    return;
                }
                if (!android.util.Patterns.PHONE.matcher(t4.getText().toString().trim()).matches()) {
                    alertDialog.setMessage("please enter valid phone number");
                    alertDialog.setPositiveButton("Ok", (dialog, which) -> dialog.cancel());
                    alertDialog.create();
                    alertDialog.show();
                    return;
                }
                obj.Insert_Data(t1.getText().toString(), t2.getText().toString(), t3.getText().toString(), t4.getText().toString());
                Toast.makeText(Register.this, "Registered successfully", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });
    }
}