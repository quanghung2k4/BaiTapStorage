package com.learn.baitapstorage.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.learn.baitapstorage.R;
import com.learn.baitapstorage.adapter.TheaterAdapter;
import com.learn.baitapstorage.dal.AppDatabase;
import com.learn.baitapstorage.models.Theater;

import java.util.List;

public class TheatersActivity extends AppCompatActivity {
    private RecyclerView rvTheaters;
    private TheaterAdapter adapter;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theaters);

        rvTheaters = findViewById(R.id.rvTheaters);
        db = AppDatabase.getInstance(this);

        List<Theater> theaterList = db.appDao().getAllTheaters();
        adapter = new TheaterAdapter(theaterList, theater -> {
            Intent intent = new Intent(TheatersActivity.this, ShowtimesActivity.class);
            intent.putExtra("THEATER_ID", theater.getId());
            startActivity(intent);
        });

        rvTheaters.setLayoutManager(new LinearLayoutManager(this));
        rvTheaters.setAdapter(adapter);
    }
}
