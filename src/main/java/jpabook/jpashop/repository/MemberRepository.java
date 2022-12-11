package jpabook.jpashop.repository;
import jpabook.jpashop.dto.MemberDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import jpabook.jpashop.entity.Member;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member,Long>,MemberRepositoryCustom {

    List<Member> findByUsernameAndAgeGreaterThan(String username,int age);


    @Query("select m from Member m where m.username= :username and m.age = :age")
    List<Member> findUser(@Param("username")String username , @Param("age") int age);

    @Query("select new jpabook.jpashop.dto.MemberDto(m.id,m.username,t.name)"+"from Member m join m.team t")
    List<MemberDto> findMemberDto();

    Page<Member> findByAge(int age, Pageable pageable);

//    Slice<Member> findByUsername(String name,Pageable pageable);

//    List<Member> findByUsername(String name,Pageable pageable);

    //스프링 데이터 JPA를 사용한 벌크성 수정 쿼리
    @Modifying
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

}
