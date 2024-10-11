package com.data.room.repository;

import com.data.room.domain.Member;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Optional;

@Repository
public class MemberRepository {
    //DBRepository Log 설정
    //Log Format [MemberRepository] [Function Name]: Message
    private final static Logger logger = LogManager.getLogger(MemberRepository.class);
    //JPA는 entitymanager 사용
    private final EntityManager em;

    @Autowired
    public MemberRepository(EntityManager em) {
        this.em = em;
    }

    public Member findById(String id){
        Member member = em.createQuery("select m from Member m where m.member_id = :id", Member.class).setParameter("id", id).getSingleResult();
        if(member == null){
            logger.error("[MemberRepository] [findById] 아이디로 회원 조회 실패");
        }
        return member;
    }




}
