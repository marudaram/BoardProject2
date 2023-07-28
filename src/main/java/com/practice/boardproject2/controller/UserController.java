package com.practice.boardproject2.controller;

import com.practice.boardproject2.dto.UserRequestDTO;
import com.practice.boardproject2.dto.UserResponseDTO;
import com.practice.boardproject2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * @methodName: logIn
     * @Description: 로그인 성공 처리
     * @param userRequestDTO
     */
    @PostMapping("/logIn")
    public ResponseEntity<String> logIn(@RequestBody UserRequestDTO userRequestDTO) {
        if (userService.logIn(userRequestDTO) == true) {
            return new ResponseEntity<>("success", HttpStatus.OK);
        }
        return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
    }

    /**
     * @methodName: join
     * @Description: 회원가입
     * @param userRequestDTO
     */
    @PostMapping("/join")
    public ResponseEntity<UserResponseDTO> join(@RequestBody UserRequestDTO userRequestDTO) {
        return new ResponseEntity<>(userService.join(userRequestDTO), HttpStatus.OK);
    }


}

