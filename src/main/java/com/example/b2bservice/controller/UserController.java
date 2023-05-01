package com.example.b2bservice.controller;

import com.example.b2bservice.alert.Message;
import com.example.b2bservice.dto.User;
import com.example.b2bservice.form.LoginForm;
import com.example.b2bservice.form.SignupForm;
import com.example.b2bservice.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RequestMapping("/user")
@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String enterSignUpForm(Model model){ // 회원가입 폼 뷰 진입
        model.addAttribute("signupForm", new SignupForm()); // valid 처리를 위해 빈 폼 객체를 뷰에 먼저 전달해준다.
        return "user-signup";
    }

    @PostMapping("/signup")
    public ModelAndView signup(@Valid @ModelAttribute SignupForm signupForm, BindingResult bindingResult, ModelAndView mv){

        if(bindingResult.hasErrors()){ // validation 검증 (오류 존재시 자동으로 model에 담겨서 전달)
            mv.setViewName("user-signup"); // 해당 뷰로 이동
            return mv;
        }

        User user = userService.signup(signupForm);

        if(user == null){
            mv.addObject("data", new Message("이미 존재하는 아이디입니다.", "javascript:history.back()")); // 뒤로가기
            mv.setViewName("alert-page"); // 설정한 Data를 가지고 alertPage view로 이동
            return mv;
        }
        else{
            mv.addObject("data", new Message("회원가입이 완료되었습니다.", "/user/login"));
            mv.setViewName("alert-page"); // 설정한 Data를 가지고 alertPage view로 이동
            return mv;
        }

    }

    @GetMapping("/login")
    public String enterLoginForm(Model model){ // 로그인 폼 뷰 진입
        model.addAttribute("loginForm", new LoginForm()); // valid 처리를 위해 빈 폼 객체를 뷰에 먼저 전달해준다.
        return "user-login";
    }

    @PostMapping("/login")
    public ModelAndView login(@Valid @ModelAttribute LoginForm loginForm, BindingResult bindingResult, HttpSession session, ModelAndView mv){ // alert를 위해 ModelAndView 이용

        if(bindingResult.hasErrors()){ // validation 검증 (오류 존재시 자동으로 model에 담겨서 전달)
            mv.setViewName("user-login"); // 해당 뷰로 이동
            return mv;
            //return "/user-login.html";
        }

        User user = userService.login(loginForm.getId(), loginForm.getPassword()); // 해당 아이디, 패스워드를 가진 user 검색

        if(user == null){ // 해당 user가 존재하지 않음, 로그인 실패
            mv.addObject("data", new Message("아이디 또는 패스워드가 일치하지 않습니다.", "javascript:history.back()")); // 뒤로가기
            mv.setViewName("alert-page"); // 설정한 Data를 가지고 alertPage view로 이동
            return mv;
            //return "/user-login.html";

        }
        else{ // user 존재, 로그인 성공
            session.setAttribute("loginUser", user); // 세션에 로그인 정보 저장
            mv.setViewName("home"); // 홈 뷰로 이동
            return mv;
            //return "/home.html"; // 로그인 성공
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, Model model){ // 로그아웃
        session.setAttribute("loginUser", null); // 세션의 loginUser를 null로 세팅
        return enterLoginForm(model); // 로그인 폼 뷰 진입 메소드 불러오기 (validation 검증이 가능한 폼 뷰로 진입해야 함.)
    }

}
