package org.example.ra.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cinemas")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Can't not blank Name!")
    private String name;
    @NotBlank(message = "Can't not blank Location")
    private String location;
    @Column(columnDefinition = "INT CHECK (capacity > 0)")
    private int capacity;
    @ManyToMany
    @JoinTable(
            name = "CM_id",
            joinColumns = @JoinColumn(name = "cinema_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    private List<Movie> movieList;

    @OneToMany(mappedBy = "cinema", cascade = CascadeType.ALL)
    private List<Showtime> showtimeList;
}
