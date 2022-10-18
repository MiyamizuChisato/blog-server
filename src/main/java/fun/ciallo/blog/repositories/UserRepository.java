package fun.ciallo.blog.repositories;

import fun.ciallo.blog.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findUserByIgnoreCaseAccountEmail(String email);

    Long countByIdentityId(Long id);

    User findByIdentityId(Long id);
}
