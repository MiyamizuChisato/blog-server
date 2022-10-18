package fun.ciallo.blog.controller;

import fun.ciallo.blog.common.annotations.UseToken;
import fun.ciallo.blog.common.enums.Status;
import fun.ciallo.blog.common.result.BlogException;
import fun.ciallo.blog.dto.MessagePostDto;
import fun.ciallo.blog.entity.Message;
import fun.ciallo.blog.entity.User;
import fun.ciallo.blog.service.IMessageService;
import fun.ciallo.blog.utils.AssertUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/message")
public class MessageController {
    @Resource
    private IMessageService messageService;
    @Resource
    private AssertUtils assertUtils;

    @GetMapping("/page/{current}")
    public Page<Message> getByPage(@PathVariable @Validated @NotNull int current) {
        if (current < 0) {
            current = 0;
        }
        Sort sort = Sort.by("createTime").descending();
        PageRequest pageRequest = PageRequest.of(current, 5, sort);
        return messageService.getByPage(pageRequest);
    }

    @GetMapping("/sub/id/{id}")
    public List<Message> getSubComments(@PathVariable Long id) {
        assertUtils.isTrue(id > 0, new BlogException(Status.PATH_ERROR));
        return messageService.getSubMessages(id);
    }

    @UseToken
    @PostMapping("/post")
    public void postMessage(@RequestBody MessagePostDto dto, HttpServletRequest request) {
        Long id = (Long) request.getAttribute("id");
        dto.setUserId(id);
        Message message = new Message();
        message.setContent(dto.getContent());
        User user = new User();
        user.setId(dto.getUserId());
        message.setUser(user);
        message.setTarget(dto.getTarget());
        if (null == dto.getTarget()) {
            messageService.addMessage(message);
        } else {
            messageService.addSubMessage(message);
        }


    }
}
