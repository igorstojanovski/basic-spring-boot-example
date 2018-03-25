package org.igorski.example.repositories;

import org.igorski.example.model.AppUser;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Igor Stojanovski.
 * Date: 3/14/2018
 * Time: 10:15 PM
 */
public interface AppUserRepository extends CrudRepository<AppUser, Long> {


    Iterable<AppUser> findAppUserById(Long id);
}
