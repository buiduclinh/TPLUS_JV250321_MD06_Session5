package org.example.ra.repo;

import org.example.ra.model.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;


@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT m.id,m.title, SUM(s.price*s.seatBooked) AS total FROM Movie m INNER JOIN Showtime s ON m.id = s.movie.id INNER JOIN Booking b ON b.showtime.id = s.id  WHERE b.status='BOOKED' AND b.bookingTime BETWEEN :startDate AND :endDate GROUP BY m.id,m.title  ")
    Page<Object[]> findAllMoviesRevenue(Pageable pageable, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    @Query("SELECT c.id,c.name, SUM(s.price*s.seatBooked) AS total FROM Cinema c INNER JOIN c.showtimeList s INNER JOIN s.bookingList b WHERE b.status='BOOKED' AND b.bookingTime BETWEEN :startDate AND :endDate GROUP BY c.id,c.name  ")
    Page<Object[]> findAllCinemasRevenue(Pageable pageable, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
