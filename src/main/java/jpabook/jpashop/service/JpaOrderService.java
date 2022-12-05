package jpabook.jpashop.service;


import jpabook.jpashop.domain.Delivery.Delivery;
import jpabook.jpashop.domain.Delivery.DeliveryStatus;
import jpabook.jpashop.domain.Item.Item;
import jpabook.jpashop.domain.Member.Member;
import jpabook.jpashop.domain.Order.Order;
import jpabook.jpashop.domain.Order.OrderItem;
import jpabook.jpashop.domain.Order.OrderSearch;
import jpabook.jpashop.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//주문 서비스는 주문 엔티티와 주문 상품 엔티티의 비지니스 로직을 활용해 주문,주문취소,주문 내역 검색 기능을 제공한다.
//참고로 예제를 단순화 하기위해 한번에 하나의 상품만 주문할 수 있다.
@Service
@Transactional(readOnly = true)
//final 이 붙은 필드를 생성자 주입(DI)해준다.
@RequiredArgsConstructor
@Slf4j
public class JpaOrderService implements OrderService{

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;


    //주문
    @Transactional
    @Override
    public Long order(Long memberId,Long itemId,int count){
        log.info("STARTTT");

        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송 정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setStatus(DeliveryStatus.READY);

        //주문 상품 생성
        OrderItem orderItem =  OrderItem.createOrderItem(item,item.getPrice(),count);

        //주문 생성
        Order order = Order.createOrder(member,delivery,orderItem);

        //주문 저장
        orderRepository.save(order);
        return order.getId();


    }

    //주문 취소
    @Transactional
    @Override
    public void cancelOrder(Long orderId){
        Order order  = orderRepository.findOne(orderId);
        order.cancel();
    }

    @Override
    public List<Order> findOrders(OrderSearch orderSearch) {
        orderRepository.findAll(orderSearch);
        return null;
    }
}
