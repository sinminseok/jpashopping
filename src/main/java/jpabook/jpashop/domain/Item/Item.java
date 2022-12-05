package jpabook.jpashop.domain.Item;


import jpabook.jpashop.domain.Category.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "detype")
@Getter
@Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<Category>();

    //비지니스 로직
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStock(int orderQuantity) {
        log.info("orderQuantity = {}",orderQuantity);
        log.info("this.stockQuantity = {}",this.stockQuantity);
        int restStock = this.stockQuantity - orderQuantity;
        log.info("restStock = {}",restStock);
        if (restStock < 0) {
            throw new NotEnoughStockException("재고가 더 없습니다,.");
        }
        this.stockQuantity = restStock;
    }

}
