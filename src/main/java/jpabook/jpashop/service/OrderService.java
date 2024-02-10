package jpabook.jpashop.service;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.Item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
  private final MemberRepository memberRepository;
  private final OrderRepository orderRepository;
  private final ItemRepository itemRepository;

  /** 주문 */
  @Transactional
  public Long order(Long memberId, Long itemId, int count) {

    //엔티티 조회
    Member member = memberRepository.findOne(memberId);
    Item item = itemRepository.findOne(itemId);

    //배송정보 생성
    Delivery delivery = new Delivery();
    delivery.setAddress(member.getAddress());
    delivery.setStatus(DeliveryStatus.READY);

    //주문상품 생성
    OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

    //주문 생성
    Order order = Order.createOrder(member, delivery, orderItem);

    //주문 저장
    orderRepository.save(order);
    return order.getId();
  }

  /** 주문 취소 */
  @Transactional
  public void cancelOrder(Long orderId) {

    //주문 엔티티 조회
    Order order = orderRepository.findOne(orderId);

    //주문 취소
    order.cancel();
  }


/** 주문 검색 */
/*
public List<Order> findOrders(OrderSearch orderSearch) {
return orderRepository.findAll(orderSearch);
}
*/
}



//Cascade란 객체 간 연관 관계에서 한 객체의 상태 변경이 다른 연관된 객체에 영향을 미치는 경우
//persist란 엔티티 객체가 데이터베이스와 연결되어 지속적으로 관리되는 상태를 의미
//트랜잭션은 데이터베이스 상태를 변화시키기 위한 하나 이상의 sql 명령문의 집합을 의미하는 논리적인 작업 단위