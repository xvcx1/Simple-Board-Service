package com.example.b2bservice.paging;

import lombok.Getter;

@Getter
public class PageStatus {

    private int nowPage; // 현재 페이지 번호
    private int boardCntPerPage; // 한 페이지당 보여줄 게시글의 갯수

    public int getPageStart(){ // 현재 페이지의 게시글 시작 행 번호
        return (this.nowPage -1)* boardCntPerPage; // ex) 3페이지, 10개씩 글 출력 가정 >> (3-1)*10 = 20 (행은 0부터 시작)
    }

    public PageStatus(){
        this.nowPage = 1;
        this.boardCntPerPage = 10;
    }

    public void setNowPage(int nowPage){ // 페이지가 음수값이 되지 않게 설정
        if(nowPage <= 0){
            this.nowPage = 1;
        }
        else{
            this.nowPage = nowPage;
        }
    }

    public void setBoardCntPerPage(int pageCount){ // 페이지당 보여줄 게시글 수가 변하지 않도록 설정
        int cnt = this.boardCntPerPage;
        if(pageCount != cnt){
            this.boardCntPerPage = cnt;
        }
        else{
            this.boardCntPerPage = pageCount;
        }
    }

}
