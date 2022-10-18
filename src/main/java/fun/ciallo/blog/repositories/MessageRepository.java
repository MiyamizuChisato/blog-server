package fun.ciallo.blog.repositories;

import fun.ciallo.blog.entity.Message;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long>, JpaSpecificationExecutor<Message> {
    List<Message> findMessagesByTarget(Long id);
}
