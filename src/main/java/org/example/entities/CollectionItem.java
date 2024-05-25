package org.example.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class CollectionItem implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date addedAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date startedReadingAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date finishedReadingAt;
    private short readingProgress = 0;
    private short rating = 0;

    @ManyToOne(cascade = CascadeType.ALL)
    private Book book;
    @ManyToOne(cascade = CascadeType.ALL)
    private User creator;

}
