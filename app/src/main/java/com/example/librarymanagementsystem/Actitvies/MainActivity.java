package com.example.librarymanagementsystem.Actitvies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.librarymanagementsystem.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        DatabaseSqlite obj = new DatabaseSqlite(this);
//        String n = obj.Insert_Data("hagar","dsdf@gmail.com","ddda4f","01254");
//        ArrayList<Users> d = obj.Get_Data();
//        Toast.makeText(this,d.size()+" ",Toast.LENGTH_LONG).show();
    }
}