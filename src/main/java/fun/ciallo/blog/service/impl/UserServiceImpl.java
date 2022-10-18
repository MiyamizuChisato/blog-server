package fun.ciallo.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import fun.ciallo.blog.common.enums.Status;
import fun.ciallo.blog.common.result.BlogException;
import fun.ciallo.blog.entity.Account;
import fun.ciallo.blog.entity.User;
import fun.ciallo.blog.repositories.UserRepository;
import fun.ciallo.blog.service.IUserService;
import fun.ciallo.blog.utils.AssertUtils;
import fun.ciallo.blog.utils.OssUtils;
import fun.ciallo.blog.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class UserServiceImpl implements IUserService {
    @Resource
    private UserRepository userRepository;

    @Resource
    private AssertUtils assertUtils;

    @Resource
    private UserUtils userUtils;

    @Resource
    private OssUtils ossUtils;

    @Override
    public String login(Account account) {
        User user = userRepository.findUserByIgnoreCaseAccountEmail(account.getEmail());
        assertUtils.notNull(user, new BlogException(Status.USER_ERROR));
        assertUtils.equals(account.getPassword(), user.getAccount().getPassword(), new BlogException(Status.USER_ERROR));
        return userUtils.tokenGenerator(user);
    }

    @Override
    public String register(User user) {
        userRepository.save(user);
        User login = getById(user.getId());
        return userUtils.tokenGenerator(login);
    }

    @Override
    public User getById(Long id) {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new BlogException(Status.PATH_ERROR);
    }

    @Override
    public Map<String, Object> update(User temp) {
        Optional<User> optional = userRepository.findById(temp.getId());
        if (optional.isPresent()) {
            User user = optional.get();
            BeanUtil.copyProperties(temp, user, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
            String token = userUtils.tokenGenerator(user);
            Map<String, Object> map = new HashMap<>(4);
            map.put("token", token);
            map.put("user", user);
            return map;
        }
        throw new BlogException(Status.PATH_ERROR);
    }

    @Override
    public Map<String, Object> update(Long id, MultipartFile avatar) throws IOException {
        User user = getById(id);
        if (!"avatar/default.png".equals(user.getAvatar())) {
            ossUtils.delete(user.getAvatar());
        }
        user.setAvatar(ossUtils.uploadAvatar(avatar));
        String token = userUtils.tokenGenerator(user);
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("token", token);
        return map;
    }

    @Override
    public User getMaster() {
        return userRepository.findByIdentityId(1L);
    }
}
