package jpabook.jpashop.service;

import jpabook.jpashop.domain.Item.Item;

import java.util.List;

public interface ItemService {

    void saveItem(Item item);

    List<Item> findItems();

    void updateItem(Long itemId,String name,int price,int stockQuantity);

    Item findOne(Long itemId);
}
