package org.example.ra.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "showtimes")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Showtime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;
    @ManyToOne
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;
    private LocalDateTime startTime;
    private BigDecimal price;
    @Column(columnDefinition = "INT CHECK (total_seat > 0)")
    private int totalSeat;
    @Column(columnDefinition = "INT CHECK (seatBooked <= totalSeat)")
    private int seatBooked = 0;
    @OneToMany(mappedBy = "showtime", cascade = CascadeType.ALL)
    private List<Booking> bookingList;
}
