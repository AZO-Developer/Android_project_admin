package com.example.librarymanagementsystem.models;

import java.io.Serializable;

public class Trip implements Serializable {
    private int id;
    private String time;
    private String name;
    private int chairnumber;
    private double ticketPrice;

    public Trip(int id, String time, int chairnumber, double ticketPrice, String name) {
        this.id = id;
        this.time = time;
        this.name = name;
        this.chairnumber = chairnumber;
        this.ticketPrice = ticketPrice;
    }

    public Trip(String time, int chairnumber, double ticketPrice, String name) {

        this.time = time;
        this.name = name;
        this.chairnumber = chairnumber;
        this.ticketPrice = ticketPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getChairnumber() {
        return chairnumber;
    }

    public void setChairnumber(int chairnumber) {
        this.chairnumber = chairnumber;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }
}
