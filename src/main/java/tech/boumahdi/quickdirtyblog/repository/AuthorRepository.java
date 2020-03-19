package tech.boumahdi.quickdirtyblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.boumahdi.quickdirtyblog.model.Author;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, String> {
    Optional<Author> findAuthorByNameContainsIgnoreCase(String name);
}
