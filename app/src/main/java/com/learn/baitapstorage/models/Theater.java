package com.learn.baitapstorage.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Theaters")
public class Theater {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String location;

    public Theater() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
