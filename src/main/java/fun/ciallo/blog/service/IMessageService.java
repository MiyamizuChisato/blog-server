package fun.ciallo.blog.service;

import fun.ciallo.blog.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IMessageService {
    Page<Message> getByPage(PageRequest pageRequest);

    List<Message> getSubMessages(Long id);

    Message getById(Long id);

    void addMessage(Message message);

    void addSubMessage(Message message);
}
