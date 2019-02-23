package org.igorski.example.repositories;

import org.igorski.example.model.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Igor Stojanovski.
 * Date: 3/14/2018
 * Time: 10:15 PM
 */
public interface UserRepository extends CrudRepository<User, Long> {
    Iterable<User> findUserById(Long id);
}
