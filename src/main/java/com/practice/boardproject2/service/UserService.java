package com.practice.boardproject2.service;

import com.practice.boardproject2.dto.UserDTO;
import com.practice.boardproject2.entity.User;
import com.practice.boardproject2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Boolean logIn(UserDTO userDTO) {

        //디비에서 사용자 정보 가져오기
        Optional<User> findUser = userRepository.findById(userDTO.getId());

        //사용자 정보가 없을 경우 실패 처리
        if(findUser == null || !findUser.get().getPassword().equals(userDTO.getPassword())) {
            System.out.println("비밀번호가 다름");
            return false;
        }

        return true;
    }

}
