package jpabook.jpashop.controller;


import jpabook.jpashop.dto.MemberDto;
import jpabook.jpashop.entity.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;


@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id){
        Member member = memberRepository.findById(id).get();
        log.info("member = {} ",member);
        return member.getUsername();
    }

    //도메인 클래스 컨버터로 엔티티를 파라미터로 받으면 이 엔티티는 단순 조회용으로만 사용하자.(트랜잭션이 없다)
    @GetMapping("/members2/{id}")
    public String findMember2(@PathVariable("id") Member member){
        return member.getUsername();
    }

    @GetMapping("/members")
    public Page<Member> list(Pageable pageable){
        Page<Member> page = memberRepository.findAll(pageable);
        return page;
    }

    //Page내용을 DTO로 변경
    @GetMapping("/members2")
    public Page<MemberDto> list2(Pageable pageable){
        Page<Member> page = memberRepository.findAll(pageable);
        Page<MemberDto> pageDto = page.map(MemberDto::new);
        return pageDto;
    }

    @PostConstruct
    public void init(){
        for(int i=0;i<100;i++){
            memberRepository.save(new Member("user"+i,i));
        }

    }



}
