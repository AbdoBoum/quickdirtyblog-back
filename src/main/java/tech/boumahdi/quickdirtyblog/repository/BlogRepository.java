package tech.boumahdi.quickdirtyblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.boumahdi.quickdirtyblog.model.Blog;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findByTitleContainingIgnoreCase(String title);
    List<BlogRepository> findAllByAuthorId(String id);
}
