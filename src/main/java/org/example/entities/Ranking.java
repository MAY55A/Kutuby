package org.example.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;
import java.io.Serializable;


@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class Ranking implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int rank;
    private int points = 0;
    @Enumerated(EnumType.STRING)
    private RankingPeriod period;

    @ManyToOne
    private User user;

    public Ranking(RankingPeriod period) {
        this.period = period;
    }

}
