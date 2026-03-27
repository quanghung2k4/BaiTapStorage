package com.learn.baitapstorage.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.learn.baitapstorage.R;
import com.learn.baitapstorage.dal.DAO;
import com.learn.baitapstorage.models.Movie;
import com.learn.baitapstorage.models.Showtime;
import com.learn.baitapstorage.models.Theater;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ShowtimeAdapter extends RecyclerView.Adapter<ShowtimeAdapter.ShowtimeViewHolder> {
    private List<Showtime> showtimes;
    private DAO dao;
    private OnShowtimeClickListener listener;

    public interface OnShowtimeClickListener {
        void onShowtimeClick(Showtime showtime);
    }

    public ShowtimeAdapter(List<Showtime> showtimes, DAO dao, OnShowtimeClickListener listener) {
        this.showtimes = showtimes;
        this.dao = dao;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ShowtimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_showtime, parent, false);
        return new ShowtimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowtimeViewHolder holder, int position) {
        Showtime showtime = showtimes.get(position);
        
        Movie movie = dao.getMovieById(showtime.getMovieId());
        Theater theater = dao.getTheaterById(showtime.getTheaterId());

        if (movie != null) holder.tvMovieTitle.setText(movie.getTitle());
        if (theater != null) holder.tvTheaterName.setText(theater.getName());
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        holder.tvStartTime.setText(sdf.format(new Date(showtime.getStartTimeMillis())));
        holder.tvPrice.setText(String.format(Locale.getDefault(), "%,.0f VND", showtime.getPrice()));

        holder.btnBook.setOnClickListener(v -> listener.onShowtimeClick(showtime));
    }

    @Override
    public int getItemCount() {
        return showtimes.size();
    }

    public static class ShowtimeViewHolder extends RecyclerView.ViewHolder {
        TextView tvMovieTitle, tvTheaterName, tvStartTime, tvPrice;
        Button btnBook;

        public ShowtimeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMovieTitle = itemView.findViewById(R.id.tvMovieTitle);
            tvTheaterName = itemView.findViewById(R.id.tvTheaterName);
            tvStartTime = itemView.findViewById(R.id.tvStartTime);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            btnBook = itemView.findViewById(R.id.btnBookShowtime);
        }
    }
}
