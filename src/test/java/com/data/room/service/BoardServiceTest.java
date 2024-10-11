package com.data.room.service;

import com.data.room.domain.Board;
import com.data.room.repository.BoardRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardServiceTest {
    @Autowired
    BoardRepository boardRepository;


    @Test
    void uploadFile() {
    }

    @Test
    void saveBoardInfo() {
        for(int i=1; i <= 50; i ++){
            Board board = new Board();
            board.setBoard_kind("Apache");
            board.setBoard_title("apache_소개_20240923.pptx" + String.valueOf(i));
            board.setBoard_content("Apache 소개 자료" + String.valueOf(i));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            board.setBoard_date(simpleDateFormat.format(System.currentTimeMillis()));
            boardRepository.save(board);
        }
//        Board board = new Board();
//        board.setBoard_kind("Apache");
//        board.setBoard_title("apache_소개_20240923.pptx");
//        board.setBoard_content("Apache 소개 자료");
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
//        board.setBoard_date(simpleDateFormat.format(System.currentTimeMillis()));
//        Board saveBoard = boardRepository.save(board);
        //assertThat(saveBoard).isEqualTo(board);

    }
}