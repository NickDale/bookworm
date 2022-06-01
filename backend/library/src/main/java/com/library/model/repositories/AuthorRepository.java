package com.library.model.repositories;

import com.library.model.entities.Author;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

public interface AuthorRepository extends AbstractJpaRepository<Author> {

    Optional<Author> findAuthorByNameIgnoreCase(@NotBlank String name);
}
