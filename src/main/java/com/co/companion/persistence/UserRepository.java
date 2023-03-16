package com.co.companion.persistence;

import com.co.companion.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends JpaRepository<UserEntity, String> {
    Boolean existsByNicknameIgnoreCase(String nick);

}
