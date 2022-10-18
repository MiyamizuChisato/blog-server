package fun.ciallo.blog.utils;

import fun.ciallo.blog.entity.User;
import cn.hutool.core.codec.Rot;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserUtils {
    @Value("${blog.secret}")
    private String SECRET;

    public static String passwordEncode(String password) {
        password = Rot.encode13(password);
        return SecureUtil.md5(password);
    }

    public String tokenGenerator(User user) {
        Map<String, Object> payload = new HashMap<>();
        DateTime now = DateTime.now();
        DateTime outTime = now.offsetNew(DateField.DAY_OF_WEEK, 1);
        payload.put(JWTPayload.ISSUED_AT, now);
        payload.put(JWTPayload.EXPIRES_AT, outTime);
        payload.put(JWTPayload.NOT_BEFORE, now);
        payload.put("id", user.getId());
        payload.put("avatar", user.getAvatar());
        payload.put("nickname", user.getNickname());
        return JWTUtil.createToken(payload, SECRET.getBytes(StandardCharsets.UTF_8));
    }

    public boolean tokenValidator(String token) {
        JWT jwt = JWTUtil.parseToken(token);
        return jwt.setKey(SECRET.getBytes(StandardCharsets.UTF_8)).verify() && jwt.validate(0);
    }

    public Object tokenParser(String token, String key) {
        JWT jwt = JWTUtil.parseToken(token);
        return jwt.getPayload(key);
    }
}
