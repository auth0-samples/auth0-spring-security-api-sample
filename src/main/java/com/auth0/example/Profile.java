package com.auth0.example;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Profile {

    private Long id;

    @NotNull(message = "Name is required")
    @Size(min = 3, max = 15)
    private String name;

    @NotNull(message = "Email is required")
    @Email(message = "Must be valid email")
    private String email;

    public Profile() {}

    public Profile(final Long id, final String name, final String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
