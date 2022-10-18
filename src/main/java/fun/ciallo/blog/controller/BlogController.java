package fun.ciallo.blog.controller;

import fun.ciallo.blog.entity.User;
import fun.ciallo.blog.service.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/blog")
public class BlogController {
    @Resource
    private IUserService userService;

    @GetMapping("/master")
    public User getMaster() {
        return userService.getMaster();
    }
}
