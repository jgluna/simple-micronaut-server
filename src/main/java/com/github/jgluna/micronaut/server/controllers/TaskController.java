package com.github.jgluna.micronaut.server.controllers;

import com.github.jgluna.micronaut.server.database.TaskRepository;
import com.github.jgluna.micronaut.server.domain.SortingAndOrderArguments;
import com.github.jgluna.micronaut.server.domain.Task;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.validation.Validated;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Validated
@Controller("/tasks")
public class TaskController {

    protected final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Get("/{id}")
    public Task show(Long id) {
        return taskRepository
                .findById(id)
                .orElse(null);
    }

    @Get(value = "/list{?args*}")
    public List<Task> list(@Valid SortingAndOrderArguments args) {
        return taskRepository.findAll(args);
    }

    @Post("/")
    public HttpResponse<Task> save(@Body String tsk) {
        Task task = taskRepository.save(tsk);

        return HttpResponse
                .created(task)
                .headers(headers -> headers.location( URI.create("/tasks/" + task.getId())));
    }

    @Put("/{id}")
    public HttpResponse update(Long id,@Body String tsk) {
        int numberOfEntitiesUpdated = taskRepository.update(id, tsk);

        return HttpResponse
                .noContent()
                .header(HttpHeaders.LOCATION,  URI.create("/tasks/" + id).getPath());
    }

    @Delete("/{id}")
    public HttpResponse delete(Long id) {
        taskRepository.deleteById(id);
        return HttpResponse.noContent();
    }
}
