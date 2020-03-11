package tech.boumahdi.quickdirtyblog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tech.boumahdi.quickdirtyblog.web.BlogController;

import java.time.Instant;
import java.util.Calendar;

@SpringBootApplication
public class QuickDirtyBlogApplication {

    private static final Logger log = LoggerFactory.getLogger(QuickDirtyBlogApplication.class);

    public static void main(String[] args) {
        log.info(Calendar.getInstance().getTime().toString());
        SpringApplication.run(QuickDirtyBlogApplication.class, args);
    }

}
