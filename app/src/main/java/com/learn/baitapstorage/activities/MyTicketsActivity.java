package com.learn.baitapstorage.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.learn.baitapstorage.R;
import com.learn.baitapstorage.adapter.TicketAdapter;
import com.learn.baitapstorage.dal.AppDatabase;
import com.learn.baitapstorage.dal.SharedPrefsManager;
import com.learn.baitapstorage.models.Ticket;

import java.util.List;

public class MyTicketsActivity extends AppCompatActivity {
    private RecyclerView rvMyTickets;
    private TicketAdapter adapter;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tickets);

        rvMyTickets = findViewById(R.id.rvMyTickets);
        db = AppDatabase.getInstance(this);

        int userId = SharedPrefsManager.getUserId(this);
        List<Ticket> ticketList = db.appDao().getTicketsByUser(userId);

        adapter = new TicketAdapter(ticketList, db.appDao());
        rvMyTickets.setLayoutManager(new LinearLayoutManager(this));
        rvMyTickets.setAdapter(adapter);
    }
}
