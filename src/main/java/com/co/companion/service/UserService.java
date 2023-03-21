package com.co.companion.service;

import com.co.companion.dto.UserDTO;
import com.co.companion.model.UserEntity;
import com.co.companion.persistence.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public UserEntity create(final UserEntity  entity) {
        if(entity == null || entity.getId() == null) {
            throw new RuntimeException("UserEntity 없음");
        }
        final String id = entity.getId();
        if(repository.existsById(id)) {
            throw new RuntimeException("존재하는 아이디입니다. 확인해 주세요.");
        }
        return repository.save(entity);
    }

    public Optional<UserEntity> getByCredentials(final String id, final String password, final PasswordEncoder encoder) {
        final Optional<UserEntity> user = repository.findById(id);
        if(user.isPresent()) {
            if(encoder.matches(password, user.get().getPassword())) return user;
        }
        return null;
    }
    public Boolean nickDupCheck(final String nickname) {
        return  repository.existsByNicknameIgnoreCase(nickname);
    }
}
