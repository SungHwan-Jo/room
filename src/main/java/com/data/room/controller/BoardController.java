package com.data.room.controller;

import com.data.room.domain.Board;
import com.data.room.domain.Member;
import com.data.room.service.BoardService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class BoardController {
    private final static Logger logger = LogManager.getLogger(BoardController.class);
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/apache")
    public String board(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        if(session.getAttribute("member") == null){
            model.addAttribute("islogin", "false");
        }else{
            model.addAttribute("islogin", "true");
        }
        List<Board> boardList = boardService.getBoardList("Apache");
        model.addAttribute("boardList", boardList);
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
}
