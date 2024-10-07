package com.data.room.service;

import com.data.room.controller.MainController;
import com.data.room.domain.Board;
import com.data.room.domain.Member;
import com.data.room.repository.BoardRepository;
import com.data.room.repository.MemberRepository;
import net.bytebuddy.implementation.bytecode.constant.FieldConstant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class BoardService {
    @Autowired
    BoardRepository boardRepository;
    @Value("${upload}")
    private String uploadDirectory;
    //BoardService Log 설정
    //Log Format [BoardService] [Function Name]: Message
    private final static Logger logger = LogManager.getLogger(BoardService.class);

    //File upload 서비스
    public void uploadFile(MultipartFile file, String kind){
        String originalFileName = file.getOriginalFilename();
        //NAS Storage upload
        try{
            File directory = new File(uploadDirectory + kind);
            //File Directory 존재 여부 확인
            if(!directory.exists()){
                directory.mkdir(); //Folder 생성
                File savefile = new File(uploadDirectory + kind + "\\" + originalFileName);
                file.transferTo(savefile);
            }else{
                File savefile = new File(uploadDirectory + kind + "\\" + originalFileName);
                file.transferTo(savefile);
            }


        }catch(IOException e){
           logger.error("[BoardService] [uploadFile]: " + e.getStackTrace());
        }
    }

    public Board saveBoardInfo(Board board){
        Board saveBoard = boardRepository.save(board);
        return saveBoard;

    }
    public List<Board> getBoardList(String kind){
        List<Board> boardList = boardRepository.findByKind(kind);
        return boardList;
    }

    @Transactional
    public void deleteBoard(int board_num){
        boardRepository.delete(board_num);
    }

    public void deleteFile(int board_num){
        Board deleteBoard = boardRepository.findById(board_num);
        File deletefile = new File(uploadDirectory + deleteBoard.getBoard_kind() + "\\" + deleteBoard.getBoard_title());
        if(deletefile.exists()){
            deletefile.delete();
        }else{
            logger.info("[BoardService] [deleteFile]: 파일이 존재하지 않습니다." );
        }

    }

}
