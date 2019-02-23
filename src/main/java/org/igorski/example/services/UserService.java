package org.igorski.example.services;

import org.igorski.example.model.AppUser;
import org.igorski.example.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Igor Stojanovski.
 * Date: 3/14/2018
 * Time: 10:14 PM
 */
@Service
public class UserService {

    @Autowired
    private AppUserRepository appUserRepository;

    public AppUser createUser(AppUser user) {
        return appUserRepository.save(user);
    }

    public Iterable<AppUser> getAllUsers() {

        return appUserRepository.findAll();
    }

    public Iterable<AppUser> getUser(Long id) {

        return appUserRepository.findAppUserById(id);
    }
}
