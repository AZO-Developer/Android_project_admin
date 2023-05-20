package com.example.librarymanagementsystem.DataBase;

import android.content.Context;

public class database {
    private static DatabaseSqlite dp;
    public static void connect(Context con){
        dp=new DatabaseSqlite(con);
    }
    public static DatabaseSqlite get_dp(){
        return dp;
    }
}
