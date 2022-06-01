package com.library.model.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.NonNull;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.List;

@NoRepositoryBean
public interface AbstractJpaRepository<T> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {

    EntityManager getEntityManager();

    <S> List<S> findAll(Class<S> returnType);

    <S> List<S> findAll(Class<S> returnType, Sort sort);

    <S> List<S> findAll(Class<S> returnType, String search, int page, int size, String[] sorts);

    <S> S saveEntity(S entity);

    <S, R extends Collection<S>> List<S> saveAllEntity(R entity);

    <S> S findById(Class<S> returnType, @NonNull Object id);

    <S> void deleteEntity(S entity);

}
