package com.learn.baitapstorage.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "account")
public class Account {
    @PrimaryKey(autoGenerate = true)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
