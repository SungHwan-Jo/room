package com.data.room.service;

import com.data.room.domain.Member;
import com.data.room.repository.MemberRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    @Autowired
    MemberRepository memberRepository;
    //MemberService Log 설정
    //Log Format [MemberService] [Function Name]: Message
    private final static Logger logger = LogManager.getLogger(MemberService.class);

    //DataBase안에 member가 있는지 확인하는 서비스
    public Member memberCheck(String id){
        Member member = new Member();
        member = memberRepository.findById(id);
        if(member == null){
            logger.error("[MemberService] [memberCheck]: Member 확인 실패 ");
        }
        return member;
    }

}
