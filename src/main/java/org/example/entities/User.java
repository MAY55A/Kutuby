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
@ToString(exclude = {"collections", "rankings", "comments", "favorites"})
@Entity
@Builder
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String userName;
    private String passwordHash;
    private String email;
    private String profilePicture = "defaultUser.jpg";
    @Column(columnDefinition = "TEXT")
    private String description = "Hi, welcome to my account !";
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdAt;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<Collection> collections = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private Set<Comment> comments = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Ranking> rankings = new ArrayList<>();
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Notification> notifications = new ArrayList<>();
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Collection> favorites = new ArrayList<>();
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<AppRole> roles = new HashSet<>();

    public User(String username, String email, String hash) {
        this.userName = username;
        this.email = email;
        this.passwordHash = hash;
    }

}
