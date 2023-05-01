package com.example.b2bservice.controller;

import com.example.b2bservice.alert.Message;
import com.example.b2bservice.dto.Board;
import com.example.b2bservice.dto.Comment;
import com.example.b2bservice.dto.User;
import com.example.b2bservice.form.BoardForm;
import com.example.b2bservice.paging.PageStatus;
import com.example.b2bservice.paging.PageBar;
import com.example.b2bservice.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RequestMapping("/board")
@Controller
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService){
        this.boardService = boardService;
    }

    @GetMapping("/list")
    public ModelAndView getBoardPage(@RequestParam(value = "page") int page, ModelAndView mv){ // page번호를 뷰에서 쿼리파라미터로 넘겨 받음. (처음 리스트 진입시 페이지는 1로 받음)
        mv.setViewName("board-list");
        PageStatus pageStatus = new PageStatus(); // 현재 페이지 상태 객체 생성
        pageStatus.setNowPage(page); // 선택한 페이지를 현재 페이지 상태에 nowPage로 설정
        PageBar pageBar = new PageBar(); // 페이지 바 생성
        pageBar.setPageStatus(pageStatus); // 페이지 바에 현재 페이지 상태 객체 등록
        pageBar.setTotalBoardCnt(boardService.getTotalCntBoard()); // 게시글 전체 갯수 설정
        List<Board> boardList = boardService.selectBoardList(pageStatus); // board가 10개씩 담긴 board list를 가져온다.
        mv.addObject("boardList", boardList);
        mv.addObject("pageBar", pageBar);
        return mv;
    }

    @GetMapping("/write")
    public String enterBoardForm(Model model){
        model.addAttribute("boardForm", new BoardForm()); // valid 처리를 위해 빈 폼 객체를 뷰에 먼저 전달해준다.
        return "board-form";
    }

    @PostMapping("/write") // 게시글 등록
    public ModelAndView writeBoard(@Valid @ModelAttribute BoardForm boardForm, BindingResult bindingResult, HttpSession session, ModelAndView mv){

        if(bindingResult.hasErrors()){ // validation 검증
            mv.setViewName("board-form");
            return mv;
        }

        User user = (User)session.getAttribute("loginUser"); // 세션에서 로그인 정보 가져오기
        boardService.createBoard(user, boardForm); // 로그인 유저정보, 요청 form을 service단에 전달
        mv.addObject("data", new Message("게시글이 등록되었습니다.", "/board/list?page=1")); // alert 메시지 띄운 후, 해당 Get Method 실행
        mv.setViewName("alert-page"); // 설정한 Data를 가지고 alertPage view로 이동
        return mv;
    }

    // 게시글 수정 ----------------------------------
    @GetMapping("/modify") // 게시글 수정 페이지 불러오기 (전에 입력된 텍스트가 뷰에 보여져야 한다.)
    public String enterBoardModifyForm(@RequestParam("boardNo") Long boardNo, Model model){
        Board board = boardService.selectBoardByBoardNo(boardNo); // 수정 할 board 객체 불러오기
        BoardForm boardForm = new BoardForm();
        boardForm.setTitle(board.getTitle());
        boardForm.setContent(board.getContent()); // 뷰에 보일 boardForm에 수정 전 board의 제목, 내용 데이터 저장
        model.addAttribute("boardForm", boardForm); // 해당 boardForm 뷰에 전달
        model.addAttribute("boardNo", boardNo); // 수정 처리 할 board의 boardNo도 함께 전달
        return "board-modify-form";
    }

    @PostMapping("/modify/{boardNo}") // 게시글 수정 로직 (post로직에서는 pathvariable 사용 권장)
    public ModelAndView modifyBoard(@Valid @ModelAttribute BoardForm boardForm, BindingResult bindingResult, @PathVariable("boardNo") Long boardNo, ModelAndView mv){

        if(bindingResult.hasErrors()){ // validation 검증
            mv.addObject("boardNo", boardNo); // validation 검증을 위해 다시 뷰페이지를 로드할 때 파라미터로 받은 boardNo도 꼭 챙겨가야 한다.!! (수정 페이지에서는 boardNo가 꼭 있어야 하도록 설계를 해놨다.)
            mv.setViewName("board-modify-form");
            return mv;
        }

        boardService.modifyBoard(boardNo, boardForm); // 수정될 board의 boardNo와 수정한 제목, 내용 데이터를 담은 boardForm 서비스 단에 전달

        mv.addObject("data", new Message("게시글이 수정되었습니다.", "/board/detail?boardNo="+boardNo)); // alert 메시지 띄운 후, 해당 board detail로 이동
        mv.setViewName("alert-page"); // 설정한 Data를 가지고 alertPage view로 이동
        return mv;
    }
    // ---------------------------------------------

    @PostMapping("/reply")
    public Comment writeComment(@RequestBody Comment comment){
        return comment;
    }

    @GetMapping("/detail")
    public String getBoardDetails(@RequestParam("boardNo") Long boardNo, Model model){ // 게시글 상세 조회
        Board board = boardService.showBoardDetail(boardNo);
        model.addAttribute("board", board);
        return "board-detail";
    }

    @GetMapping ("/delete")
    public ModelAndView deleteBoard(@RequestParam("boardNo") Long boardNo, ModelAndView mv){
        boardService.deleteBoard(boardNo);
        mv.addObject("data", new Message("게시글이 삭제되었습니다.", "/board/list?page=1")); // alert 메시지 띄운 후, 해당 Get Method 실행
        mv.setViewName("alert-page"); // 설정한 Data를 가지고 alertPage view로 이동
        return mv;
    }

}
