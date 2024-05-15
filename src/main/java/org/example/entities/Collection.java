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
public class Collection implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @Enumerated(EnumType.STRING)
    private CollectionType type;
    @Enumerated(EnumType.STRING)
    private Visibility visibility = Visibility.Public;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdAt;
    private String coverImage;
    private int views;

    @ManyToOne
    private User owner;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<CollectionItem> items;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Comment> comments;
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "favourites")
    private Set<User> likers;

    public Collection(String name, CollectionType type) {
        this.name = name;
        this.type = type;
    }

}
