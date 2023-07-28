package com.practice.boardproject2.repository;

import com.practice.boardproject2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    //아이디와 비밀번호 확인
    Optional<User> findByIdAndPassword(String id, String password);
}
