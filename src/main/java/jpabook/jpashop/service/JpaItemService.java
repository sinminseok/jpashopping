package jpabook.jpashop.service;


import jpabook.jpashop.domain.Item.Item;
import jpabook.jpashop.domain.Item.dtype.Book;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//상품 서비스는 컨트롤러에서 호출하고 상품 리포지토리에 단순히 위임만한다.
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JpaItemService implements ItemService{

    private final ItemRepository itemRepository;

    @Transactional
    @Override
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    @Transactional
    @Override
    public void updateItem(Long itemId,String name,int price,int stockQuantity){

        //준영속 상태의 엔티티를 영속성 컨텍스트에서 엔티티를 다시 조회한후 수정한다ㅣ(변경감지)
        Item findItem = itemRepository.findOne(itemId);
        findItem.setPrice(price);
        findItem.setName(name);
        findItem.setStockQuantity(stockQuantity);


    }

    @Override
    public List<Item> findItems(){
        return itemRepository.findAll();
    }
    @Override
    public Item findOne(Long itemId){
        return  itemRepository.findOne(itemId);
    }
}
