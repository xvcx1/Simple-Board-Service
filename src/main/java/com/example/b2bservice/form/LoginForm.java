package com.example.b2bservice.form;

import lombok.Data;
import javax.validation.constraints.NotEmpty;

@Data
public class LoginForm {

    @NotEmpty(message = "아이디를 입력하세요.")
    private String id;

    @NotEmpty(message = "패스워드를 입력하세요.")
    private String password;

}
