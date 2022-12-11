package jpabook.jpashop.entity;


import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public class JpaBaseEntity {


    @Column(updatable = false)
    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    //JPa entity 가 비영속에서 영속이 될떄 호출된다
    @PrePersist
    public void prePersist(){
        LocalDateTime now = LocalDateTime.now();
        createdDate = now;
        updatedDate = now;
    }

    //영속석 컨테스트에서 업데이트 되기 전에 호출된다.
    @PreUpdate
    public void preUpdate(){
        updatedDate = LocalDateTime.now();
    }
}
