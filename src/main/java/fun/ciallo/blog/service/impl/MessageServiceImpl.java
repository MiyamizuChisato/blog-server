package fun.ciallo.blog.service.impl;

import fun.ciallo.blog.common.enums.Status;
import fun.ciallo.blog.common.result.BlogException;
import fun.ciallo.blog.entity.Comment;
import fun.ciallo.blog.entity.Message;
import fun.ciallo.blog.repositories.MessageRepository;
import fun.ciallo.blog.service.IMessageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements IMessageService {
    @Resource
    private MessageRepository messageRepository;

    @Override
    public Page<Message> getByPage(PageRequest pageRequest) {
        Specification<Message> messageSpecification = new Specification<>() {
            @Override
            public Predicate toPredicate(Root<Message> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                predicate.getExpressions().add(criteriaBuilder.isNull(root.get("target")));
                return predicate;
            }
        };
        return messageRepository.findAll(messageSpecification, pageRequest);
    }

    @Override
    public List<Message> getSubMessages(Long id) {
        return messageRepository.findMessagesByTarget(id);
    }

    @Override
    public Message getById(Long id) {
        Optional<Message> optional = messageRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new BlogException(Status.PATH_ERROR);
    }

    @Override
    public void addMessage(Message message) {
        messageRepository.save(message);
    }

    @Override
    public void addSubMessage(Message message) {
        Message supMessage = getById(message.getTarget());
        supMessage.setChildren(supMessage.getChildren() + 1);
        addMessage(message);
    }
}
