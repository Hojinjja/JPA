package jpabook.jpashop.domain.Item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Category;
import jpabook.jpashop.domain.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 상속 전략 - 싱글테이블 전략 ( 한 테이블에 다 때려넣음 )
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {

  @Id
  @GeneratedValue
  @Column(name = "item_id")
  private Long id;

  private String name;
  private int price;
  private int stockQuantity;


  //다대다 설정
  @ManyToMany(mappedBy = "items")
  private List<Category> categories = new ArrayList<>(); // <Category> 엔티티와 다대다 관계를 갖는다.


  //--비즈니스 로직--//

  //재고 증가 (stock ++)
  public void addStock(int quantity){
    this.stockQuantity += quantity;
  }

  //재고 감소 (stock --_
  public void removeStock(int quantity){
    int restStock = this.stockQuantity - quantity;
    if(restStock<0){
      throw new NotEnoughStockException("need more stock"); // NotEnoughStockException 생성해야함
    }
    this.stockQuantity = restStock;
  }


}
