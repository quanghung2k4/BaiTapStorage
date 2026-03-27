package com.learn.baitapstorage.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "Showtimes",
        foreignKeys = {
                @ForeignKey(entity = Movie.class, parentColumns = "id", childColumns = "movieId", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Theater.class, parentColumns = "id", childColumns = "theaterId", onDelete = ForeignKey.CASCADE)
        },
        indices = {@Index("movieId"), @Index("theaterId")}
)
public class Showtime {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int movieId;
    private int theaterId;
    private long startTimeMillis;
    private double price;

    public Showtime() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getTheaterId() {
        return theaterId;
    }

    public void setTheaterId(int theaterId) {
        this.theaterId = theaterId;
    }

    public long getStartTimeMillis() {
        return startTimeMillis;
    }

    public void setStartTimeMillis(long startTimeMillis) {
        this.startTimeMillis = startTimeMillis;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
