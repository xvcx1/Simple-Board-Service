package com.example.b2bservice.repository;

import com.example.b2bservice.dto.Board;
import com.example.b2bservice.paging.PageStatus;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class BoardRepository {

    private final EntityManager em;

    public BoardRepository(EntityManager em){
        this.em = em;
    }

    public List<Board> findAll() { // 게시글 모두 불러오기
        return em.createQuery("select m from Board m", Board.class).getResultList();
    }

//    public Page findByPageNum(int pageNum) { // 게시글 페이지 단위로 불러오기
//        return em.createQuery("select m from Board m order by m.boardNo desc limit 10, 10", Board.class).getSingleResult();
//    }

    public Board save(Board board) { // 게시글 등록
        em.persist(board);
        return board; // 반환이 필요하지는 않음
    }

    public Board update(Board board) {
        return null;
    }

    public Board delete(Board board) {
        em.remove(board);
        return board;
    }

    public Board findByBoardNo(Long boardNo){
        Board board = em.createQuery("select m from Board m where m.boardNo = :board_no", Board.class)
                .setParameter("board_no", boardNo)
                .getSingleResult();
        return board;
    }

    public List<Board> selectBoardList(PageStatus pageStatus){ // limit 이용해서 board 10개씩 불러오기
        return em.createQuery("select m from Board m order by m.boardNo desc", Board.class)
                .setFirstResult(pageStatus.getPageStart()) // 현재 페이지의 게시글 시작 행 번호부터
                .setMaxResults(pageStatus.getBoardCntPerPage()) // 10개까지만 출력하도록 설정
                .getResultList();
    }

    public int getTotalBoardCnt(){
        return em.createQuery("select count(m) from Board m", Long.class).getSingleResult().intValue();
    }

}
