package jpabook.jpashop.entity;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of={"id","name"})
@Getter@Setter
public class Team {

    @Id @GeneratedValue
    @Column(name = "team_id")
    private Long id;
    private String name;
    @OneToMany(mappedBy = "team")
    List<Member> members = new ArrayList<>();
    public Team(String name) {
        this.name = name;
    }
}
