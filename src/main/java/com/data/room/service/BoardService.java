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
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.*;
import java.net.URLEncoder;
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
        String originalFileName = null;

        //NAS Storage upload
        try{
            originalFileName = file.getOriginalFilename();
            File uploadFile = new File(uploadDirectory);
            if(!uploadFile.exists()){
                uploadFile.mkdir();
            }
            File directory = new File(uploadDirectory + kind);
            //File Directory 존재 여부 확인
            if(!directory.exists()){
                directory.mkdir(); //Folder 생성
                File savefile = new File(uploadDirectory + kind + "/" + originalFileName);
                file.transferTo(savefile);
            }else{
                File savefile = new File(uploadDirectory + kind + "/" + originalFileName);
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
    public List<Board> getBoardList(String kind, String searchVal, int start, int end){
        List<Board> boardList = boardRepository.findByKind(kind, searchVal, start, end);
        return boardList;
    }

    public int getBoardCount(String kind, String searchVal){
        int result = boardRepository.totalCount(kind, searchVal);
        return result;
    }

    @Transactional
    public void deleteBoard(int board_num){
        boardRepository.delete(board_num);
    }

    public void deleteFile(int board_num){
        Board deleteBoard = boardRepository.findById(board_num);
        File deletefile = new File(uploadDirectory + deleteBoard.getBoard_kind() + "/" + deleteBoard.getBoard_title());
        if(deletefile.exists()){
            deletefile.delete();
        }else{
            logger.info("[BoardService] [deleteFile]: 파일이 존재하지 않습니다." );
        }

    }

    public void downloadFile(HttpServletResponse response, int board_num){
        Board downloadBoard = boardRepository.findById(board_num);
        String encodingFile = null;
        //response header에 한글 파일인경우 encoding 필요
        try {
            encodingFile = URLEncoder.encode(downloadBoard.getBoard_title(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        String filePath = uploadDirectory + downloadBoard.getBoard_kind() + "/" + downloadBoard.getBoard_title();

        System.out.println("File Path : " + filePath);
        File file = new File(filePath);
        //페이지의 ContentType 초기화를 위한 Reset
        response.reset();

        //인코딩 변경
        response.setHeader("Content-Disposition", "attachment;filename=" + encodingFile);
        //size 설정
        response.setContentLength((int)file.length());
        System.out.println("System TEST " + (int)file.length());
        //임시 버퍼
        byte[] data = new byte[(int)file.length()];
        //file inputstream 생성
        BufferedInputStream fis = null;
        BufferedOutputStream fos = null;

        try{
            fis = new BufferedInputStream(new FileInputStream(file));
            fos = new BufferedOutputStream(response.getOutputStream());

            int count = 0;
            while((count = fis.read(data)) != -1){
                fos.write(data);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                fis.close();
                fos.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
