package com.example.b2bservice.service;

import com.example.b2bservice.dto.User;
import com.example.b2bservice.form.SignupForm;
import com.example.b2bservice.repository.UserRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder; // for password encryption

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User login(String id, String password){ // null 반환 == 로그인 실패

        User user = userRepository.findById(id);

        if(user == null) return null; // 로그인 실패 (존재하지 않는 아이디)

        boolean isMatch = passwordEncoder.matches(password, user.getPassword()); // raw 패스워드와 DB에 저장된 암호화된 패스워드 일치 여부 확인

        if(isMatch){
            return user; // 로그인 성공
        }
        else{
            return null; // 로그인 실패 (패스워드 불일치)
        }

    }

    public User signup(SignupForm signupForm){ // 회원가입 (boolean타입 반환 고려...)

        if(userRepository.findById(signupForm.getId()) != null){
            return null; // 중복 아이디 존재
        }

        User user = new User(
                signupForm.getId(),
                passwordEncoder.encode(signupForm.getPassword()), // 패스워드 암호화
                signupForm.getName(),
                signupForm.getBirth(),
                signupForm.getGender(),
                signupForm.getAddress(),
                signupForm.getPhoneNumber()
        );

        userRepository.save(user);

        return user;
    }

}
