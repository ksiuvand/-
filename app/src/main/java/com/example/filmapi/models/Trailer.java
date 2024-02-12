package com.example.filmapi.models;

import java.util.ArrayList;

public class Trailer {
    public int total;
    public ArrayList<Video> items;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<Video> getItems() {
        return items;
    }

    public void setItems(ArrayList<Video> items) {
        this.items = items;
    }
}
