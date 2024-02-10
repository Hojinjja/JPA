package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter @Setter
public class Member {

  @Id @GeneratedValue
  @Column(name = "member_id")
  private Long id;

  private String name;

  @Embedded
  private Address address;

  // Member와 Order는 '1대N'의 관계이다 (한명의 회원이 여러 주문가능)
  @OneToMany(mappedBy = "member")// mappedBy ="member" 오더 테이블에 있는 member 필드에 의해 맵핑된다 -읽기전용 ( 내 주인은 Order에 있는 member야)
  private List<Order> orders = new ArrayList<>(); // <Order> 테이블과 맵핑
  //컬렉션 내에서 초기화 해라. (엔티티 설계시 주의점)

}
