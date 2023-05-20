package com.example.librarymanagementsystem.Actitvies;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.librarymanagementsystem.R;
import com.example.librarymanagementsystem.DataBase.database;
import com.google.android.material.textfield.TextInputEditText;
import com.example.librarymanagementsystem.models.Users;

public class Login extends AppCompatActivity {
    TextInputEditText emailInput, passwordInput;
    AlertDialog.Builder alertDialog;

    @SuppressLint("SuspiciousIndentation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database.connect(this);
        if (database.get_dp().Get_Data().size() == 0)
            database.get_dp().Insert_Admin("amr", "amrmousa1682@gmail.com", "1682", "010");
        setContentView(R.layout.activity_login);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        emailInput.setText("amrmousa1682@gmail.com");
        passwordInput.setText("1682");
    }

    public void loginHandler(View v) {
        for (Users user : database.get_dp().Get_Data()) {
            System.out.println(user.getEmail() + " " + user.getPass() + " " + user.getRole());
        }
        alertDialog = new AlertDialog.Builder(Login.this);
        alertDialog.setTitle("Validation Error");
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailInput.getText().toString().trim()).matches()) {
            alertDialog.setMessage("please enter valid email");
            alertDialog.setPositiveButton("Ok", (dialog, which) -> dialog.cancel());
            alertDialog.create();
            alertDialog.show();
            return;
        }
        if (passwordInput.getText().toString().trim().length() == 0) {
            alertDialog.setMessage("please enter valid password");
            alertDialog.setPositiveButton("Ok", (dialog, which) -> dialog.cancel());
            alertDialog.create();
            alertDialog.show();
            return;
        }
        for (Users user : database.get_dp().Get_Data()) {
            if (user.getEmail().equals(emailInput.getText().toString().trim()) && user.getPass().equals(passwordInput.getText().toString().trim())) {
                if (user.getRole().equals("admin")) {
                    Toast.makeText(this, "Login Successfully as admin", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, HomeActivicty.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Login Successfully as user", Toast.LENGTH_SHORT).show();

                }
                return;
            }
        }
        alertDialog.setTitle("Login Error");
        alertDialog.setMessage("wrong email or password");
        alertDialog.setPositiveButton("Ok", (dialog, which) -> dialog.cancel());
        alertDialog.create();
        alertDialog.show();
    }

    public void registerHandler(View v) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

}
