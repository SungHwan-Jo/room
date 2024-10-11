package com.data.room.repository;

import com.data.room.domain.Board;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;


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
    //paging 기능 구현


    public List<Board> findByKind(String kind, String searchVal, int start, int end){
        List<Board> boardList = em.createQuery("select b from Board b where b.board_kind = :kind and (lower(b.board_title) like lower(:searchVal) or upper(b.board_title) like upper(:searchVal)) order by board_num desc", Board.class)
                .setParameter("kind", kind).setParameter("searchVal", searchVal)
                .setFirstResult(start)
                .setMaxResults(end)
                .getResultList();
        if(boardList == null){
            logger.error("[BoardRepository] [findByKind] 게시판 조회 실패");
        }
        return boardList;
    }

    //제품별 총 갯수
    public int totalCount(String kind, String searchVal) {
        long count = em.createQuery("select count(b) from Board b where b.board_kind = :kind and (lower(b.board_title) like lower(:searchVal) or upper(b.board_title) like upper(:searchVal))", Long.class)
                .setParameter("kind", kind)
                .setParameter("searchVal", searchVal).getSingleResult();
        if(count == 0){
            logger.warn("[BoardRepository] [totalCount] board 게시판 수량 0");
        }
        return Long.valueOf(count).intValue();
    }

    public Board findById(int board_num){
        Board board = em.createQuery("select b from Board b where b.board_num = :board_num order by board_num desc", Board.class).setParameter("board_num", board_num).getSingleResult();
        if(board == null){
            logger.error("[BoardRepository] [findById] id를 통한 게시판 조회 실패");
        }
        return board;
    }



    public void delete(int board_num){
        Board board = this.findById(board_num);
        if(board == null){
            logger.error("[BoardRepository] [delete] 삭제 실패");
        }
        em.remove(board);

    }

}
