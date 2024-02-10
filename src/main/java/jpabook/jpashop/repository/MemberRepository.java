package jpabook.jpashop.repository;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

// @Repository -> 스프링 빈으로 등록해줌
@Repository
@RequiredArgsConstructor
public class MemberRepository {

  private final EntityManager em; //엔티티 매니저 주입 -> @PersistenceContext가 있어야 injection이 되는데, 스프링에서는 Autowired도 가능하게 해줌

  public void save(Member member){
    em.persist(member); //영속성 컨텍스트에 member를 넣고 , 트랜잭션이 commit되는 시점에 db에 반영
  }
  public Member findOne(Long id){ // 단건 조회
    return em.find(Member.class, id);
  }

  public List<Member> findAll(){ // 다수건 조회 // 쿼리문(jpql)으로 다수를 조회해서 리스트화 하기
    List<Member> result = em.createQuery("select m from Member m", Member.class) //jpa쿼리는 joing 할때 from하고 entity 집어넣기
        .getResultList();

    return result;
  }

  public List<Member> findByName(String name){ //이름으로 조회하기
    return em.createQuery("select m from Member m where m.name= :name", Member.class)
        .setParameter("name",name) //파라미터 바인딩
        .getResultList();
  }


}
