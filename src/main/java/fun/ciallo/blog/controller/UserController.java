package fun.ciallo.blog.controller;

import fun.ciallo.blog.common.annotations.UseToken;
import fun.ciallo.blog.common.enums.Status;
import fun.ciallo.blog.common.result.BlogException;
import fun.ciallo.blog.dto.UserRegisterDto;
import fun.ciallo.blog.dto.UserUpdateDto;
import fun.ciallo.blog.entity.Account;
import fun.ciallo.blog.dto.UserLoginDto;
import fun.ciallo.blog.entity.User;
import fun.ciallo.blog.service.IUserService;
import fun.ciallo.blog.utils.AssertUtils;
import fun.ciallo.blog.utils.UserUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Resource
    private IUserService userService;
    @Resource
    private AssertUtils assertUtils;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @PostMapping("/login")
    public String login(@RequestBody UserLoginDto dto) {
        dto.setPassword(UserUtils.passwordEncode(dto.getPassword()));
        Account account = new Account();
        BeanUtils.copyProperties(dto, account);
        return userService.login(account);
    }

    @PostMapping("/register")
    public String register(@RequestBody UserRegisterDto dto) {
        String key = "verify::" + dto.getUserAccount().getEmail();
        String code = (String) redisTemplate.opsForValue().get(key);
        assertUtils.notNull(code, new BlogException(Status.USER_ERROR));
        assert code != null;
        assertUtils.equals(code, dto.getCode(), new BlogException(Status.USER_ERROR));
        Account account = new Account();
        BeanUtils.copyProperties(dto.getUserAccount(), account);
        account.setPassword(UserUtils.passwordEncode(account.getPassword()));
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        user.setAccount(account);
        String token = userService.register(user);
        redisTemplate.delete(key);
        return token;
    }

    @GetMapping("/id/{id}")
    public User getById(@PathVariable Long id) {
        assertUtils.isTrue(id > 0, new BlogException(Status.PATH_ERROR));
        return userService.getById(id);
    }

    @UseToken
    @PutMapping("/update/info")
    public Map<String, Object> update(@RequestBody UserUpdateDto dto, HttpServletRequest request) {
        Long id = (Long) request.getAttribute("id");
        System.out.println(dto.toString());
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        user.setId(id);
        return userService.update(user);
    }

    @UseToken
    @PutMapping("/update/avatar")
    public Map<String, Object> update(@RequestParam("avatar") MultipartFile avatar, HttpServletRequest request) throws IOException {
        Long id = (Long) request.getAttribute("id");
        return userService.update(id, avatar);
    }
}
