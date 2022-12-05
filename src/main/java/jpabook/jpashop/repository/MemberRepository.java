package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member.Member;

import java.util.List;

public interface MemberRepository {

    Long save(Member member);

    Member findOne(Long id);

    List<Member> findAll();

    List<Member> findByName(String name);
}
