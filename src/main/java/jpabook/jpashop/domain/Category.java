package jpabook.jpashop.domain;


import jakarta.persistence.*;
import jpabook.jpashop.domain.Item.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter@Setter
public class Category {

  @Id @GeneratedValue
  @Column(name = "category_id")
  private Long id;

  private String name;

  @ManyToMany//카테고리도 리스트로 아이템을 가지고 , 아이템도 리스트로 카테고리를 가진다
  @JoinTable(name="category_item", // 연결테이블을 지정 'category_item'이라는 테이블을 연결테이블로 지정한다.
      joinColumns = @JoinColumn(name = "category_id"), //현재 엔티티의 외래키(fk)로는 category_id를 설정한다.
      inverseJoinColumns = @JoinColumn(name="item_id")) // 상대 엔티티의 외래키(fk)로는 item_id로 설정한다.
  private List<Item> items = new ArrayList<>();

  //parent - child 가 한 테이블 내에서 연관관계를 갖는다.
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id") // parent_id 칼럼이 parent 필드와 매핑된다.
  private Category parent;

  @OneToMany(mappedBy = "parent") //child는 parent를 기반으로 매핑된다.
  private List<Category> child = new ArrayList<>();
  ///

  public void addChildCategory(Category child){
    this.child.add(child);
    child.setParent(this);

  }

}
