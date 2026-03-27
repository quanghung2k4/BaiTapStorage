package com.learn.baitapstorage.dal;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.learn.baitapstorage.models.Movie;
import com.learn.baitapstorage.models.Showtime;
import com.learn.baitapstorage.models.Theater;
import com.learn.baitapstorage.models.Ticket;
import com.learn.baitapstorage.models.User;

import java.util.List;

@Dao
public interface DAO {
    // Users
    @Query("SELECT * FROM Users WHERE username = :username AND password = :password")
    User login(String username, String password);

    @Query("SELECT * FROM Users WHERE id = :userId LIMIT 1")
    User getUserById(int userId);

    @Insert
    void insertUser(User user);

    // Movies
    @Query("SELECT * FROM Movies")
    List<Movie> getAllMovies();

    @Query("SELECT * FROM Movies WHERE id = :movieId LIMIT 1")
    Movie getMovieById(int movieId);

    // Theaters
    @Query("SELECT * FROM Theaters")
    List<Theater> getAllTheaters();

    @Query("SELECT * FROM Theaters WHERE id = :theaterId LIMIT 1")
    Theater getTheaterById(int theaterId);

    // Showtimes
    @Query("SELECT * FROM Showtimes ORDER BY startTimeMillis")
    List<Showtime> getAllShowtimes();

    @Query("SELECT * FROM Showtimes WHERE movieId = :movieId ORDER BY startTimeMillis")
    List<Showtime> getShowtimesByMovie(int movieId);

    @Query("SELECT * FROM Showtimes WHERE theaterId = :theaterId ORDER BY startTimeMillis")
    List<Showtime> getShowtimesByTheater(int theaterId);

    @Query("SELECT * FROM Showtimes WHERE id = :showtimeId LIMIT 1")
    Showtime getShowtimeById(int showtimeId);

    // Tickets
    @Insert
    long insertTicket(Ticket ticket);

    @Query("SELECT * FROM Tickets WHERE id = :ticketId LIMIT 1")
    Ticket getTicketById(int ticketId);

    @Query("SELECT * FROM Tickets WHERE userId = :userId ORDER BY bookedAtMillis DESC")
    List<Ticket> getTicketsByUser(int userId);

    @Query("SELECT * FROM Tickets WHERE showtimeId = :showtimeId")
    List<Ticket> getTicketsByShowtime(int showtimeId);

    @Query("SELECT * FROM Tickets WHERE showtimeId = :showtimeId AND seatCode = :seatCode LIMIT 1")
    Ticket getTicketByShowtimeAndSeat(int showtimeId, String seatCode);
}
