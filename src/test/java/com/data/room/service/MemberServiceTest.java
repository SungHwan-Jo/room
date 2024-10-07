package com.data.room.service;

import com.data.room.domain.Member;
import com.data.room.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional //TEST시 DB에 데이터를 저장한 뒤 commit을 하지 않는 것.
class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void memberCheck() {
        //주어진 조건
        Member member = new Member();
        member.setMember_id("fjdghks153");

        Member findMember = memberService.memberCheck("fjdghks153");
        assertThat(member.getMember_id()).isEqualTo(findMember.getMember_id());

    }
}