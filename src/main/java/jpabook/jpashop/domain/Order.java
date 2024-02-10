package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")     //order 엔티티는 member 엔티티와 연관관계
@Getter @Setter
@NoArgsConstructor(access= AccessLevel.PROTECTED)

public class Order {

  @Id @GeneratedValue
  @Column(name = "order_id")
  private Long id;
                                      // X TO ONE 들은 모두 LAZY로 바꿔준다
  @ManyToOne(fetch = FetchType.LAZY) // 무조건 LAZY 써라 (EAGER쓰면 연관된 이상한 쿼리 다 날라간다)
  @JoinColumn(name = "member_id") //맵핑을 뭘로 할거냐 -> fk를 member_id로 설정
  private Member member;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) //OrderItem에 있는 order와 연관관계
  private List<OrderItem> orderItems = new ArrayList<>();

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) //Cascade ALL하면 Order 저장할때 delivery 개체만 세팅해놔도
  @JoinColumn(name = "delivery_id")                                 //Order를 persist하면 Cascade ALL도 cascade된다.
  private Delivery delivery;

  private LocalDateTime orderDate; //HIBERNATE가 알아서 해줌 (LocalDateTime - Java 8부터)

  @Enumerated(EnumType.STRING)
  private OrderStatus status; // 주문상태 [ORDER / CANCEL] 두가지 상태 (enum)



  //<==연관관계 메서드==>
  public void setMember(Member member){
    this.member = member;
    member.getOrders().add(this);
  }

  public void addOrderItem(OrderItem orderItem){
    orderItems.add(orderItem);
    orderItem.setOrder(this);
  }

  public void setDelivery(Delivery delivery){
    this.delivery = delivery;
    delivery.setOrder(this);
  }

  //==생성 메서드==/ 주문 생성 관련시 여기만 건들면 된다.
  public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){ //이 ...은 리스트? 암튼 여러개 저장가능케함.
    Order order = new Order();
    order.setMember(member);
    order.setDelivery(delivery);

    for (OrderItem orderItem : orderItems){
      order.addOrderItem(orderItem);
    }
    order.setStatus(OrderStatus.ORDER);
    order.setOrderDate(LocalDateTime.now());
    return order;
  }



  //== 비즈니스 메서드 ==

  //주문 취소
  public void cancel(){
    if(delivery.getStatus()==DeliveryStatus.COMP){
      throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
    }

    this.setStatus(OrderStatus.CANCEL);
    for(OrderItem orderItem: orderItems){
      orderItem.cancel();
    }
  }

  //==조회 로직==

  //전체 주문가격 조회
  public int getTotalPrice(){
    int totalPrice = 0;
    for (OrderItem orderItem : orderItems){
      totalPrice += orderItem.getTotalPrice();
    }
    return totalPrice;
  }
}
