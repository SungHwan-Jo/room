package com.data.room.service;

import com.data.room.domain.Member;
import com.data.room.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    @Autowired
    MemberRepository memberRepository;

    //DataBase안에 member가 있는지 확인하는 서비스
    public Member memberCheck(String id){
        Member member = new Member();
        member = memberRepository.findById(id);
        return member;
    }

}
