package com.learn.baitapstorage.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.learn.baitapstorage.R;
import com.learn.baitapstorage.adapter.MovieAdapter;
import com.learn.baitapstorage.dal.AppDatabase;
import com.learn.baitapstorage.models.Movie;

import java.util.List;

public class MoviesActivity extends AppCompatActivity {
    private RecyclerView rvMovies;
    private MovieAdapter adapter;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        rvMovies = findViewById(R.id.rvMovies);
        db = AppDatabase.getInstance(this);

        List<Movie> movieList = db.appDao().getAllMovies();
        adapter = new MovieAdapter(movieList, movie -> {
            Intent intent = new Intent(MoviesActivity.this, ShowtimesActivity.class);
            intent.putExtra("MOVIE_ID", movie.getId());
            startActivity(intent);
        });

        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        rvMovies.setAdapter(adapter);
    }
}
