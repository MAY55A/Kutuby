package org.example.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
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
    private Set<Collection> collections;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Comment> comments;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Ranking> rankings;
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Notification> notifications;
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Collection> favourites;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<AppRole> roles;

}
