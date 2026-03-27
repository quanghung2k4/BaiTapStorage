package com.learn.baitapstorage.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.learn.baitapstorage.MainActivity;
import com.learn.baitapstorage.R;
import com.learn.baitapstorage.dal.AppDatabase;
import com.learn.baitapstorage.models.Movie;
import com.learn.baitapstorage.models.Showtime;
import com.learn.baitapstorage.models.Theater;
import com.learn.baitapstorage.models.Ticket;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TicketResultActivity extends AppCompatActivity {
    private TextView tvMovie, tvTheater, tvDateTime, tvSeat, tvPrice;
    private Button btnBackHome;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_result);

        tvMovie = findViewById(R.id.tvMovieTitle);
        tvTheater = findViewById(R.id.tvTheaterName);
        tvDateTime = findViewById(R.id.tvDateTime);
        tvSeat = findViewById(R.id.tvSeat);
        tvPrice = findViewById(R.id.tvPrice);
        btnBackHome = findViewById(R.id.btnBackHome);
        db = AppDatabase.getInstance(this);

        int ticketId = getIntent().getIntExtra("TICKET_ID", -1);
        if (ticketId != -1) {
            displayTicketDetails(ticketId);
        }

        btnBackHome.setOnClickListener(v -> {
            Intent intent = new Intent(TicketResultActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void displayTicketDetails(int ticketId) {
        Ticket ticket = db.appDao().getTicketById(ticketId);
        if (ticket != null) {
            Showtime showtime = db.appDao().getShowtimeById(ticket.getShowtimeId());
            if (showtime != null) {
                Movie movie = db.appDao().getMovieById(showtime.getMovieId());
                Theater theater = db.appDao().getTheaterById(showtime.getTheaterId());

                if (movie != null) tvMovie.setText(movie.getTitle());
                if (theater != null) tvTheater.setText(theater.getName());
                
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
                tvDateTime.setText(sdf.format(new Date(showtime.getStartTimeMillis())));
                tvSeat.setText(ticket.getSeatCode());
                tvPrice.setText(String.format(Locale.getDefault(), "%,.0f VND", showtime.getPrice()));
            }
        }
    }
}
