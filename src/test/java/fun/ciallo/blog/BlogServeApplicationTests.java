package fun.ciallo.blog;

import fun.ciallo.blog.entity.Account;
import fun.ciallo.blog.entity.User;
import fun.ciallo.blog.repositories.UserRepository;
import fun.ciallo.blog.utils.OssUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class BlogServeApplicationTests {
    @Resource
    OssUtils ossUtils;

    @Test
    void contextLoads() {
        ossUtils.delete("article/test.md");
    }

}
