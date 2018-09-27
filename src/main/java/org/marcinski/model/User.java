package org.marcinski.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
    @NotEmpty
    private String name;
    private int active;

    @ManyToMany
    @JoinTable(
            name = "user_role"
            ,joinColumns = @JoinColumn(name = "user_id")
            ,inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
}

