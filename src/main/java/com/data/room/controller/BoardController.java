package com.data.room.controller;

import com.data.room.domain.Board;
import com.data.room.domain.Member;
import com.data.room.domain.Product;
import com.data.room.service.BoardService;
import com.data.room.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class BoardController {
    private final static Logger logger = LogManager.getLogger(BoardController.class);
    private final BoardService boardService;
    private final ProductService productService;

    public BoardController(BoardService boardService, ProductService productService) {
        this.boardService = boardService;
        this.productService = productService;
    }

    @GetMapping("/Apache")
    public String apacheBoard(Model model, HttpServletRequest request, @RequestParam(value="searchVal", required = false) String searchVal){
        if(searchVal == null){
            searchVal = "%%";
        }else {
            searchVal = "%" + searchVal + "%";
        }
        //페이징 처리
        String currentPage = request.getParameter("page");
        //페이지 초기화
        if(currentPage == "" || currentPage == null){
            currentPage="0";
        }
        int pageCount;
        int pageNum = Integer.parseInt(currentPage) + 1 ;
        int Gtotal = boardService.getBoardCount("Apache", searchVal);
        //총 페이지 개수 계산
        if(Gtotal%10==0){
            pageCount = Gtotal/10;
        }else{
            pageCount = (Gtotal/10) + 1;
        }
        int start = (pageNum -1) * 10 + 1;
        int end = pageNum * 10;

        int temp = (pageNum -1)%10;
        int startPage = pageNum - temp;
        int endPage = startPage + 9;
        if(endPage > pageCount) {
            endPage = pageCount;
        }

        HttpSession session = request.getSession();
        if(session.getAttribute("member") == null){
            model.addAttribute("islogin", "false");
        }else{
            model.addAttribute("islogin", "true");
            Member member = (Member)session.getAttribute("member");
            model.addAttribute("memberid", member.getMember_id());
        }
        List<Board> boardList = boardService.getBoardList("Apache", searchVal, start -1, 10);
        model.addAttribute("boardList", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("path", "/Apache");

        //LNB 제품 리스트 조회
        List<Product> productList = productService.getProductList();
        model.addAttribute("productList", productList);
        return "main";
    }
    @GetMapping("/Nginx")
    public String nginxBoard(Model model, HttpServletRequest request, @RequestParam(value="searchVal", required = false) String searchVal){
        if(searchVal == null){
            searchVal = "%%";
        }else {
            searchVal = "%" + searchVal + "%";
        }
        //페이징 처리
        String currentPage = request.getParameter("page");
        //페이지 초기화
        if(currentPage == "" || currentPage == null){
            currentPage="0";
        }
        int pageCount;
        int pageNum = Integer.parseInt(currentPage) + 1 ;
        int Gtotal = boardService.getBoardCount("Nginx", searchVal);
        //총 페이지 개수 계산
        if(Gtotal%10==0){
            pageCount = Gtotal/10  + 1;
        }else{
            pageCount = (Gtotal/10) + 1;
        }
        int start = (pageNum -1) * 10 + 1;
        int end = pageNum * 10;

        int temp = (pageNum -1)%10;
        int startPage = pageNum - temp;
        int endPage = startPage + 9;
        if(endPage > pageCount) {
            endPage = pageCount;
        }

        HttpSession session = request.getSession();
        if(session.getAttribute("member") == null){
            model.addAttribute("islogin", "false");
        }else{
            model.addAttribute("islogin", "true");
            Member member = (Member)session.getAttribute("member");
            model.addAttribute("memberid", member.getMember_id());
        }
        List<Board> boardList = boardService.getBoardList("Nginx", searchVal, start -1, 10);
        model.addAttribute("boardList", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("path", "/Nginx");

        //LNB 제품 리스트 조회
        List<Product> productList = productService.getProductList();
        model.addAttribute("productList", productList);
        return "main";
    }

    @GetMapping("/WebtoB")
    public String webtobBoard(Model model, HttpServletRequest request, @RequestParam(value="searchVal", required = false) String searchVal){
        if(searchVal == null){
            searchVal = "%%";
        }else {
            searchVal = "%" + searchVal + "%";
        }
        //페이징 처리
        String currentPage = request.getParameter("page");
        //페이지 초기화
        if(currentPage == "" || currentPage == null){
            currentPage="0";
        }
        int pageCount;
        int pageNum = Integer.parseInt(currentPage) + 1 ;
        int Gtotal = boardService.getBoardCount("WebtoB", searchVal);
        //총 페이지 개수 계산
        if(Gtotal%10==0){
            pageCount = Gtotal/10  + 1;
        }else{
            pageCount = (Gtotal/10) + 1;
        }
        int start = (pageNum -1) * 10 + 1;
        int end = pageNum * 10;

        int temp = (pageNum -1)%10;
        int startPage = pageNum - temp;
        int endPage = startPage + 9;
        if(endPage > pageCount) {
            endPage = pageCount;
        }

        HttpSession session = request.getSession();
        if(session.getAttribute("member") == null){
            model.addAttribute("islogin", "false");
        }else{
            model.addAttribute("islogin", "true");
            Member member = (Member)session.getAttribute("member");
            model.addAttribute("memberid", member.getMember_id());
        }
        List<Board> boardList = boardService.getBoardList("WebtoB", searchVal, start -1, 10);
        model.addAttribute("boardList", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("path", "/WebtoB");

        //LNB 제품 리스트 조회
        List<Product> productList = productService.getProductList();
        model.addAttribute("productList", productList);
        return "main";
    }

    @GetMapping("/Patch")
    public String patchBoard(Model model, HttpServletRequest request, @RequestParam(value="searchVal", required = false) String searchVal){
        if(searchVal == null){
            searchVal = "%%";
        }else {
            searchVal = "%" + searchVal + "%";
        }
        //페이징 처리
        String currentPage = request.getParameter("page");
        //페이지 초기화
        if(currentPage == "" || currentPage == null){
            currentPage="0";
        }
        int pageCount;
        int pageNum = Integer.parseInt(currentPage) + 1 ;
        int Gtotal = boardService.getBoardCount("Patch", searchVal);
        //총 페이지 개수 계산
        if(Gtotal%10==0){
            pageCount = Gtotal/10  + 1;
        }else{
            pageCount = (Gtotal/10) + 1;
        }
        int start = (pageNum -1) * 10 + 1;
        int end = pageNum * 10;

        int temp = (pageNum -1)%10;
        int startPage = pageNum - temp;
        int endPage = startPage + 9;
        if(endPage > pageCount) {
            endPage = pageCount;
        }

        HttpSession session = request.getSession();
        if(session.getAttribute("member") == null){
            model.addAttribute("islogin", "false");
        }else{
            model.addAttribute("islogin", "true");
            Member member = (Member)session.getAttribute("member");
            model.addAttribute("memberid", member.getMember_id());
        }
        List<Board> boardList = boardService.getBoardList("Patch", searchVal, start -1, 10);
        model.addAttribute("boardList", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("path", "/Patch");

        //LNB 제품 리스트 조회
        List<Product> productList = productService.getProductList();
        model.addAttribute("productList", productList);
        return "main";
    }

    @GetMapping("/boardWrite")
    public String boardWrite(Model model, HttpServletRequest requeset){
        HttpSession session = requeset.getSession();
        if(session.getAttribute("member") == null){
            model.addAttribute("islogin", "false");
        }else{
            model.addAttribute("islogin", "true");
            Member member = (Member)session.getAttribute("member");
            model.addAttribute("memberid", member.getMember_id());
        }
        return "boardwrite";
    }

    @PostMapping("/fileupload")
    public String fileUpload(Model model, @RequestParam("fileInput") MultipartFile uploadFile, HttpServletRequest request){
        String kind = request.getParameter("kind");
        String filename = uploadFile.getOriginalFilename();
        String contents = request.getParameter("contents");
        HttpSession session = request.getSession();
        if(session.getAttribute("member") == null){
            model.addAttribute("islogin", "false");
        }else{
            model.addAttribute("islogin", "true");
            Member member = (Member)session.getAttribute("member");
            model.addAttribute("memberid", member.getMember_id());
        }

        //file upload
        boardService.uploadFile(uploadFile, kind);

        //file info database 저장
        Board board = new Board();
        board.setBoard_kind(kind);
        board.setBoard_title(filename);
        board.setBoard_content(contents);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        board.setBoard_date(simpleDateFormat.format(System.currentTimeMillis()));
        Board result = boardService.saveBoardInfo(board);
        if(result.getBoard_num() != 0){
            model.addAttribute("message","파일 저장 완료");
            model.addAttribute("searchUrl","/main");
        }

        return "alert";
    }
    @PostMapping("/boardDelete")
    public String boardDelete(Model model, HttpServletRequest request, @RequestParam List<String> boardNums){
        HttpSession session = request.getSession();
        if(session.getAttribute("member") == null){
            model.addAttribute("islogin", "false");
        }else{
            model.addAttribute("islogin", "true");
        }
        for(int i=0; i < boardNums.size(); i++){
            int board_num = Integer.valueOf(boardNums.get(i));
            boardService.deleteFile(board_num); //DB에서 지우기 전에 File을 먼저 지운다.
            boardService.deleteBoard(board_num);
        }

        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;

    }

    @GetMapping("/download")
    public void boardDownload(HttpServletRequest request, HttpServletResponse response){
        int board_num = Integer.parseInt(request.getParameter("boardId"));
        boardService.downloadFile(response, board_num);
    }
}
