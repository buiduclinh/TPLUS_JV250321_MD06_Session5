package org.example.ra.repo;

import org.example.ra.model.Showtime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime,Long> {
    Page<Showtime> findShowtimeByMovie_IdOrderByStartTime(Long movieId, Pageable pageable);
}
