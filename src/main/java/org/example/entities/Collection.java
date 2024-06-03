package org.example.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString(exclude = "owner")
@Entity
public class Collection implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @Enumerated(EnumType.STRING)
    private CollectionType type = CollectionType.Personnalised;
    @Enumerated(EnumType.STRING)
    private Visibility visibility = Visibility.Private;
    @Column(columnDefinition = "TEXT")
    private String description = "Explore a diverse collection of books curated just for you. From classic literature to contemporary fiction, there\\'s something for every reader in this collection.";
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdAt;
    private String coverImage = "defaultColl.jpg";
    private int views;

    @ManyToOne
    private User owner;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<CollectionItem> items = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "favorites")
    private Set<User> likers = new HashSet<>();

    public Collection(String name, CollectionType type, User owner, String coverImage, String desc) {
        this.name = name;
        this.type = type;
        this.owner = owner;
        this.coverImage = coverImage;
        this.description = desc;
    }
}
