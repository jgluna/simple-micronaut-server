package com.github.jgluna.micronaut.server.domain;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "genre")
public class Task {

    public Task() {}

    public Task(@NotNull String name) {
        this.name = name;
    }

    @NotBlank
    @Column(name = "name", nullable = false, unique = false)
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
