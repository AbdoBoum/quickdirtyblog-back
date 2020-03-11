package tech.boumahdi.quickdirtyblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import tech.boumahdi.quickdirtyblog.model.Blog;

import java.util.List;

@RepositoryRestResource
public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findByTitleContainingIgnoreCase(String title);
}
