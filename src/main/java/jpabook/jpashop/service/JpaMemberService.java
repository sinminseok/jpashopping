package jpabook.jpashop.service;


import jpabook.jpashop.domain.Member.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JpaMemberService implements MemberService{

    @Autowired
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public Long join(Member member){
        validateDuplicateMember(member);

        memberRepository.save(member);
        return member.getId();
    }


    @Override
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    @Override
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

    private void validateDuplicateMember(Member member){
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
}
