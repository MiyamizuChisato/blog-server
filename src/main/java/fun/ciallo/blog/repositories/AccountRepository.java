package fun.ciallo.blog.repositories;

import fun.ciallo.blog.entity.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
    boolean existsByEmailIgnoreCase(String email);
}
