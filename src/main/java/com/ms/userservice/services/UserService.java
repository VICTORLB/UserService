package com.ms.userservice.services;

import com.ms.userservice.model.UserEntity;
import com.ms.userservice.producer.UserProducer;
import com.ms.userservice.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    final UserRepository userRepository;

    final UserProducer userProducer;

    public UserService(UserRepository userRepository, UserProducer userProducer) {
        this.userRepository = userRepository;
        this.userProducer = userProducer;
    }

    @Transactional
    public UserEntity saveUser(UserEntity userEntity){
        userRepository.save(userEntity);
        userProducer.publishMessageEmail(userEntity);
        return userEntity;
    }
}
