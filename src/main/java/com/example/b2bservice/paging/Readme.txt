< PageStatus 클래스 >

PageStatus 클래스는 뷰에 보이게 될 페이지의 상태 정보를 저장하는 클래스이다.

해당 클래스의 필드로는 다음이 있다.

현재 페이지 번호를 나타내는 nowPage 필드
페이지에서 보여줄 게시글의 갯수를 나타내는 boardCntPerPage 필드 (페이지당 게시글 출력 갯수를 조정하고 싶으면 이 필드 값을 수정)

해당 클래스의 메소드로는 다음이 있다.

getPageStart() 메소드는 현재 페이지의 게시글 시작 행 번호를 계산하는 메소드이다.
MySQL의 limit 기능을 이용하여 DB에서 게시글을 가져오기 때문에 게시글 시작 행 번호가 필요하다.


< PageBar 클래스 >

PageBar 클래스는 현재 페이지에서 보이게 될 페이징 네비게이션 바의 정보를 저장하는 클래스이다.

해당 클래스의 필드로는 다음이 있다.

현재 페이지의 상태를 저장할 PageStatus 클래스의 객체 필드
DB에 저장되어있는 총 게시글 수인 totalBoardCnt 필드
페이지 바에 보여질 첫번째 페이지 번호인 startPageNum 필드
페이지 바에 보여질 마지막 페이지 번호인 endPageNum 필드
(endPageNum은 페이지 바에서의 페이지번호 최댓값이다. tempEndPage는 게시글 수를 고려한 마지막 페이지 번호다. 헷갈리지 말것.
 ex) 한 뷰에 5개까지의 번호 링크를 보여줄 수 있는 페이지 바에서 3페이지까지 게시글이 출력되면 5페이지가 endPageNum, 3페이지가 tempEndPage)
이전, 다음 버튼 유무 정보를 저장 할 prev, next 필드
페이지 바에 표시될 페이지 번호 링크의 갯수인 displayPageNumCnt 필드 (페이지 바에서 보일 페이지 번호 링크 갯수를 조정하고 싶으면 이 필드 값을 수정)

해당 클래스의 메소드로는 다음이 있다.

calcData() 메소드는 위 필드들의 값을 구해서 설정하기위한 계산 알고리즘 메소드이다.