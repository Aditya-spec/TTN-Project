package com.Bootcamp.Project.Application.services;

import com.Bootcamp.Project.Application.entities.*;

import com.Bootcamp.Project.Application.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public static final int MAX_FAILED_ATTEMPTS = 3;

    private static final long LOCK_TIME_DURATION = 24 * 60 * 60 * 1000; // 24 hours

    @Autowired
    private UserRepository repo;

    public User getEmail(String email) {
        return getEmail(email);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void increaseFailedAttempts(User user) {
        int newFailAttempts = user.getFailedAttempt() + 1;
        repo.updateFailedAttempts(newFailAttempts, user.getEmail());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void resetFailedAttempts(String email) {
        repo.updateFailedAttempts(0, email);
    }

    public void lock(User user) {
        user.setAccountNonLocked(false);
        user.setLockTime(new Date());

        repo.save(user);
    }

   // @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean unlockWhenTimeExpired(User user) {
        long lockTimeInMillis = user.getLockTime().getTime();
        long currentTimeInMillis = System.currentTimeMillis();

        if (lockTimeInMillis + LOCK_TIME_DURATION < currentTimeInMillis) {
            user.setAccountNonLocked(true);
            user.setLockTime(null);
            user.setFailedAttempt(0);

            repo.save(user);

            return true;
        }

        return false;
    }
}


