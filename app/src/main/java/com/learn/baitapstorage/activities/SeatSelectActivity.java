package com.learn.baitapstorage.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.learn.baitapstorage.R;
import com.learn.baitapstorage.dal.AppDatabase;
import com.learn.baitapstorage.dal.SharedPrefsManager;
import com.learn.baitapstorage.models.Ticket;

import java.util.ArrayList;
import java.util.List;

public class SeatSelectActivity extends AppCompatActivity {
    private GridLayout glSeats;
    private TextView tvSelectedSeat;
    private Button btnConfirmSeat;
    private AppDatabase db;
    private int showtimeId;
    private String selectedSeatCode = null;
    private List<String> bookedSeats = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_select);

        glSeats = findViewById(R.id.glSeats);
        tvSelectedSeat = findViewById(R.id.tvSelectedSeat);
        btnConfirmSeat = findViewById(R.id.btnConfirmSeat);
        db = AppDatabase.getInstance(this);

        showtimeId = getIntent().getIntExtra("SHOWTIME_ID", -1);
        if (showtimeId == -1) {
            finish();
            return;
        }

        loadBookedSeats();
        createSeatMap();

        btnConfirmSeat.setOnClickListener(v -> {
            if (selectedSeatCode == null) {
                Toast.makeText(this, "Please select a seat", Toast.LENGTH_SHORT).show();
                return;
            }

            int userId = SharedPrefsManager.getUserId(this);
            Ticket ticket = new Ticket();
            ticket.setShowtimeId(showtimeId);
            ticket.setUserId(userId);
            ticket.setSeatCode(selectedSeatCode);
            ticket.setBookedAtMillis(System.currentTimeMillis());

            long ticketId = db.appDao().insertTicket(ticket);
            if (ticketId > 0) {
                Intent intent = new Intent(SeatSelectActivity.this, TicketResultActivity.class);
                intent.putExtra("TICKET_ID", (int) ticketId);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Booking failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadBookedSeats() {
        List<Ticket> tickets = db.appDao().getTicketsByShowtime(showtimeId);
        for (Ticket t : tickets) {
            bookedSeats.add(t.getSeatCode());
        }
    }

    private void createSeatMap() {
        int rows = 7;
        int cols = 8;
        glSeats.setRowCount(rows);
        glSeats.setColumnCount(cols);

        int seatSize = (int) (getResources().getDisplayMetrics().widthPixels / (cols + 2));

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                char rowChar = (char) ('A' + i);
                String seatCode = rowChar + String.valueOf(j + 1);

                TextView tvSeat = new TextView(this);
                tvSeat.setText(seatCode);
                tvSeat.setGravity(Gravity.CENTER);
                tvSeat.setTextSize(10);
                tvSeat.setTextColor(ContextCompat.getColor(this, R.color.white));
                
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = seatSize;
                params.height = seatSize;
                params.setMargins(8, 8, 8, 8);
                tvSeat.setLayoutParams(params);

                if (bookedSeats.contains(seatCode)) {
                    tvSeat.setBackgroundResource(R.drawable.seat_booked_bg);
                    tvSeat.setEnabled(false);
                } else {
                    tvSeat.setBackgroundResource(R.drawable.seat_available_bg);
                    tvSeat.setOnClickListener(v -> {
                        resetSeatSelection();
                        selectedSeatCode = seatCode;
                        tvSeat.setBackgroundResource(R.drawable.seat_selected_bg);
                        tvSeat.setTextColor(ContextCompat.getColor(this, R.color.black));
                        tvSelectedSeat.setText("Selected Seat: " + selectedSeatCode);
                    });
                }

                glSeats.addView(tvSeat);
            }
        }
    }

    private void resetSeatSelection() {
        for (int i = 0; i < glSeats.getChildCount(); i++) {
            TextView tv = (TextView) glSeats.getChildAt(i);
            String code = tv.getText().toString();
            if (!bookedSeats.contains(code)) {
                tv.setBackgroundResource(R.drawable.seat_available_bg);
                tv.setTextColor(ContextCompat.getColor(this, R.color.white));
            }
        }
    }
}
