package com.practice.boardproject2.controller;

import com.practice.boardproject2.dto.UserDTO;
import com.practice.boardproject2.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/logIn")
    public String logIn(@RequestBody UserDTO userDTO, Model model) {
        if(userService.logIn(userDTO) == true) {
            //로그인 성공 처리
            //세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
//            HttpSession session = request.getSession();
//            session.setAttribute(session, session.getId());

            return "success";
        } else {
            String msg = "로그인 실패 / 아이디와 비밀번호를 확인하세요";
            model.addAttribute("msg", msg);
        }


        return "fail";

    }

}

