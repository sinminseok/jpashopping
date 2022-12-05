package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Item.Item;

import java.util.List;

public interface ItemRepository {

    void save(Item item);

    Item findOne(Long id);

    List<Item> findAll();

}
