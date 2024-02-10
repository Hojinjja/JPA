package jpabook.jpashop.domain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

@Entity
@Getter@Setter
public class Delivery {

  @Id @GeneratedValue
  @Column(name = "delivery_id")
  private Long id;

  //Delivery는 Order와 1대1관계 = 주문은 꼭 하나의 배송 정보만 가지고, 배송 정보도 꼭 하나의 주문 정보만 갖는다.
  //1대1 관계에선 어디서 fk를 둬도 상관이 없으나 , 액세스를 더 많이하는 곳에 fk를 두는 것을 선호(Order에 두자)
  @OneToOne(mappedBy = "delivery",fetch = FetchType.LAZY) // Delivery 엔티티에 있는 order는 Order에 있는 delivery에 의해 매핑된다.
  private Order order; // (Delivery 클래스의 order(거울) -맵핑-> Order 클래스의 delivery(진짜))

  @Embedded
  private Address address;


  @Enumerated(EnumType.STRING) // EnumType은 항상 STRING으로 하자. ORDINAL로 하면 중간에 상태 추가되면 망함.(밀려짐)
  private DeliveryStatus status; // [READY/COMP] ->2가지 상태(enum 파일생성)
}
