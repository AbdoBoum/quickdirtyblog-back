package tech.boumahdi.quickdirtyblog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tech.boumahdi.quickdirtyblog.model.Author;
import tech.boumahdi.quickdirtyblog.model.Blog;
import tech.boumahdi.quickdirtyblog.repository.BlogRepository;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class Initializer implements CommandLineRunner {

    private final Logger log = LoggerFactory.getLogger(Initializer.class);
    private final BlogRepository blogRepository;

    public Initializer(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Author abdo = new Author("Abderrahim BOUMAHDI");
        List<Blog> blogs = Stream.of("Java streams", "Aws Labmda", "Methode reference with java", "Java tutorial",
                "And What About Code Quality?", "Design Pattern in Practice")
                .map(Blog::new)
                .collect(Collectors.toList());
        String tags = "Java, Collections, Aws";
        blogs.forEach(blog -> {
            blog.setAuthor(abdo);
            blog.setContent("<h2>this is a simple java</h2><p>this is also a simple texte</p>");
            blog.setTags(tags);
            blog.setDraft(false);
            blog.setDate(Calendar.getInstance().getTime());
        });

        blogs.get(0).setDraft(true);
        blogRepository.saveAll(blogs);
        blogRepository.findByTitleContainingIgnoreCase("java").forEach(blog -> log.info(blog.toString()));
    }
}
