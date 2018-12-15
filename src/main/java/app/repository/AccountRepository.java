package app.repository;

import app.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
public interface AccountRepository extends CrudRepository<Account, Long> {
    Account findByEmail(String email);
}