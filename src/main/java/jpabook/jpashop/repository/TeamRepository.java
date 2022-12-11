package jpabook.jpashop.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import jpabook.jpashop.entity.Team;

public interface TeamRepository extends JpaRepository<Team,Long> {
}
