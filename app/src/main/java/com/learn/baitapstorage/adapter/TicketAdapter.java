package com.learn.baitapstorage.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.learn.baitapstorage.R;
import com.learn.baitapstorage.dal.DAO;
import com.learn.baitapstorage.models.Movie;
import com.learn.baitapstorage.models.Showtime;
import com.learn.baitapstorage.models.Theater;
import com.learn.baitapstorage.models.Ticket;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {
    private List<Ticket> tickets;
    private DAO dao;

    public TicketAdapter(List<Ticket> tickets, DAO dao) {
        this.tickets = tickets;
        this.dao = dao;
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket, parent, false);
        return new TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        Ticket ticket = tickets.get(position);
        Showtime showtime = dao.getShowtimeById(ticket.getShowtimeId());
        
        if (showtime != null) {
            Movie movie = dao.getMovieById(showtime.getMovieId());
            Theater theater = dao.getTheaterById(showtime.getTheaterId());

            if (movie != null) holder.tvMovieTitle.setText(movie.getTitle());
            if (theater != null) holder.tvTheaterName.setText(theater.getName());
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
            holder.tvDateTime.setText(sdf.format(new Date(showtime.getStartTimeMillis())));
            holder.tvPrice.setText(String.format(Locale.getDefault(), "%,.0f VND", showtime.getPrice()));
        }
        
        holder.tvSeat.setText("Seat: " + ticket.getSeatCode());
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    public static class TicketViewHolder extends RecyclerView.ViewHolder {
        TextView tvMovieTitle, tvTheaterName, tvDateTime, tvSeat, tvPrice;

        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMovieTitle = itemView.findViewById(R.id.tvMovieTitle);
            tvTheaterName = itemView.findViewById(R.id.tvTheaterName);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
            tvSeat = itemView.findViewById(R.id.tvSeat);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
    }
}
