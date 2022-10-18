package fun.ciallo.blog.utils;

import fun.ciallo.blog.pojo.Mail;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class MailUtils {
    @Resource
    private JavaMailSender mailSender;

    @Resource
    private MailProperties mailProperties;

    @Resource
    private TemplateEngine templateEngine;

    public void sendRegisterEmailVerifyCode(Mail mail) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject(mail.getTitle());
        helper.setFrom(mailProperties.getUsername());
        helper.setTo(mail.getTaker());
        Context context = new Context();
        context.setVariable("code", mail.getParams().get("code"));
        String mailText = templateEngine.process("VerifyMail.html", context);
        helper.setText(mailText, true);
        mailSender.send(mimeMessage);
    }
}
