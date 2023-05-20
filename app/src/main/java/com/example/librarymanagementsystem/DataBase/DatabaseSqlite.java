package com.example.librarymanagementsystem.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.librarymanagementsystem.models.Trip;
import com.example.librarymanagementsystem.models.Users;

import java.util.ArrayList;

public class DatabaseSqlite extends SQLiteOpenHelper {
    public static final String DatabaseName = "android.db";
    Context context;

    public DatabaseSqlite(Context context) {
        super(context, DatabaseName, null, 5);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Register (id INTEGER primary key AUTOINCREMENT, name TEXT NOT NULL, email NOT NULL UNIQUE, password TEXT NOT NULL, phone TEXT NOT NULL,role TEXT NOT NULL DEFAULT 'user')");
        db.execSQL("create table BusTrip (id INTEGER primary key AUTOINCREMENT, chairNumber INTEGER NOT NULL, tickerPRice INTEGER NOT NULL,time TEXT,name TEXT)");
        db.execSQL("create table UserTrip (id_user INTEGER , id_trip INTEGER,primary key(id_user,id_trip), FOREIGN KEY(id_user) REFERENCES Register(id) ON DELETE CASCADE ON UPDATE NO ACTION, FOREIGN KEY(id_trip) REFERENCES BusTrip(id) ON DELETE CASCADE ON UPDATE NO ACTION)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Register");
        db.execSQL("DROP TABLE IF EXISTS BusTrip");
        db.execSQL("DROP TABLE IF EXISTS UserTrip");
        onCreate(db);
    }

    public String Insert_Data(String n, String e, String pass, String ph)         // insert data for new users
    {
        SQLiteDatabase s = this.getWritableDatabase();                          // make object to insert data
        ContentValues values = new ContentValues();                            // take data that go to table
        values.put("name", n);
        values.put("email", e);
        values.put("password", pass);
        values.put("phone", ph);
        long re = s.insert("Register", null, values);        //   insert data in the table
        if (re == -1) return "Error";
        else return "Inserted row";

    }

    //inset admin
    public String Insert_Admin(String n, String e, String pass, String ph) {
        SQLiteDatabase s = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", n);
        values.put("email", e);
        values.put("password", pass);
        values.put("phone", ph);
        values.put("role", "admin");
        long re = s.insert("Register", null, values);
        if (re == -1) return "Error";
        else return "Inserted row";

    }

    public ArrayList<Users> Get_Data() {
        ArrayList<Users> arrayList = new ArrayList<Users>();
        SQLiteDatabase s = this.getReadableDatabase();
        Cursor c = s.rawQuery("select * from Register", null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            arrayList.add(new Users(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5)));
            c.moveToNext();
        }
        return arrayList;
    }

    //
    public ArrayList<Trip> select_All_Trip() {
        ArrayList<Trip> trips = new ArrayList<>();
        try {
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + "BusTrip", null);
            if (cursor.moveToFirst()) {
                do {

                    int id = cursor.getInt(0);
                    int chairnumber = cursor.getInt(1);
                    int tecket = cursor.getInt(2);
                    String time = cursor.getString(3);
                    String name = cursor.getString(4);
                    Trip c = new Trip(id, time, chairnumber, Double.valueOf(tecket), name);
                    trips.add(c);
                } while (cursor.moveToNext());
                cursor.close();
            }
        } catch (Exception ex) {

        }

        return trips;
    }
    //
    public ArrayList<Trip> select_Spac_Trip(int idtrip) {
        ArrayList<Trip> trips = new ArrayList<>();
        try {
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + "BusTrip WHERE id=?", new String[]{idtrip+""});
            if (cursor.moveToFirst()) {
                do {

                    int id = cursor.getInt(0);
                    int chairnumber = cursor.getInt(1);
                    int tecket = cursor.getInt(2);
                    String time = cursor.getString(3);
                    String name = cursor.getString(4);
                    Trip c = new Trip(id, time, chairnumber, Double.valueOf(tecket), name);
                    trips.add(c);
                } while (cursor.moveToNext());
                cursor.close();
            }
        } catch (Exception ex) {

        }

        return trips;
    }


    /********************************** Insert_Trip *****************************************************/
    public boolean insert_Trip(Trip trip)                                        // insert data for new trips
    {
        SQLiteDatabase st = this.getWritableDatabase();                          // make object to insert data_trip
        ContentValues value = new ContentValues();                              // take data that go to table
        value.put("chairNumber", trip.getChairnumber());
        value.put("tickerPRice", trip.getTicketPrice());
        value.put("time", trip.getTime());
        value.put("name", trip.getName());
        long re = st.insert("BusTrip", null, value);         //   insert data in the table
        return (re != -1) ? true : false;
    }

    /********************************** UPDATE *****************************************************/
    public boolean update_Trip(Trip trip)                      // update trip data
    {
        long inserted = -1;
        try {
            SQLiteDatabase sq = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("chairNumber", trip.getChairnumber());
            values.put("tickerPRice", trip.getTicketPrice());
            values.put("time", trip.getTicketPrice());
            values.put("name", trip.getName());
            inserted = sq.update("BusTrip", values, "id=?", new String[]{trip.getId() + ""});
        } catch (Exception ex) {
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return (inserted > 0) ? true : false;
    }

    /********************************** DELETE *****************************************************/
    public boolean Delete_Trip(int id) {
        int deleted = -1;
        try {
            SQLiteDatabase sql = this.getWritableDatabase();
            deleted = sql.delete("BusTrip", "id=?", new String[]{id+""});
        } catch (Exception ex) {
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return (deleted > 0) ? true : false;
    }
}
