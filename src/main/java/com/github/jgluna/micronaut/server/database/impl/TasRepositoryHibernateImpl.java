package com.github.jgluna.micronaut.server.database.impl;

import com.github.jgluna.micronaut.server.conf.ApplicationConfigurationProperties;
import com.github.jgluna.micronaut.server.database.TaskRepository;
import com.github.jgluna.micronaut.server.domain.SortingAndOrderArguments;
import com.github.jgluna.micronaut.server.domain.Task;
import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.spring.tx.annotation.Transactional;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Singleton
public class TasRepositoryHibernateImpl implements TaskRepository {

    @PersistenceContext
    private EntityManager entityManager;
    private final ApplicationConfigurationProperties applicationConfiguration;

    public TasRepositoryHibernateImpl(@CurrentSession EntityManager entityManager,
                               ApplicationConfigurationProperties applicationConfiguration) {
        this.entityManager = entityManager;
        this.applicationConfiguration = applicationConfiguration;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Task> findById(@NotNull Long id) {
        return Optional.ofNullable(entityManager.find(Task.class, id));
    }

    @Override
    @Transactional
    public Task save(@NotBlank String name) {
        Task task = new Task(name);
        entityManager.persist(task);
        return task;
    }

    @Override
    @Transactional
    public void deleteById(@NotNull Long id) {
        findById(id).ifPresent(genre -> entityManager.remove(genre));
    }

    private final static List<String> VALID_PROPERTY_NAMES = Arrays.asList("id", "name");

    @Override
    @Transactional(readOnly = true)
    public List<Task> findAll(@NotNull SortingAndOrderArguments args) {
        String qlString = "SELECT t FROM Task as t";
        if (args.getOrder().isPresent() && args.getSort().isPresent() && VALID_PROPERTY_NAMES.contains(args.getSort().get())) {
            qlString += " ORDER BY t." + args.getSort().get() + " " + args.getOrder().get().toLowerCase();
        }
        TypedQuery<Task> query = entityManager.createQuery(qlString, Task.class);
        query.setMaxResults(args.getMax().orElseGet(applicationConfiguration::getMax));
        args.getOffset().ifPresent(query::setFirstResult);

        return query.getResultList();
    }

    @Override
    public int update(@NotNull Long id, @NotBlank String name) {
        return entityManager.createQuery("UPDATE Task s SET name = :name where id = :id")
                .setParameter("name", name)
                .setParameter("id", id)
                .executeUpdate();
    }
}
