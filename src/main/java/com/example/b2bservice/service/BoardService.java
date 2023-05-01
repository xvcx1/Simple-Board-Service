package com.example.b2bservice.service;

import com.example.b2bservice.dto.Board;
import com.example.b2bservice.dto.User;
import com.example.b2bservice.form.BoardForm;
import com.example.b2bservice.paging.PageStatus;
import com.example.b2bservice.repository.BoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public List<Board> showBoardList(){
        return boardRepository.findAll();
    }

    public Board selectBoardByBoardNo(Long boardNo){
        return boardRepository.findByBoardNo(boardNo);
    }

    public Board createBoard(User user, BoardForm boardForm){
        Board board = new Board();
        board.setTitle(boardForm.getTitle()); // 요청 form에서 title 추출
        board.setContent(boardForm.getContent()); // 요청 form에서 content 추출
        board.setUserId(user.getId()); // 로그인 유저 아이디 저장
        board.setViews(0); // 조회수 0 시작
        board.setWrittenDate(getCurrentTime()); // 현재 시각
        return boardRepository.save(board);
    }

    public String getCurrentTime(){ // 현재시간 구하기
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return sdf.format(timestamp);
    }

    public Board modifyBoard(Long boardNo, BoardForm boardForm){
        Board board = boardRepository.findByBoardNo(boardNo); // 수정 처리할 board 불러오기
        board.setTitle(boardForm.getTitle()); // 수정된 제목으로 업데이트
        board.setContent(boardForm.getContent()); // 수정된 내용으로 업데이트
        return boardRepository.save(board); // board update 처리 완료
    }

    public Board showBoardDetail(Long boardNo){
        Board board = boardRepository.findByBoardNo(boardNo);
        board.setViews(board.getViews()+1); // 조회수 증가
        boardRepository.save(board); // board update
        return board;
    }

    public Board deleteBoard(Long boardNo){
        Board board = boardRepository.findByBoardNo(boardNo); // 해당하는 board entity 추출
        return boardRepository.delete(board); // 해당 entity DB에서 삭제
    }

    public List<Board> selectBoardList(PageStatus pageStatus){
        return boardRepository.selectBoardList(pageStatus);
    }

    public int getTotalCntBoard(){
        return boardRepository.getTotalBoardCnt();
    }

}
