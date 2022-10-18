package fun.ciallo.blog.service.impl;

import fun.ciallo.blog.repositories.AccountRepository;
import fun.ciallo.blog.service.IAccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class AccountServiceImpl implements IAccountService {
    @Resource
    private AccountRepository accountRepository;

    @Override
    public boolean exitsByEmail(String email) {
        return accountRepository.existsByEmailIgnoreCase(email);
    }
}
