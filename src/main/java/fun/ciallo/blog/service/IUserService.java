package fun.ciallo.blog.service;

import fun.ciallo.blog.entity.Account;
import fun.ciallo.blog.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface IUserService {
    String login(Account account);

    String register(User user);

    User getById(Long id);

    Map<String, Object> update(User temp);

    Map<String, Object> update(Long id, MultipartFile avatar) throws IOException;

    User getMaster();
}
