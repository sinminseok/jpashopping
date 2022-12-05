package jpabook.jpashop.config;


import jpabook.jpashop.repository.*;
import jpabook.jpashop.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {


    @Bean
    public MemberRepository memberRepository(){
        return new JpaMemberRepository();
    }

    @Bean
    public OrderRepository orderRepository(){
        return new JpaOrderRepository();
    }

    @Bean
    public ItemRepository itemRepository(){
        return new JpaItemRepository();
    }

    @Bean
    public OrderService orderService() {
        return new JpaOrderService(memberRepository(),orderRepository(),itemRepository());
    }

    @Bean
    public ItemService itemService(){
        return new JpaItemService(itemRepository());
    }

    @Bean
    public MemberService memberService(){
        return new JpaMemberService(memberRepository());
    }








}
