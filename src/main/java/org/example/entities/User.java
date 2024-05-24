package org.example.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString(exclude = {"collections", "rankings"})
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
    private String profilePicture;
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdAt;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private Set<Collection> collections = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Comment> comments = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Ranking> rankings = new HashSet<>();
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Notification> notifications = new HashSet<>();
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Collection> favourites = new HashSet<>();
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<AppRole> roles = new HashSet<>();

    public User(String username, String email, String hash) {
        this.userName = username;
        this.email = email;
        this.passwordHash = hash;
    }

}
