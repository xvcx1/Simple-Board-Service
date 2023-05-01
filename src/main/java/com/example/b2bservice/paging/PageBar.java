package com.example.b2bservice.paging;

import lombok.Getter;

/*  변수 네이밍 최적화 (Criteria 클래스 포함)
nowPage // 현재 페이지

boardCntPerPage // 페이지당 출력할 board 개수

totalBoardCnt // 전체 board 개수

startPageNum // 해당 페이지에서의 첫번째 페이지 번호

endPageNum // 해당 페이지에서의 마지막 페이지 번호

prev // 이전 버튼

next // 다음 버튼

displayPageNumCnt // 페이지 번호 개수
*/

@Getter
public class PageBar {

    private PageStatus pageStatus; // 전달 될 페이지 상태 객체 변수
    private int totalBoardCnt; // 총 게시글 수
    private int startPageNum; // 화면에 보여질 첫번째 페이지 번호, 시작 페이지 번호
    // 화면에 보여질 마지막 페이지 번호, 끝 페이지 번호
    private int endPageNum; // (게시글 수를 고려한 마지막 페이지 번호인 tempEndPage로 치환될 예정. ex) 3페이지까지 게시글이 출력되면 5페이지가 endPageNum, 3페이지가 tempEndPage)
    private boolean prev; // 이전 버튼 유무
    private boolean next; // 다음 버튼 유무
    private int displayPageNumCnt = 5; // 화면에 보일 페이지 바 번호 링크의 개수

    public void setPageStatus(PageStatus pageStatus) {
        this.pageStatus = pageStatus;
    }

    public void setTotalBoardCnt(int totalBoardCnt) {
        this.totalBoardCnt = totalBoardCnt;
        calcData();
    }

    private void calcData() {
        // 현재 페이지에서 보이는 페이지 리스트 중 마지막 페이지의 번호를 구하는 공식 ( Math.ceil() : 올림 함수 )
        endPageNum = (int) (Math.ceil(pageStatus.getNowPage() / (double) displayPageNumCnt) * displayPageNumCnt);
        // 현재 페이지에서 보이는 페이지 리스트 중 첫 페이지의 번호를 구하는 공식
        startPageNum = (endPageNum - displayPageNumCnt) + 1;
        if(startPageNum <= 0) startPageNum = 1; // 첫 페이지가 음수가 되지 않도록 설정
        // 게시글 수를 고려해서 계산한 마지막 페이지 번호 (이 계산식이 없으면 무조건 최대 페이지 번호까지 보이게 됨.)
        int tempEndPage = (int) (Math.ceil(totalBoardCnt / (double) pageStatus.getBoardCntPerPage()));
        if (endPageNum > tempEndPage) {
            endPageNum = tempEndPage;
        }
        // prev 버튼 생성 여부 (시작 페이지 번호가 1이면 prev 버튼은 생성 X)
        prev = startPageNum == 1 ? false : true;
        // next 버튼 생성 여부 (끝 페이지 번호와 페이지당 게시글 수의 곱이 DB내의 전체 게시글 수보다 크면 생성 X)
        next = endPageNum * pageStatus.getBoardCntPerPage() < totalBoardCnt ? true : false;

    }

    public void setStartPageNum(int startPageNum) {
        this.startPageNum = startPageNum;
    }

    public void setEndPageNum(int endPageNum) {
        this.endPageNum = endPageNum;
    }
    public boolean isPrev() {
        return prev;
    }
    public void setPrev(boolean prev) {
        this.prev = prev;
    }
    public boolean isNext() {
        return next;
    }
    public void setNext(boolean next) {
        this.next = next;
    }

    public void setDisplayPageNumCnt(int displayPageNumCnt) {
        this.displayPageNumCnt = displayPageNumCnt;
    }

}
