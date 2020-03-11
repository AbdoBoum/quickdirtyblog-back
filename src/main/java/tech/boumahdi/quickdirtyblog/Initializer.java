package tech.boumahdi.quickdirtyblog;

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

    private final BlogRepository blogRepository;

    public Initializer(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Author abdo = new Author("Abderrahim");
        List<Blog> blogs =  Stream.of("Java streams", "Aws Labmda", "Methode reference with java", "Java tutorial")
                .map(title -> new Blog(title))
                .collect(Collectors.toList());
        String tags = "Java, Collections, Serverless";
        blogs.forEach(blog -> {
            blog.setAuthor(abdo);
            blog.setTags(tags);
            blog.setDate(Calendar.getInstance().getTime());
        });
        blogRepository.saveAll(blogs);
        blogRepository.findByTitleContainingIgnoreCase("java").forEach(System.out::println);
    }
}
