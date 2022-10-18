package fun.ciallo.blog.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Slf4j
@Configuration
public class RabbitConfig {
    public static final String MAIL_VERIFY_QUEUE_NAME = "mail.verify.queue";
    public static final String MAIL_RESUME_QUEUE_NAME = "mail.resume.queue";
    public static final String MAIL_RESUME_PASS_QUEUE_NAME = "mail.resume.pass.queue";
    public static final String MAIL_VERIFY_ROUTING_KEY_NAME = "mail.verify.routing.key";
    public static final String MAIL_RESUME_ROUTING_KEY_NAME = "mail.resume.routing.key";
    public static final String MAIL_RESUME_PASS_ROUTING_KEY_NAME = "mail.resume.pass.routing.key";
    public static final String MAIL_EXCHANGE_NAME = "mail.exchange";

    @Resource
    private CachingConnectionFactory cachingConnectionFactory;

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        rabbitTemplate.setConfirmCallback((data, ack, cause) -> {
            if (ack) {
                log.info("消息发送成功");
            } else {
                log.info("消息发送失败，{}，cause：{}", data, cause);
            }
        });
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnsCallback((r) -> {
            log.info("消息路由到队列失败 exchange:{},route:{},code:{},text:{},message:{}",
                    r.getExchange(), r.getRoutingKey(), r.getReplyCode(), r.getReplyText(), r.getMessage());
        });
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue mailVerifyQueue() {
        return new Queue(MAIL_VERIFY_QUEUE_NAME, true);
    }

    @Bean
    public Queue mailResumeQueue() {
        return new Queue(MAIL_RESUME_QUEUE_NAME, true);
    }

    @Bean
    public Queue mailResumePassQueue() {
        return new Queue(MAIL_RESUME_PASS_QUEUE_NAME, true);
    }

    @Bean
    public DirectExchange mailExchange() {
        return new DirectExchange(MAIL_EXCHANGE_NAME, true, false);
    }

    @Bean
    public Binding mailVerifyBinding() {
        return BindingBuilder.bind(mailVerifyQueue()).to(mailExchange()).with(MAIL_VERIFY_ROUTING_KEY_NAME);
    }

    @Bean
    public Binding mailResumeBinding() {
        return BindingBuilder.bind(mailResumeQueue()).to(mailExchange()).with(MAIL_RESUME_ROUTING_KEY_NAME);
    }

    @Bean
    public Binding mailResumePassBinding() {
        return BindingBuilder.bind(mailResumePassQueue()).to(mailExchange()).with(MAIL_RESUME_PASS_ROUTING_KEY_NAME);
    }
}
