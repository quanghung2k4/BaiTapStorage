package com.learn.baitapstorage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.learn.baitapstorage.activities.LoginActivity;
import com.learn.baitapstorage.activities.MoviesActivity;
import com.learn.baitapstorage.activities.MyTicketsActivity;
import com.learn.baitapstorage.activities.ShowtimesActivity;
import com.learn.baitapstorage.activities.TheatersActivity;
import com.learn.baitapstorage.dal.SharedPrefsManager;

public class MainActivity extends AppCompatActivity {
    private View btnMovies, btnTheaters, btnShowtimes, btnMyTickets, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Check login session
        if (!SharedPrefsManager.isLoggedIn(this)) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_main);

        btnMovies = findViewById(R.id.btnMovies);
        btnTheaters = findViewById(R.id.btnTheaters);
        btnShowtimes = findViewById(R.id.btnShowtimes);
        btnMyTickets = findViewById(R.id.btnMyTickets);
        btnLogout = findViewById(R.id.btnLogout);

        btnMovies.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, MoviesActivity.class));
        });

        btnTheaters.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, TheatersActivity.class));
        });

        btnShowtimes.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ShowtimesActivity.class));
        });

        btnMyTickets.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, MyTicketsActivity.class));
        });

        btnLogout.setOnClickListener(v -> {
            SharedPrefsManager.clearLogin(this);
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        });
    }
}
