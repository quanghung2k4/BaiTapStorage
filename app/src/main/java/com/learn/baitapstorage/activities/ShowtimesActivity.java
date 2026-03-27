package com.learn.baitapstorage.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.learn.baitapstorage.R;
import com.learn.baitapstorage.adapter.ShowtimeAdapter;
import com.learn.baitapstorage.dal.AppDatabase;
import com.learn.baitapstorage.models.Showtime;

import java.util.List;

public class ShowtimesActivity extends AppCompatActivity {
    private RecyclerView rvShowtimes;
    private ShowtimeAdapter adapter;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showtimes);

        rvShowtimes = findViewById(R.id.rvShowtimes);
        db = AppDatabase.getInstance(this);

        int movieId = getIntent().getIntExtra("MOVIE_ID", -1);
        int theaterId = getIntent().getIntExtra("THEATER_ID", -1);

        List<Showtime> showtimeList;
        if (movieId != -1) {
            showtimeList = db.appDao().getShowtimesByMovie(movieId);
        } else if (theaterId != -1) {
            showtimeList = db.appDao().getShowtimesByTheater(theaterId);
        } else {
            showtimeList = db.appDao().getAllShowtimes();
        }

        adapter = new ShowtimeAdapter(showtimeList, db.appDao(), showtime -> {
            Intent intent = new Intent(ShowtimesActivity.this, SeatSelectActivity.class);
            intent.putExtra("SHOWTIME_ID", showtime.getId());
            startActivity(intent);
        });

        rvShowtimes.setLayoutManager(new LinearLayoutManager(this));
        rvShowtimes.setAdapter(adapter);
    }
}
