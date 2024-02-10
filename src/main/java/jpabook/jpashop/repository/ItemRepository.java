package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

  private final EntityManager em;

  public void save(Item item){
    if (item.getId()==null){ //아이템은 jpa 저장하기 전까지 id값이 없다 ->완전히 새로 생성한 객체다.
      em.persist(item); // em.persist로 완전히 새롭게 등록한다.
    }else{ // 만약 item의 id가 있다면 이미 db에 등록되어있다는 뜻
      em.merge(item); // 그러면 업데이트 해준다.
    }
  }

  public Item findOne(Long id){ //단건 찾기
    return em.find(Item.class, id);
  }

  public List<Item> findAll(){ //다수의 건 찾기
    return em.createQuery("select i from Item i ", Item.class)
        .getResultList();
  }
}
