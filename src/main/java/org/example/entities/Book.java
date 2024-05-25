package org.example.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.nonNull;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString(exclude = "collectionItems")
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
    @ElementCollection(targetClass = Genre.class)
    @CollectionTable
    @Enumerated(EnumType.STRING)
    private Set<Genre> genres;
    @Temporal (TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date publishedAt;
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date addedAt;
    @Column(columnDefinition = "TEXT")
    private String description;
    private int weight;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "book")
    private Set<CollectionItem> collectionItems = new HashSet<>();

    public Book(String title, String author, String language, String coverImage, Set<Genre> genres, Date publishedAt, String description, int weight) {
        this.title = title;
        this.author = author;
        this.language = language;
        this.coverImage = coverImage;
        this.genres = genres;
        this.publishedAt = publishedAt;
        this.description = description;
        this.weight = weight;
    }
    private List<Short> getRatings() {
        return getCollectionItems().stream().map(CollectionItem::getRating).filter(rating -> rating >0).toList();
    }
    public int nbRatings() {
        return getRatings().size();
    }
    public int getRating() {
        if (nbRatings() == 0) {
            return 0;
        }
        short sum = 0;
        for (Short rating : getRatings()) {
            sum += rating;
        }
        return sum / nbRatings();
    }
}