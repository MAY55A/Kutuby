package org.example.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class Book implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String author;
    private String language;
    private String coverImage;
    private long isbn;
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Genre.class)
    private Set<Genre> genres;
    @Temporal (TemporalType.DATE)
    private Date publishedAt;
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date addedAt;
    @Column(columnDefinition = "TEXT")
    private String description;
    private int weight;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Comment> comments;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "book")
    private Set<CollectionItem> collectionItems;
}