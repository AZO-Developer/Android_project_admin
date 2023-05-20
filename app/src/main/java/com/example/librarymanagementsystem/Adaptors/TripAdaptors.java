package com.example.librarymanagementsystem.Adaptors;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.librarymanagementsystem.Actitvies.AddTrip;
import com.example.librarymanagementsystem.Actitvies.HomeActivicty;
import com.example.librarymanagementsystem.DataBase.DatabaseSqlite;
import com.example.librarymanagementsystem.Interfaces.OnRecyclerViewItemClickLestenr;
import com.example.librarymanagementsystem.R;
import com.example.librarymanagementsystem.models.Trip;

import java.util.ArrayList;


public class TripAdaptors extends RecyclerView.Adapter<TripAdaptors.MyViewHolder> {
    private Context context;
    private int Resource;
    private ArrayList<Trip> trips;
    private OnRecyclerViewItemClickLestenr listner;
DatabaseSqlite db;
    public TripAdaptors(Context context, ArrayList<Trip> trips, int Resource, OnRecyclerViewItemClickLestenr listner) {
        this.trips = trips;
        this.context = context;
        this.Resource = Resource;
        this.listner = listner;
        db=new DatabaseSqlite(context);
    }

    public void SetData(int pos, Trip trip) {
        trips.set(pos, trip);
        notifyDataSetChanged();//

    }

    public void addNewData(Trip trip) {
        trips.add(trip);
        notifyDataSetChanged();//

    }

    public void DeleteItem(int id) {
        trips.remove(id);
        notifyDataSetChanged();//
    }

    public void refreshAdaptors(ArrayList<Trip> trips) {
        this.trips = trips;
        notifyDataSetChanged();//
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(Resource, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Trip trip = trips.get(position);

        holder.id.setText(trip.getId() + "");
        holder.time.setText(trip.getTime() + "");
        holder.chairCount.setText(trip.getChairnumber() + "");
        holder.ticketPrice.setText(trip.getTicketPrice() + "");
        try{
            holder.tname.setText(trip.getName() + "");
        }catch (Exception ex){
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

        holder.time.setTag(position);//عشان تخفي مثلا رقم جواها
    }

    @Override
    public int getItemCount() {
        return trips.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView id;
        TextView time;
        TextView chairCount;
        TextView ticketPrice;
        TextView tname;
        Button btupdate;
        Button btdelete;
        


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.txtid);
            time = itemView.findViewById(R.id.txttime);
            chairCount = itemView.findViewById(R.id.txtcountChair);
            ticketPrice = itemView.findViewById(R.id.txtTicketPrice);
            tname = itemView.findViewById(R.id.txtname);
            btdelete = itemView.findViewById(R.id.btndelete);
            btupdate = itemView.findViewById(R.id.btnUpdate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //عشان تسدعي بقي اللي كنت خافبة
                    int idRc = (int) time.getTag();
                    int idtrip=Integer.valueOf(id.getText().toString());
                    listner.onItemClick(idtrip,idRc);
                }
            });
            btupdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int idRc = (int) time.getTag();
                    int idtrip=Integer.valueOf(id.getText().toString());
                    Intent intent=new Intent(context, AddTrip.class);
                    Bundle bundle=new Bundle();
                    bundle.putInt("idTrip",idtrip);
                    intent.putExtras(bundle);
                   try {
                       ContextCompat.startActivity(v.getContext(),intent,bundle);
                   }catch (Exception ex){
                       Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
                   }
                }
            });

            btdelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int idRc = (int) time.getTag();
                    int idtrip=Integer.valueOf(id.getText().toString());
                   boolean delted= db.Delete_Trip(idtrip);
                   if(delted){
                       Toast.makeText(context, "deleted", Toast.LENGTH_SHORT).show();
                       ArrayList<Trip> tripss = db.select_All_Trip();
                       refreshAdaptors(tripss);
                   }
                }
            });
        }
    }

}
