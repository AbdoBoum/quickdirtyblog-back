package tech.boumahdi.quickdirtyblog.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import tech.boumahdi.quickdirtyblog.model.Author;
import tech.boumahdi.quickdirtyblog.model.Blog;
import tech.boumahdi.quickdirtyblog.repository.AuthorRepository;
import tech.boumahdi.quickdirtyblog.repository.BlogRepository;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class BlogController {
    private final Logger log = LoggerFactory.getLogger(BlogController.class);
    private BlogRepository blogRepository;
    private AuthorRepository authorRepository;

    public BlogController(BlogRepository blogRepository, AuthorRepository authorRepository) {
        this.blogRepository = blogRepository;
        this.authorRepository = authorRepository;
    }

    @GetMapping("/blogs")
    Page<Blog> getBlogs(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @GetMapping("/blogs/published")
    Page<Blog> getPublishedBlogs(Pageable pageable) {
        return blogRepository.findAllByDraftFalse(pageable);
    }

    @GetMapping("/blog/{id}")
    ResponseEntity<?> getBlog(@PathVariable Long id) {
        Optional<Blog> blog = blogRepository.findById(id);
        return blog.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/blog")
    ResponseEntity<Blog> createBlog(@Valid @RequestBody Blog blog, @AuthenticationPrincipal OAuth2User principal)
            throws URISyntaxException {
        log.info("Request to create blog: {}", blog);
        Map<String, Object> details = principal.getAttributes();
        String authorName = details.get("family_name").toString();
        // check to see if user already exists
        Optional<Author> author = authorRepository.findAuthorByNameContainsIgnoreCase(authorName);
        blog.setAuthor(author.orElse(new Author(authorName)));
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
