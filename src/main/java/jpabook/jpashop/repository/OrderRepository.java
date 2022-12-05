package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order.Order;
import jpabook.jpashop.domain.Order.OrderSearch;

import java.util.List;

public interface OrderRepository {

    void save(Order order);

    Order findOne(Long id);

    List<Order> findAll(OrderSearch orderSearch);



}
