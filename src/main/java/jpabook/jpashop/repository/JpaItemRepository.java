package jpabook.jpashop.repository;


import jpabook.jpashop.domain.Item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaItemRepository implements ItemRepository{


    @PersistenceContext
    private EntityManager em;

    @Override
    public void save(Item item) {
        if (item.getId() == null) {
            em.persist(item);
        } else {
            em.merge(item);
        }

    }
    @Override
    public Item findOne(Long id){
        return em.find(Item.class,id);
    }
    @Override
    public List<Item> findAll(){
        return em.createQuery("select i from Item i",Item.class).getResultList();
    }
}
