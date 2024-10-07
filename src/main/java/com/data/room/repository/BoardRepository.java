package com.data.room.repository;

import com.data.room.domain.Board;
import com.data.room.domain.Member;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BoardRepository {
    //BoardRepository Log 설정
    //Log Format [BoardRepository] [Function Name]: Message
    private final static Logger logger = LogManager.getLogger(BoardRepository.class);
    //JPA는 entitymanager 사용
    private final EntityManager em;

    @Autowired
    public BoardRepository(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public Board save(Board board){
        em.persist(board);
        return board;
    }

    public List<Board> findByKind(String kind){
        List<Board> boardList = em.createQuery("select b from Board b where b.board_kind = :kind order by board_num desc", Board.class).setParameter("kind", kind).getResultList();
        return boardList;
    }

    public Board findById(int board_num){
        Board board = em.createQuery("select b from Board b where b.board_num = :board_num order by board_num desc", Board.class).setParameter("board_num", board_num).getSingleResult();
        return board;
    }



    public void delete(int board_num){
        Board board = this.findById(board_num);
        em.remove(board);

    }





}
