package com.data.room.controller;

import com.data.room.domain.Board;
import com.data.room.domain.Member;
import com.data.room.domain.Product;
import com.data.room.service.BoardService;
import com.data.room.service.MemberService;
import com.data.room.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MainController {
    private final MemberService memberService;
    private final BoardService boardService;
    private final ProductService productService;

    //MainController Log 설정
    //Log Format [MainController] [Function Name]: Message
    private final static Logger logger = LogManager.getLogger(MainController.class);

    public MainController(MemberService memberService, BoardService boardService, ProductService productService) {
        this.memberService = memberService;
        this.boardService = boardService;
        this.productService = productService;
    }

    @GetMapping("/")
    public String root(Model model){
        return "redirect:/main";
    }
    @GetMapping("/main")
    public String main(Model model, HttpServletRequest request, @RequestParam(value="searchVal", required = false) String searchVal){
        //검색어 처리
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
        model.addAttribute("path", "/main");

        //LNB 제품 리스트 조회
        List<Product> productList = productService.getProductList();
        model.addAttribute("productList", productList);
        return "main";
    }

    @PostMapping("/login")
    public String login(Model model, HttpServletRequest request){
        String id = request.getParameter("content_id");
        String pwd = request.getParameter("content_pwd");
        if (id == null || pwd == null){
            model.addAttribute("message","아이디, 비밀번호를 확인하세요");
            model.addAttribute("searchUrl","/main");
            return "alert";
        }
        Member member = memberService.memberCheck(id);
        if (member == null){
            model.addAttribute("message","아이디, 비밀번호를 확인하세요");
            model.addAttribute("searchUrl","/main");
            return "alert";
        }else{
            HttpSession session = request.getSession();
            session.setAttribute("member", member);
            return "redirect:/main";
        }

    }
}
