package jpabook.jpashop.repository;

import jpabook.jpashop.dto.MemberDto;
import jpabook.jpashop.entity.Member;
import jpabook.jpashop.entity.Team;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
@RunWith(SpringJUnit4ClassRunner.class)
public class MemberRepositoryTest {


    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    private EntityManager em;


    @Test
    public void testjpa(){
        for(int i=101;i<120;i++){
            Team team = teamRepository.save(new Team("팀" + i));
            memberRepository.save(new Member("user"+i,i,team));

            String query = "select m from Member m inner join m.team t where t.name = :teamName";

            List resultList = em.createQuery(query)
                    .setParameter("teamName", "팀111").getResultList();

            System.out.println("resultList"+resultList);

        }
    }

    @Test
    public void testMember(){
        Member member = new Member("memberA");
        Member savedMember = memberRepository.save(member);
        Member findMember =
                memberRepository.findById(savedMember.getId()).get();
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());

        assertThat(findMember).isEqualTo(member); //JPA 엔티티 동일성 }
    }

    @Test
    public void basicCRUD(){
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        //단건 조화 검증
        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Optional<Member> findMember2 = memberRepository.findById(member2.getId());

        List<Member> all = memberRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        long count  = memberRepository.count();
        assertThat(count).isEqualTo(2);

        memberRepository.delete(member1);
        memberRepository.delete(member2);

    }

    @Test
    public void testfindUser(){

        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> user = memberRepository.findUser(member1.getUsername(), member1.getAge());
        System.out.println("user = "+ user);

        //        findUser(@Param("username")String username , @Param("age") int age);
    }

    @Test
    public void testfindUserDto(){

        Member member1 = new Member("member1");
        Member member2 = new Member("member2");

        Team team = new Team("teamA");
        Team team2 = new Team("team2");

        member1.setTeam(team);
        member2.setTeam(team2);

        memberRepository.save(member1);
        memberRepository.save(member2);
        teamRepository.save(team);
        teamRepository.save(team2);

        List<MemberDto> memberDtos = memberRepository.findMemberDto();
        for(MemberDto memberDto:memberDtos){
            System.out.println("memberDto = " + memberDto);
        }

        //        findUser(@Param("username")String username , @Param("age") int age);
    }

    @Test
    public void page() throws Exception{
        //given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));

        //when
        PageRequest pageRequest = PageRequest.of(0,3, Sort.by(Sort.Direction.DESC,"username"));

        Page<Member> byAge = memberRepository.findByAge(10, pageRequest);
        byAge.getContent();
    }

    @Test
    public void buldUpdate() throws Exception{
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 19));
        memberRepository.save(new Member("member3", 20));
        memberRepository.save(new Member("member4", 21));
        memberRepository.save(new Member("member5", 40));

        int resultCount = memberRepository.bulkAgePlus(20);

        assertThat(resultCount).isEqualTo(3);
    }

    @Test
    public void JpaEventBaseEntity() throws Exception{
        Member member = new Member("member1");
        memberRepository.save(member);//@PrePersist

        Thread.sleep(100);
        member.setUsername("member2");

        em.flush();//@PreUpdate
        em.clear();


    }
}