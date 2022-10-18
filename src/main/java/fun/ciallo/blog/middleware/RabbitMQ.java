package fun.ciallo.blog.middleware;

import fun.ciallo.blog.config.RabbitConfig;
import fun.ciallo.blog.pojo.Mail;
import fun.ciallo.blog.utils.MailUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.MessagingException;

@Slf4j
@Component
public class RabbitMQ {
    @Resource
    private MailUtils mailUtils;

    @RabbitListener(queues = RabbitConfig.MAIL_VERIFY_QUEUE_NAME)
    public void registerEmailVerify(Mail mail) throws MessagingException {
        log.info("接收到验证码消息任务，向[{}]------>发送验证码", mail.getTaker());
        mailUtils.sendRegisterEmailVerifyCode(mail);
    }
}
