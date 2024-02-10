package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter@Setter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class OrderItem {

  @Id @GeneratedValue
  @Column(name = "order_item_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY) //OrderItem은 Item 테이블과 N대 1 관계 (1개의 Item은 여러 OrderItem에 속할 수 있다)
  @JoinColumn(name = "item_id") //Item 엔티티에 있는 item_id와 item이 조인(맵핑)된다.
  private Item item;

  @ManyToOne(fetch = FetchType.LAZY) //OrderItem은 Order와 'N대1'의 관계를 가진다 - 1개의 Order에는 N개의 OrderItem을 가질 수 있음.
  @JoinColumn(name = "order_id")  // Order 엔티티에 있는 order_id와 order가 조인(맵핑)된다.
  private Order order;

  private int orderPrice; // 주문 가격
  private int count; //주문 수랑


  //==생성 메서드 == // 주문을 생성하는 메서드
  public static OrderItem createOrderItem(Item item, int orderPrice, int count){
    OrderItem orderItem = new OrderItem();
    orderItem.setItem(item);
    orderItem.setOrderPrice(orderPrice);
    orderItem.setCount(count);

    item.removeStock(count); //재고를 주문한 만큼 removeStock()한다.
    return orderItem;
  }

  // == 비즈니스 로직 ==
  public void cancel() { //주문 취소로 재고 수량 원상복구
    getItem().addStock(count);
  }

  //==조회 로직==
  public int getTotalPrice() {
    return getOrderPrice()*getOrderPrice();
  }
}
