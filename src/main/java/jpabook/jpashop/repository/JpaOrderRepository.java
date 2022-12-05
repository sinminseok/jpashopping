package jpabook.jpashop.repository;


import jpabook.jpashop.domain.Order.Order;
import jpabook.jpashop.domain.Order.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaOrderRepository implements OrderRepository{

    @PersistenceContext
    private EntityManager em;

    @Override
    public void save(Order order){
        em.persist(order);
    }
    @Override
    public Order findOne(Long id){
        return em.find(Order.class,id);
    }

    @Override
    public List<Order> findAll(OrderSearch orderSearch) {
        em.find()
        return null;
    }


}
