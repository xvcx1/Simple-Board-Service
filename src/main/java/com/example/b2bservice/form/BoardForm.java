package com.example.b2bservice.form;

import lombok.Data;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class BoardForm {

    @NotEmpty(message = "제목을 입력하세요.")
    @Size(max = 100, message = "제목은 100자 미만으로 작성하세요.")
    private String title;

    @NotEmpty(message = "내용을 입력하세요.")
    @Size(max = 1000, message = "내용은 1000자 미만으로 작성하세요.")
    private String content;

}
