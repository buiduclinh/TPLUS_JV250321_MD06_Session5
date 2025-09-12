package org.example.ra.model;

import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "booking")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "showtime_id")
    private Showtime showtime;
    @Column(columnDefinition = "INT CHECK(seats > 0)")
    private int seats;
    private LocalDateTime bookingTime = LocalDateTime.now();
    @Enumerated(EnumType.STRING)
    private Status status = Status.BOOKED;
}
