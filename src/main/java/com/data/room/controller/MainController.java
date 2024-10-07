package com.data.room.controller;

import com.data.room.domain.Board;
import com.data.room.domain.Member;
import com.data.room.service.BoardService;
import com.data.room.service.MemberService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MainController {
    private final MemberService memberService;
    private final BoardService boardService;

    //MainController Log 설정
    //Log Format [MainController] [Function Name]: Message
    private final static Logger logger = LogManager.getLogger(MainController.class);

    public MainController(MemberService memberService, BoardService boardService) {
        this.memberService = memberService;
        this.boardService = boardService;
    }

    @GetMapping("/")
    public String root(Model model){
        return "redirect:/main";
    }
    @GetMapping("/main")
    public String main(Model model, HttpServletRequest requeset){
        HttpSession session = requeset.getSession();
        if(session.getAttribute("member") == null){
            model.addAttribute("islogin", "false");
        }else{
            model.addAttribute("islogin", "true");
            Member member = (Member)session.getAttribute("member");
            model.addAttribute("memberid", member.getMember_id());
        }
        List<Board> boardList = boardService.getBoardList("Apache");
        model.addAttribute("boardList", boardList);
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
