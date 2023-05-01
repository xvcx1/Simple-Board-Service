package com.example.b2bservice.alert;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// alertPage.html로 alert메시지와 이후 링크를 전달하기 위해 data를 담을 클래스
@Getter
@Setter
@NoArgsConstructor
public class Message {
    String message = "";
    String href = "";

    public Message(String message, String href) {
        this.message = message;
        this.href = href;
    }
}
