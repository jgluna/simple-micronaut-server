package com.github.jgluna.micronaut.server.database;

import com.github.jgluna.micronaut.server.domain.SortingAndOrderArguments;
import com.github.jgluna.micronaut.server.domain.Task;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    Optional<Task> findById(@NotNull Long id);

    Task save(@NotBlank String name);

    void deleteById(@NotNull Long id);

    List<Task> findAll(@NotNull SortingAndOrderArguments args);

    int update(@NotNull Long id, @NotBlank String name);
}
