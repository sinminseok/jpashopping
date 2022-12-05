package jpabook.jpashop.service;

import jpabook.jpashop.repository.MemberRepository;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import jpabook.jpashop.domain.Member.Member;


import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

//RunWith(Spring.class) 스프링과 테스트 통합
//@SpringBootTest: 스프링 부트 띄우고 테스트 이게 없ㅇ르면 @Autowired안됨
//Transactionalq 반복 가능한 테스트 지원 각각의 테스트를 실행할 때마다 트랜잭션을 시작하고 끝나면 롤백한다.(테스트 케이스에서 사용될때만 롤백된다)
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;


    @Test
    public void 회원가입() throws Exception{

        //Given
        Member member = new Member();
        member.setName("kimdd");

        //When
        Long saveId = memberService.join(member);

        Assertions.assertEquals(member,memberRepository.findOne(saveId));
    }

//    @Test
//    public void 중복회원예외() throws Exception{
//        Member member1 = new Member();
//        member1.setName("KIM");
//
//        Member member2 = new Member();
//        member2.setName("KIM");
//
//        memberService.join(member1);
//        memberService.join(member2);
//
//        fail("에외 발생");
//
//
//    }
}