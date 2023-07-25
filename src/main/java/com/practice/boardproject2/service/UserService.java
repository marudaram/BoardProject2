package com.practice.boardproject2.service;

import com.practice.boardproject2.dto.UserRequestDTO;
import com.practice.boardproject2.dto.UserResponseDTO;
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
    public Boolean logIn(UserRequestDTO userRequestDTO) {



        //디비에서 사용자 정보 가져오기
        Optional<User> findId = userRepository.findById(userRequestDTO.getId());
        Optional<User> findPw = userRepository.findByPassword(userRequestDTO.getPassword());

        //사용자 정보가 없을 경우 실패 처리
        if(!findId.get().getId().equals(userRequestDTO.getId())) {
            System.out.println("아이디가 다름");
            return false;
        } else if(!findPw.get().getPassword().equals(userRequestDTO.getPassword())) {
            System.out.println("비밀번호가 다름");
            return false;
        } else {
            return true;
        }


    }


    //회원 가입
    @Transactional
    public UserResponseDTO join(UserRequestDTO userRequestDTO) {
        User user = userRepository.save(toEntity(userRequestDTO));
        return toDto(user);
    }


    private UserResponseDTO toDto(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .password(user.getPassword())
                .name(user.getName())
                .build();
    }

    private User toEntity(UserRequestDTO dto) {
        return User.builder()
                .id(dto.getId())
                .password(dto.getPassword())
                .name(dto.getName())
                .build();
    }




}
