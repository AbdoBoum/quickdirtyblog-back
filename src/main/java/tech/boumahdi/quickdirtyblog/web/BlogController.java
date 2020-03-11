package tech.boumahdi.quickdirtyblog.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.boumahdi.quickdirtyblog.model.Blog;
import tech.boumahdi.quickdirtyblog.repository.BlogRepository;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class BlogController {
    private final Logger log = LoggerFactory.getLogger(BlogController.class);
    private BlogRepository blogRepository;

    public BlogController(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @GetMapping("/blogs")
    List<Blog> getBlogs() {
        return blogRepository.findAll();
    }

    @GetMapping("/blog/{id}")
    ResponseEntity<?> getGroup(@PathVariable Long id) {
        Optional<Blog> blog = blogRepository.findById(id);
        return blog.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/blog")
    ResponseEntity<Blog> createBlog(@Valid @RequestBody Blog blog) throws URISyntaxException {
        log.info("Request to create blog: {}", blog);
        Blog result = blogRepository.save(blog);
        return ResponseEntity.created(new URI("/api/blog/" + result.getId()))
                .body(result);
    }

    @PutMapping("/blog/{id}")
    ResponseEntity<Blog> updateBlog(@Valid @RequestBody Blog blog) {
        log.info("Request to update blog: {}", blog);
        Blog result = blogRepository.save(blog);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/blog/{id}")
    public ResponseEntity<?> deleteBlog(@PathVariable Long id) {
        log.info("Request to delete blog: {}", id);
        blogRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
