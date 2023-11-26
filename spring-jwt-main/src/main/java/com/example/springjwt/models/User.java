package com.example.springjwt.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table (name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    private Integer coins;

    @ElementCollection (targetClass = Role.class, fetch = FetchType.EAGER)
    @JoinTable (
            name = "roles",
            joinColumns = @JoinColumn (name = "user_id")
    )
    @Enumerated (EnumType.STRING)
    private Set<Role> roles;

    public static Builder builder() {
        return new Builder();
    }
     public static class Builder {
        private User user = new User();

        public Builder username(String username) {
            user.setUsername(username);
            return this;
        }
        public Builder password(String password) {
            user.setPassword(password);
            return this;
        }
        public Builder roles(Set<Role> roles) {
            user.setRoles(roles);
            return this;
        }
        public Builder coins(Integer coins) {
            user.setCoins(coins);
            return this;
        }
        public User build() {
            return user;
        }
    }
}
