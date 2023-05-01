package com.example.b2bservice.form;

import lombok.Data;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

/*
 추후 UserSignUpForm으로 수정 할 예정
 기업이 서비스에 가입하고나서 직원등록 후 직원들에게 회원가입이 가능한 직원 코드를 부여
 */

@Data
public class SignupForm {

//    @NotEmpty(message = "직원코드를 입력하세요.")
//    private String eCode;

    @NotEmpty(message = "아이디를 입력하세요.")
    @Size(min = 5, max = 20, message = "5 ~ 16 길이로 입력하세요.")
    private String id;

    @NotEmpty(message = "패스워드를 입력하세요.")
    @Size(min = 8, max = 16, message = "8 ~ 16 길이로 입력하세요.")
    private String password;

    @NotEmpty(message = "패스워드 확인란을 입력하세요.")
    private String passwordConfirm;

    @NotEmpty(message = "이름을 입력하세요.")
    private String name;

    @NotEmpty(message = "생년월일을 입력하세요.")
    private String birth;

    @NotEmpty(message = "성별을 선택하세요.")
    private String gender;

    @NotEmpty(message = "주소를 입력하세요.")
    private String address;

    @NotEmpty(message = "핸드폰번호를 입력하세요.")
    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "010-xxxx-xxxx 형식으로 입력하세요.") // 정규식 패턴
    private String phoneNumber;

}
