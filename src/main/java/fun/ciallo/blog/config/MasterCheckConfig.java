package fun.ciallo.blog.config;

import fun.ciallo.blog.BlogServeApplication;
import fun.ciallo.blog.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Slf4j
@Component
public class MasterCheckConfig {
    @Resource
    private UserRepository userRepository;
    @Resource
    private ApplicationContext context;

    @PostConstruct
    public void init() {
        Long count = userRepository.countByIdentityId(1L);
        if (1L != count) {
            log.error("Can't find a master in your database");
            SpringApplication.exit(context);
        }
    }
}
