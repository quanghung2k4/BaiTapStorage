package com.learn.baitapstorage.dal;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.learn.baitapstorage.models.Movie;
import com.learn.baitapstorage.models.Showtime;
import com.learn.baitapstorage.models.Theater;
import com.learn.baitapstorage.models.Ticket;
import com.learn.baitapstorage.models.User;


@Database(entities = {User.class, Movie.class, Theater.class, Showtime.class, Ticket.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DAO appDao();
    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "movie_ticket_db")
                    .allowMainThreadQueries() // Cho phép query trên Main Thread theo yêu cầu
                    .fallbackToDestructiveMigration()
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            // Thêm cứng dữ liệu mẫu khi DB vừa tạo
                            db.execSQL("INSERT INTO Users (username, password, fullName) VALUES ('admin', '123456', 'Administrator')");
                            db.execSQL("INSERT INTO Users (username, password, fullName) VALUES ('user', '123456', 'Demo User')");

                            db.execSQL("INSERT INTO Movies (title, genre, durationMinutes, description, posterUrl) VALUES ('Dune: Part Two', 'Sci-Fi', 166, 'Epic sci-fi adventure', 'https://m.media-amazon.com/images/I/81Tq44QkDCL._AC_UF894,1000_QL80_.jpg')");
                            db.execSQL("INSERT INTO Movies (title, genre, durationMinutes, description, posterUrl) VALUES ('Kung Fu Panda 4', 'Animation', 94, 'Family animation', 'https://m.media-amazon.com/images/I/81C0y9JwqUL._AC_UF894,1000_QL80_.jpg')");

                            db.execSQL("INSERT INTO Theaters (name, location) VALUES ('CGV Vincom', 'Q1, TP.HCM')");
                            db.execSQL("INSERT INTO Theaters (name, location) VALUES ('Lotte Cinema', 'Q7, TP.HCM')");

                            long now = System.currentTimeMillis();
                            db.execSQL("INSERT INTO Showtimes (movieId, theaterId, startTimeMillis, price) VALUES (1, 1, " + (now + 3600_000) + ", 90000)");
                            db.execSQL("INSERT INTO Showtimes (movieId, theaterId, startTimeMillis, price) VALUES (1, 2, " + (now + 7200_000) + ", 95000)");
                            db.execSQL("INSERT INTO Showtimes (movieId, theaterId, startTimeMillis, price) VALUES (2, 1, " + (now + 5400_000) + ", 80000)");
                        }
                    })
                    .build();
        }
        return instance;
    }
}
