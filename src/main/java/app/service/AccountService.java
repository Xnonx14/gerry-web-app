package app.service;

import app.model.Account;
import app.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("accountService")
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    public void saveAccount(Account account) {
        accountRepository.save(account);
    }
}
