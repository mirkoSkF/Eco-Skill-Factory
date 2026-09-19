package it.skillfactory.eco.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
    name = "users",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_users_username",
            columnNames = "username"
        ),
        @UniqueConstraint(
            name = "uk_users_email",
            columnNames = "email"
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
        nullable = false,
        length = 100,
        unique = true
    )
    private String username;

    @Column(
        nullable = false,
        length = 150,
        unique = true
    )
    private String email;

    @Column(
        nullable = false
    )
    private String password;

    @Column(
        name = "first_name",
        length = 100
    )
    private String firstName;

    @Column(
        name = "last_name",
        length = 100
    )
    private String lastName;

    @Column(
        name = "avatar_url",
        length = 500
    )
    private String avatarUrl;

    @Column(
        nullable = false
    )
    private boolean enabled = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(
            name = "user_id"
        ),
        inverseJoinColumns = @JoinColumn(
            name = "role_id"
        )
    )
    @Builder.Default
    private Set<Role> roles = new HashSet<>();

    @Column(
        name = "created_at",
        nullable = false,
        updatable = false
    )
    private LocalDateTime createdAt;

    @Column(
        name = "updated_at"
    )
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {

        LocalDateTime now = LocalDateTime.now();

        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {

        updatedAt = LocalDateTime.now();
    }
}