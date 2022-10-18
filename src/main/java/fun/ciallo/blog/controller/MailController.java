package fun.ciallo.blog.controller;

import fun.ciallo.blog.common.annotations.Regexp;
import fun.ciallo.blog.common.enums.Status;
import fun.ciallo.blog.common.result.BlogException;
import fun.ciallo.blog.config.RabbitConfig;
import fun.ciallo.blog.pojo.Mail;
import fun.ciallo.blog.service.IAccountService;
import fun.ciallo.blog.utils.AssertUtils;
import cn.hutool.captcha.generator.RandomGenerator;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.Pattern;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Validated
@RestController
@RequestMapping("/api/mail")
public class MailController {
    @Resource
    private AssertUtils assertUtils;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private IAccountService accountService;

    @GetMapping("/verify/{email}")
    public void registerEmailVerify(@PathVariable @Pattern(regexp = Regexp.EMAIL) String email) {
        assertUtils.notTrue(accountService.exitsByEmail(email), new BlogException(Status.USER_ERROR));
        String code = new RandomGenerator("0123456789", 6).generate();
        redisTemplate.opsForValue().set("verify::" + email, code, 30, TimeUnit.MINUTES);
        Mail mail = new Mail();
        mail.setTaker(email);
        mail.setTitle("新用户注册");
        Map<String, Object> data = new HashMap<>();
        data.put("code", code);
        mail.setParams(data);
        rabbitTemplate.convertAndSend(
                RabbitConfig.MAIL_EXCHANGE_NAME,
                RabbitConfig.MAIL_VERIFY_ROUTING_KEY_NAME,
                mail
        );
    }
}
