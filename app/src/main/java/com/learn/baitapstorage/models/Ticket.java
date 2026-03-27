package com.learn.baitapstorage.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "Tickets",
        foreignKeys = {
                @ForeignKey(entity = Showtime.class, parentColumns = "id", childColumns = "showtimeId", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "userId", onDelete = ForeignKey.CASCADE)
        },
        indices = {@Index("showtimeId"), @Index("userId")}
)
public class Ticket {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int showtimeId;
    private int userId;
    private String seatCode; // e.g. A1
    private long bookedAtMillis;

    public Ticket() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(int showtimeId) {
        this.showtimeId = showtimeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSeatCode() {
        return seatCode;
    }

    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    public long getBookedAtMillis() {
        return bookedAtMillis;
    }

    public void setBookedAtMillis(long bookedAtMillis) {
        this.bookedAtMillis = bookedAtMillis;
    }
}
